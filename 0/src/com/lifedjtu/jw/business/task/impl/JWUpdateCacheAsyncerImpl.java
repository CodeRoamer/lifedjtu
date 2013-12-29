package com.lifedjtu.jw.business.task.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lifedjtu.jw.business.JWRemoteService;
import com.lifedjtu.jw.business.task.JWUpdateCacheAsyncer;
import com.lifedjtu.jw.dao.CriteriaWrapper;
import com.lifedjtu.jw.dao.impl.CourseDao;
import com.lifedjtu.jw.dao.impl.CourseInstanceDao;
import com.lifedjtu.jw.dao.impl.ExamDao;
import com.lifedjtu.jw.dao.impl.ExamInstanceDao;
import com.lifedjtu.jw.dao.impl.UserCourseDao;
import com.lifedjtu.jw.dao.support.UUIDGenerator;
import com.lifedjtu.jw.pojos.Course;
import com.lifedjtu.jw.pojos.CourseInstance;
import com.lifedjtu.jw.pojos.Exam;
import com.lifedjtu.jw.pojos.ExamInstance;
import com.lifedjtu.jw.pojos.User;
import com.lifedjtu.jw.pojos.UserCourse;
import com.lifedjtu.jw.pojos.dto.CourseDto;
import com.lifedjtu.jw.pojos.dto.CourseTakenItem;
import com.lifedjtu.jw.pojos.dto.DjtuDate;
import com.lifedjtu.jw.pojos.dto.ExamDto;
import com.lifedjtu.jw.pojos.dto.ExamTimeEntry;
import com.lifedjtu.jw.util.MapMaker;

@Component("jwUpdateCacheAsyncer")
@Transactional
public class JWUpdateCacheAsyncerImpl implements JWUpdateCacheAsyncer{
	@Autowired
	private CourseDao courseDao;
	@Autowired
	private CourseInstanceDao courseInstanceDao;
	@Autowired
	private ExamDao examDao;
	@Autowired
	private ExamInstanceDao examInstanceDao;
	@Autowired
	private UserCourseDao userCourseDao;
	@Autowired
	private JWRemoteService jwRemoteService;
	
	@Override
	public boolean updateCourseInfo(String userId, List<CourseDto> courseDtos, DjtuDate djtuDate) {
		List<Course> courses = new ArrayList<Course>();
		List<CourseInstance> courseInstances = new ArrayList<CourseInstance>();
		List<UserCourse> userCourses = new ArrayList<UserCourse>();
		for(CourseDto courseDto : courseDtos){
			Course course = courseDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("courseAlias", courseDto.getAliasName()),Restrictions.eq("courseName", courseDto.getCourseName())));
			if(course==null){
				course = new Course();
				course.setCourseAlias(courseDto.getAliasName());
				course.setCourseName(courseDto.getCourseName());
				course.setId(UUIDGenerator.randomUUID());
				courses.add(course);
			}
			CourseInstance courseInstance = updateCourseInstanceInfo(course, courseDto, djtuDate.getYear(), djtuDate.getTerm());
			courseInstances.add(courseInstance);
			UserCourse userCourse = userCourseDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("user.id", userId),Restrictions.eq("courseInstance.id", courseInstance.getId())));
			if(userCourse==null){
				userCourse = new UserCourse();
				userCourse.setId(UUIDGenerator.randomUUID());
				userCourse.setUser(new User(userId));
				userCourse.setCourseInstance(courseInstance);
				userCourses.add(userCourse);
			}
			
		}
		courseDao.addMulti(courses);
		courseInstanceDao.addMulti(courseInstances);
		userCourseDao.addMulti(userCourses);
		return true;
	}

	@Override
	public CourseInstance updateCourseInstanceInfo(Course course, CourseDto courseDto, int year, int term) {
		CourseInstance courseInstance = courseInstanceDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("courseAlias", courseDto.getAliasName()),Restrictions.eq("courseRemoteId", courseDto.getCourseRemoteId()),Restrictions.eq("courseName", courseDto.getCourseName())));
		if(courseInstance==null){
			courseInstance = new CourseInstance();
			courseInstance.setId(UUIDGenerator.randomUUID());
			courseInstance.setCourseAlias(courseDto.getAliasName());
			courseInstance.setCourseName(courseDto.getCourseName());
			courseInstance.setCourseRemoteId(courseDto.getCourseRemoteId());
			courseInstance.setBadEval(0);
			courseInstance.setGoodEval(0);
			courseInstance.setCourse(course);
		}
		

		
		StringBuilder takenBuilder = new StringBuilder();
		List<CourseTakenItem> courseTakenItems = courseDto.getCourseTakenItems();
		for(CourseTakenItem courseTakenItem : courseTakenItems){
			takenBuilder.append(transferCourseTakenItem(courseTakenItem)+";");
		}
		
		courseInstance.setCourseTakenInfo(takenBuilder.toString());
		courseInstance.setTeacherName(courseDto.getTeacherName());
		courseInstance.setYear(year);
		courseInstance.setTerm(term);
		
		return courseInstance;
	}

	@Override
	public boolean updateExamInfo(String userId, List<ExamDto> examDtos){
		
		
		for(ExamDto examDto : examDtos){
			Course course = courseDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("courseName", examDto.getCourseName()),Restrictions.eq("courseAlias", examDto.getCourseAliasName())));
			Exam exam = examDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("course.id", course.getId())));
			if(exam==null){
				exam = new Exam();
				exam.setCourse(course);
				exam.setCourseAlias(examDto.getCourseAliasName());
				exam.setCourseName(examDto.getCourseName());
				exam.setId(UUIDGenerator.randomUUID());
			}
			ExamTimeEntry timeEntry;
			try {
				timeEntry = transferExamDate(examDto.getExamDate());
				exam.setExamDate(timeEntry.getDate());
				exam.setLastedMinutes(timeEntry.getLastedMinutes());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			examDao.add(exam);
			
			
			//开始两种更新！第一种更新和第二种更新均需要跟此用户与此考试的科目关联的CourseInstance
			UserCourse userCourse = userCourseDao.findOneByJoinedParams(MapMaker.instance("courseInstance", "courseInstance").toMap(),CriteriaWrapper.instance().and(Restrictions.eq("user.id", userId),Restrictions.eq("courseInstance.courseAlias", course.getCourseAlias())));
			CourseInstance courseInstance = userCourse.getCourseInstance();
			//1. 直接用户更新
			List<UserCourse> userCourses = userCourseDao.findByParams(CriteriaWrapper.instance().and(Restrictions.eq("courseInstance.id", courseInstance.getId())));
			ExamInstance examInstance = updateExamInstanceInfo(exam, examDto, courseInstance);
			for(UserCourse uc : userCourses){
				uc.setExamInstance(examInstance);
			}
			userCourseDao.addMulti(userCourses);
			
			//2. 间接用户更新
			List<UserCourse> userCourses2 = userCourseDao.findByJoinedParams(MapMaker.instance("courseInstance", "courseInstance").toMap(),CriteriaWrapper.instance().and(Restrictions.eq("courseInstance.courseAlias", courseInstance.getCourseAlias()),Restrictions.ne("courseInstance.id", courseInstance.getId())));
			List<ExamInstance> examInstances = new ArrayList<ExamInstance>();
			for(UserCourse uc : userCourses2){
				ExamInstance tempInstance = uc.getExamInstance();
				if(tempInstance==null){
					tempInstance = new ExamInstance();
					tempInstance.setCourseInstance(uc.getCourseInstance());
					tempInstance.setCourseName(uc.getCourseInstance().getCourseName());
					tempInstance.setExam(exam);
					tempInstance.setId(UUIDGenerator.randomUUID());
					tempInstance.setScoreOut(false);
					tempInstance.setExamDate(exam.getExamDate());
					tempInstance.setLastedMinutes(exam.getLastedMinutes());
					tempInstance.setRoomName(null);
					examInstances.add(tempInstance);
				}else if(tempInstance.getRoomName()==null||tempInstance.getRoomName().equals("")){
					tempInstance.setRoomName(null);
					tempInstance.setExamDate(exam.getExamDate());
					tempInstance.setLastedMinutes(exam.getLastedMinutes());
					examInstances.add(tempInstance);
				}else{
					continue;
				}
				uc.setExamInstance(tempInstance);
			}
			examInstanceDao.addMulti(examInstances);
			userCourseDao.addMulti(userCourses2);
		}
	
		
		return true;
	}

	@Override
	public ExamInstance updateExamInstanceInfo(Exam exam, ExamDto examDto, CourseInstance courseInstance){
		ExamInstance examInstance = examInstanceDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("courseInstance.id", courseInstance.getId())));
		if(examInstance==null){
			examInstance = new ExamInstance();
			examInstance.setCourseInstance(courseInstance);
			examInstance.setCourseName(courseInstance.getCourseName());
			examInstance.setExam(exam);
			examInstance.setId(UUIDGenerator.randomUUID());
			examInstance.setScoreOut(false);
		}
		examInstance.setExamDate(exam.getExamDate());
		examInstance.setExamStatus(examDto.getCourseProperty());
		examInstance.setLastedMinutes(exam.getLastedMinutes());
		examInstance.setRoomName(examDto.getRoomName());
		examInstanceDao.add(examInstance);
		return examInstance;
	}

	
	
	
	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public CourseInstanceDao getCourseInstanceDao() {
		return courseInstanceDao;
	}

	public void setCourseInstanceDao(CourseInstanceDao courseInstanceDao) {
		this.courseInstanceDao = courseInstanceDao;
	}

	public ExamDao getExamDao() {
		return examDao;
	}

	public void setExamDao(ExamDao examDao) {
		this.examDao = examDao;
	}

	public ExamInstanceDao getExamInstanceDao() {
		return examInstanceDao;
	}

	public void setExamInstanceDao(ExamInstanceDao examInstanceDao) {
		this.examInstanceDao = examInstanceDao;
	}
	
	public JWRemoteService getJwRemoteService() {
		return jwRemoteService;
	}

	public void setJwRemoteService(JWRemoteService jwRemoteService) {
		this.jwRemoteService = jwRemoteService;
	}

	public UserCourseDao getUserCourseDao() {
		return userCourseDao;
	}

	public void setUserCourseDao(UserCourseDao userCourseDao) {
		this.userCourseDao = userCourseDao;
	}

	private String transferCourseTakenItem(CourseTakenItem courseTakenItem){
		StringBuilder builder = new StringBuilder();
		
		String week = courseTakenItem.getWeek();	
		week = week.substring(0, week.length()-1);
		String[] parts = week.split("-");
		builder.append("startWeek="+parts[0]+"&endWeek="+parts[1]+"&");
		
		String weekDayStr = courseTakenItem.getDay().substring(1);
		int weekDay = 0;
		if(weekDayStr.equals("一")){
			weekDay = 1;
		}else if(weekDayStr.equals("二")){
			weekDay = 2;
		}else if(weekDayStr.equals("三")){
			weekDay = 3;
		}else if(weekDayStr.equals("四")){
			weekDay = 4;
		}else if(weekDayStr.equals("五")){
			weekDay = 5;
		}else if(weekDayStr.equals("六")){
			weekDay = 6;
		}else if(weekDayStr.equals("日")){
			weekDay = 7;
		}
		builder.append("weekDay="+weekDay+"&");
		
		builder.append("roomName="+courseTakenItem.getRoomName()+"&");
		
		String segments = courseTakenItem.getTime();
		Pattern pattern = Pattern.compile("[^0-9]*([0-9]+)([^0-9]+)([0-9]+)[^0-9]*");
		Matcher matcher = pattern.matcher(segments);
		if(matcher.find()){
			int first = Integer.parseInt(matcher.group(1));
			int second = Integer.parseInt(matcher.group(3));
			String action = matcher.group(2);
			
			if(action.equals("、")){
				builder.append("segments="+first+"|"+second);
			}else if(action.equals("-")){
				builder.append("segments="+first++);
				for(; first <= second; ++first){
					builder.append("|"+first);
				}
			}
		}
		return builder.toString();
	}
	
	private ExamTimeEntry transferExamDate(String examDate) throws ParseException{
		Pattern pattern = Pattern.compile("([0-9]+-[0-9]+-[0-9]+)[^0-9]+([0-9]+:[0-9]+)[^0-9]+([0-9]+:[0-9]+)");
		Matcher matcher = pattern.matcher(examDate);
		if(matcher.find()){
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			ExamTimeEntry entry = new ExamTimeEntry();
			String date = matcher.group(1);
			//System.err.println(date);
			String startTime = matcher.group(2);
			//System.err.println(startTime);
			String endTime = matcher.group(3);
			//System.err.println(endTime);
			
			Date startDate = dateFormat.parse(date+" "+startTime);
			Date endDate = dateFormat.parse(date+" "+endTime);
			
			entry.setDate(startDate);
			entry.setLastedMinutes((int)((endDate.getTime()-startDate.getTime())/1000/60));
			return entry;
		}
		return null;
	}
}
