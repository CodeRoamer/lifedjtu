package com.lifedjtu.jw.pojos;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class RoomTakenItem extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8280870762481766184L;
	@Id
	private String id;
	@OneToOne
	@JoinColumn(name="roomId")
	private Room room;
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
	
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
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
