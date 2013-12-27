package com.lifedjtu.jw.pojos;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Course extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5498423261074121186L;
	@Id
	private String id;
	private String courseName;
	private String courseAlias;
	
	public Course() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseAlias() {
		return courseAlias;
	}

	public void setCourseAlias(String courseAlias) {
		this.courseAlias = courseAlias;
	}
	
	
}
