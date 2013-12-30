package com.lifedjtu.jw.business.task.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
import com.lifedjtu.jw.util.pattern.InfoProcessHub;

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
				course.setCourseCredits(Double.parseDouble(courseDto.getCourseMarks().trim()));
				course.setCourseProperty(courseDto.getCourseAttr());
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
		if(courseTakenItems==null||courseTakenItems.size()==0){
			takenBuilder.append("时间地点均不占");
		}else{
			for(CourseTakenItem courseTakenItem : courseTakenItems){
				takenBuilder.append(InfoProcessHub.transferCourseTakenItem(courseTakenItem)+";");
			}
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
				timeEntry = InfoProcessHub.transferExamDate(examDto.getExamDate());
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

	
	
}
