package com.lifedjtu.jw.pojos;

import java.util.List;


public class CourseInfo extends EntityObject{
	private String courseName;
	private String courseSequence;
	private String aliasName;
	private String courseProperty;
	private String courseAttr;
	private String examType;
	private int courseQuantity;
	private int courseTaken;
	private String teacherName;
	private double courseTime;
	private List<String> mixClasses;
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseSequence() {
		return courseSequence;
	}
	public void setCourseSequence(String courseSequence) {
		this.courseSequence = courseSequence;
	}
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public String getCourseProperty() {
		return courseProperty;
	}
	public void setCourseProperty(String courseProperty) {
		this.courseProperty = courseProperty;
	}
	public String getCourseAttr() {
		return courseAttr;
	}
	public void setCourseAttr(String courseAttr) {
		this.courseAttr = courseAttr;
	}
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	public int getCourseQuantity() {
		return courseQuantity;
	}
	public void setCourseQuantity(int courseQuantity) {
		this.courseQuantity = courseQuantity;
	}
	public int getCourseTaken() {
		return courseTaken;
	}
	public void setCourseTaken(int courseTaken) {
		this.courseTaken = courseTaken;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public double getCourseTime() {
		return courseTime;
	}
	public void setCourseTime(double courseTime) {
		this.courseTime = courseTime;
	}
	public List<String> getMixClasses() {
		return mixClasses;
	}
	public void setMixClasses(List<String> mixClasses) {
		this.mixClasses = mixClasses;
	}
	
	
}
