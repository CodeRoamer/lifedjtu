package com.lifedjtu.jw.pojos;

public class Exam extends EntityObject{
	private String courseAliasName;
	private String courseName;
	private String examDate;
	private String roomName;
	private String courseProperty;
	public String getCourseAliasName() {
		return courseAliasName;
	}
	public void setCourseAliasName(String courseAliasName) {
		this.courseAliasName = courseAliasName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getExamDate() {
		return examDate;
	}
	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getCourseProperty() {
		return courseProperty;
	}
	public void setCourseProperty(String courseProperty) {
		this.courseProperty = courseProperty;
	}
	
}
