package com.lifedjtu.jw.pojos.dto;

import com.lifedjtu.jw.pojos.EntityObject;

public class EvaEntry extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -211137091872163460L;
	private String courseName;
	private String courseTeacher;
	private String courseHref;
	private int courseStatus;
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseTeacher() {
		return courseTeacher;
	}
	public void setCourseTeacher(String courseTeacher) {
		this.courseTeacher = courseTeacher;
	}
	public String getCourseHref() {
		return courseHref;
	}
	public void setCourseHref(String courseHref) {
		this.courseHref = courseHref;
	}
	public int getCourseStatus() {
		return courseStatus;
	}
	public void setCourseStatus(int courseStatus) {
		this.courseStatus = courseStatus;
	}
	public EvaEntry(String courseName, String courseTeacher, String courseHref,
			int courseStatus) {
		super();
		this.courseName = courseName;
		this.courseTeacher = courseTeacher;
		this.courseHref = courseHref;
		this.courseStatus = courseStatus;
	}
	
	public EvaEntry() {}
}
