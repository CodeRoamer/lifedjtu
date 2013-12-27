package com.lifedjtu.jw.pojos;

import java.util.Date;

public class ExamInstance extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7828941619416271997L;

	private String examInstanceId;
	
	private String id;
	private String courseInstanceId;
	private String courseName;
	private String roomId; //可以通过这个外键连接教室表，获取教室的详细信息，例如位置和大小
	private String roomName;
	private boolean scoreOut;
	
	private Date examDate;
	private int lastedMinutes;
	private String examStatus;
	
	public int getLastedMinutes() {
		return lastedMinutes;
	}
	public void setLastedMinutes(int lastedMinutes) {
		this.lastedMinutes = lastedMinutes;
	}
	public boolean isScoreOut() {
		return scoreOut;
	}
	public void setScoreOut(boolean scoreOut) {
		this.scoreOut = scoreOut;
	}
	public String getExamInstanceId() {
		return examInstanceId;
	}
	public void setExamInstanceId(String examInstanceId) {
		this.examInstanceId = examInstanceId;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
