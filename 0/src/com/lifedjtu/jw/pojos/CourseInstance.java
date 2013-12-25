package com.lifedjtu.jw.pojos;

public class CourseInstance extends EntityObject{
	private String courseInstanceId;
	private String courseName;
	private String courseRemoteId;
	private String courseAlias;
	private String courseId;
	private String teacherName;
	
	public CourseInstance() {}

	public String getCourseInstanceId() {
		return courseInstanceId;
	}

	public void setCourseInstanceId(String courseInstanceId) {
		this.courseInstanceId = courseInstanceId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseRemoteId() {
		return courseRemoteId;
	}

	public void setCourseRemoteId(String courseRemoteId) {
		this.courseRemoteId = courseRemoteId;
	}

	public String getCourseAlias() {
		return courseAlias;
	}

	public void setCourseAlias(String courseAlias) {
		this.courseAlias = courseAlias;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	
}
