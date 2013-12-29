package com.lifedjtu.jw.business.task;

import java.util.List;

import com.lifedjtu.jw.pojos.Course;
import com.lifedjtu.jw.pojos.CourseInstance;
import com.lifedjtu.jw.pojos.Exam;
import com.lifedjtu.jw.pojos.ExamInstance;
import com.lifedjtu.jw.pojos.dto.CourseDto;
import com.lifedjtu.jw.pojos.dto.DjtuDate;
import com.lifedjtu.jw.pojos.dto.ExamDto;

public interface JWUpdateCacheAsyncer {

	/**
	 * 以下方法行为需再考虑
	 */
	public boolean updateCourseInfo(String userId, List<CourseDto> courseDtos, DjtuDate djtuDate);//主调方法
	
	public CourseInstance updateCourseInstanceInfo(Course course, CourseDto courseDto,int year, int term);
	
	public boolean updateExamInfo(String userId, List<ExamDto> examDtos);//主调方法
	
	public ExamInstance updateExamInstanceInfo(Exam exam, ExamDto examDto, CourseInstance courseInstance);
}
