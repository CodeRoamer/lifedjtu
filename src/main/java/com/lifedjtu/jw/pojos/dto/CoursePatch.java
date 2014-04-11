package com.lifedjtu.jw.pojos.dto;

import java.util.List;

import com.lifedjtu.jw.pojos.Course;
import com.lifedjtu.jw.pojos.CourseInstance;
import com.lifedjtu.jw.pojos.EntityObject;

public class CoursePatch extends EntityObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -583123764446889071L;
	
	private Course course;
	private List<CourseInstance> courseInstances;
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public List<CourseInstance> getCourseInstances() {
		return courseInstances;
	}
	public void setCourseInstances(List<CourseInstance> courseInstances) {
		this.courseInstances = courseInstances;
	}
	public CoursePatch(Course course, List<CourseInstance> courseInstances) {
		super();
		this.course = course;
		this.courseInstances = courseInstances;
	}
	
	public CoursePatch() {}
}
