package com.lifedjtu.jw.pojos;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class RoomTakenItem extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8280870762481766184L;
	@Id
	private String id;
	private int roomRemoteId;
	private String roomName;
	@ManyToOne
	@JoinColumn(name="buildingId")
	private Building building;
	private Date dataDate;
	private String todayTakenCondition;
	private String tomorrowTakenCondition;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	
	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
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
