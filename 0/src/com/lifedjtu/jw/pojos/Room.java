package com.lifedjtu.jw.pojos;

public class Room extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8448616113206422882L;
	private String id;
	private String roomName;
	private String buildingId;
	private int roomRemoteId;
	
	private int roomSeatNum;
	private String roomSeatType;
	private int courseCapacity;
	private int examCapacity;
	private String roomType;
	
	public int getRoomSeatNum() {
		return roomSeatNum;
	}

	public void setRoomSeatNum(int roomSeatNum) {
		this.roomSeatNum = roomSeatNum;
	}

	public String getRoomSeatType() {
		return roomSeatType;
	}

	public void setRoomSeatType(String roomSeatType) {
		this.roomSeatType = roomSeatType;
	}

	public int getCourseCapacity() {
		return courseCapacity;
	}

	public void setCourseCapacity(int courseCapacity) {
		this.courseCapacity = courseCapacity;
	}

	public int getExamCapacity() {
		return examCapacity;
	}

	public void setExamCapacity(int examCapacity) {
		this.examCapacity = examCapacity;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public int getRoomRemoteId() {
		return roomRemoteId;
	}

	public void setRoomRemoteId(int roomRemoteId) {
		this.roomRemoteId = roomRemoteId;
	}

	public Room() {}
}
