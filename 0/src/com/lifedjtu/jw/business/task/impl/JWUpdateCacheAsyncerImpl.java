package com.lifedjtu.jw.business.task.impl;

import java.util.ArrayList;
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
import com.lifedjtu.jw.dao.support.UUIDGenerator;
import com.lifedjtu.jw.pojos.Course;
import com.lifedjtu.jw.pojos.CourseInstance;
import com.lifedjtu.jw.pojos.dto.CourseDto;
import com.lifedjtu.jw.pojos.dto.CourseTakenItem;
import com.lifedjtu.jw.pojos.dto.DjtuDate;
import com.lifedjtu.jw.pojos.dto.ExamDto;

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
	private JWRemoteService jwRemoteService;
	
	@Override
	public boolean updateCourseInfo(List<CourseDto> courseDtos, DjtuDate djtuDate) {
		List<Course> courses = new ArrayList<Course>();
		List<CourseInstance> courseInstances = new ArrayList<CourseInstance>();
		for(CourseDto courseDto : courseDtos){
			Course course = courseDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("courseAlias", courseDto.getAliasName()),Restrictions.eq("courseName", courseDto.getCourseName())));
			if(course==null){
				course = new Course();
				course.setCourseAlias(courseDto.getAliasName());
				course.setCourseName(courseDto.getCourseName());
				course.setId(UUIDGenerator.randomUUID());
				courses.add(course);
			}
			courseInstances.add(updateCourseInstanceInfo(course, courseDto, djtuDate.getYear(), djtuDate.getTerm()));
		}
		courseDao.addMulti(courses);
		courseInstanceDao.addMulti(courseInstances);
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
	public boolean updateExamInfo(List<ExamDto> examDtos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateExamInstanceInfo(List<ExamDto> examDtos) {
		// TODO Auto-generated method stub
		return false;
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
	
}
