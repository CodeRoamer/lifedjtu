package com.lifedjtu.jw.pojos;

public class Room extends EntityObject{
	private String roomId;
	private String roomName;
	private String buildingId;
	private int roomRemoteId;
	
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
