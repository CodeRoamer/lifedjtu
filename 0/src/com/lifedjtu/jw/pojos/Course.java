package com.lifedjtu.jw.pojos;

public class Course extends EntityObject{
	private String courseId;
	private String courseName;
	private String courseAlias;
	
	public Course() {}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseAlias() {
		return courseAlias;
	}

	public void setCourseAlias(String courseAlias) {
		this.courseAlias = courseAlias;
	}
	
	
}
