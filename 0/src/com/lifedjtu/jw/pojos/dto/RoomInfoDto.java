package com.lifedjtu.jw.pojos.dto;

import java.util.List;

import com.lifedjtu.jw.pojos.EntityObject;

public class RoomInfoDto extends EntityObject {
	private String roomName;
	private String siteNumber;
	private String classNumber;
	private String examNumber;
	private String stationaryType;
	private String roomType;
	private List<String> occupyInfos;
	
	
	public RoomInfoDto(String roomName, String siteNumber, String classNumber,
			String examNumber, String stationaryType, String roomType,
			List<String> occupyInfos) {
		super();
		this.roomName = roomName;
		this.siteNumber = siteNumber;
		this.classNumber = classNumber;
		this.examNumber = examNumber;
		this.stationaryType = stationaryType;
		this.roomType = roomType;
		this.occupyInfos = occupyInfos;
	}
	
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getSiteNumber() {
		return siteNumber;
	}
	public void setSiteNumber(String siteNumber) {
		this.siteNumber = siteNumber;
	}
	public String getClassNumber() {
		return classNumber;
	}
	public void setClassNumber(String classNumber) {
		this.classNumber = classNumber;
	}
	public String getExamNumber() {
		return examNumber;
	}
	public void setExamNumber(String examNumber) {
		this.examNumber = examNumber;
	}
	public String getStationaryType() {
		return stationaryType;
	}
	public void setStationaryType(String stationaryType) {
		this.stationaryType = stationaryType;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public List<String> getOccupyInfos() {
		return occupyInfos;
	}
	public void setOccupyInfos(List<String> occupyInfos) {
		this.occupyInfos = occupyInfos;
	}

	@Override
	public String toString() {
		return "RoomInfoDto [roomName=" + roomName + ", siteNumber="
				+ siteNumber + ", classNumber=" + classNumber + ", examNumber="
				+ examNumber + ", stationaryType=" + stationaryType
				+ ", roomType=" + roomType + ", occupyInfos=" + occupyInfos
				+ "]\n";
	}
	
}
