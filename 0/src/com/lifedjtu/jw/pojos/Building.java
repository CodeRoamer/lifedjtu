package com.lifedjtu.jw.pojos;

import java.util.List;


public class Building extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2751780603601804234L;
	private String buildingId;
	private String buildingName;
	private String areaId;
	private int buildingRemoteId;
	private double longitude;
	private double latitude;
	private double radius;
	
	
	private List<Room> rooms;

	public Building() {}

	public String getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public int getBuildingRemoteId() {
		return buildingRemoteId;
	}

	public void setBuildingRemoteId(int buildingRemoteId) {
		this.buildingRemoteId = buildingRemoteId;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}
	
}
