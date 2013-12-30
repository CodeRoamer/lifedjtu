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
	
	//新加入属性
	private String areaName;
	private String hostedAcademy;
	private double courseClasses;  //学时
	private double courseCredits;  //学分
	private String courseProperty;  //选课属性：任选，限选，必修
	
	
	public Course() {}

	public String getId() {
		return id;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getHostedAcademy() {
		return hostedAcademy;
	}

	public void setHostedAcademy(String hostedAcademy) {
		this.hostedAcademy = hostedAcademy;
	}

	public double getCourseClasses() {
		return courseClasses;
	}

	public void setCourseClasses(double courseClasses) {
		this.courseClasses = courseClasses;
	}

	public double getCourseCredits() {
		return courseCredits;
	}

	public void setCourseCredits(double courseCredits) {
		this.courseCredits = courseCredits;
	}

	public String getCourseProperty() {
		return courseProperty;
	}

	public void setCourseProperty(String courseProperty) {
		this.courseProperty = courseProperty;
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
