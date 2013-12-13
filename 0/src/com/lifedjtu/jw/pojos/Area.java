package com.lifedjtu.jw.pojos;

import java.util.List;


public class Area extends EntityObject{
	private String id;
	private String name;
	private List<String> buildings;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getBuildings() {
		return buildings;
	}
	public void setBuildings(List<String> buildings) {
		this.buildings = buildings;
	}
	
}
