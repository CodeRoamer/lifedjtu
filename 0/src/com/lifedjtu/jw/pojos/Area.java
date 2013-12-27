package com.lifedjtu.jw.pojos;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Area extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1199454941218580378L;
	@Id
	private String id;
	private String areaName;
	private int areaRemoteId;
	private double longitude;
	private double latitude;
	private double radius;
	
	@OneToMany(mappedBy="area", fetch=FetchType.LAZY)
	private List<String> buildings;
	
	public Area() {}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public int getAreaRemoteId() {
		return areaRemoteId;
	}
	public void setAreaRemoteId(int areaRemoteId) {
		this.areaRemoteId = areaRemoteId;
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
	public List<String> getBuildings() {
		return buildings;
	}
	public void setBuildings(List<String> buildings) {
		this.buildings = buildings;
	}
	
}
