package com.lifedjtu.jw.pojos;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Building extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2751780603601804234L;
	@Id
	private String id;
	private String buildingName;
	@ManyToOne
	@JoinColumn(name="areaId")
	private Area area;
	private int buildingRemoteId;
	private double longitude;
	private double latitude;
	private double radius;
	
	@OneToMany(mappedBy="building", fetch=FetchType.LAZY)
	private List<Room> rooms;

	public Building() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}


	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
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
