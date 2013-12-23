package com.lifedjtu.jw.pojos;

public class CourseTakenItem extends EntityObject{
	private String week;
	private String day;
	private String time;
	private String roomName;
	
	public CourseTakenItem(String week, String day, String time, String roomName) {
		super();
		this.week = week;
		this.day = day;
		this.time = time;
		this.roomName = roomName;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	
}
