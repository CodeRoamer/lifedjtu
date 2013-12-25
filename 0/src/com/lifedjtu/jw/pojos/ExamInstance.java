package com.lifedjtu.jw.pojos;

import java.sql.Date;

public class ExamInstance extends EntityObject{
	private String examInstanceId;
	
	private String examId;
	private String courseInstanceId;
	private String courseName;
	private String roomId; //可以通过这个外键连接教室表，获取教室的详细信息，例如位置和大小
	private String roomName;
	
	private Date examDate;
	private String examStatus;
	public String getExamInstanceId() {
		return examInstanceId;
	}
	public void setExamInstanceId(String examInstanceId) {
		this.examInstanceId = examInstanceId;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
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
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public Date getExamDate() {
		return examDate;
	}
	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}
	public String getExamStatus() {
		return examStatus;
	}
	public void setExamStatus(String examStatus) {
		this.examStatus = examStatus;
	}
	
	
}
