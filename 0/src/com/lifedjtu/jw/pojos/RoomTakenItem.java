package com.lifedjtu.jw.pojos;

import java.util.Date;

public class RoomTakenItem {
	private String roomId;
	private int roomRemoteId;
	private String roomName;
	private String buildingId;
	private Date dataDate;
	private String todayTakenCondition;
	private String tomorrowTakenCondition;
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public int getRoomRemoteId() {
		return roomRemoteId;
	}
	public void setRoomRemoteId(int roomRemoteId) {
		this.roomRemoteId = roomRemoteId;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
	public Date getDataDate() {
		return dataDate;
	}
	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}
	public String getTodayTakenCondition() {
		return todayTakenCondition;
	}
	public void setTodayTakenCondition(String todayTakenCondition) {
		this.todayTakenCondition = todayTakenCondition;
	}
	public String getTomorrowTakenCondition() {
		return tomorrowTakenCondition;
	}
	public void setTomorrowTakenCondition(String tomorrowTakenCondition) {
		this.tomorrowTakenCondition = tomorrowTakenCondition;
	}
	
	
}
