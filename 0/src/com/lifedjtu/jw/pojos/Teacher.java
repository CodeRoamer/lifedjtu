package com.lifedjtu.jw.pojos;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Teacher {
	private int id;
	private String name;
	
	@Id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
