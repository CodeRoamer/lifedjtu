package com.lifedjtu.jw.pojos.dto;

import java.util.List;

import com.lifedjtu.jw.pojos.EntityObject;

public class CourseRecordDto extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1663379957559386488L;
	private String courseAliasName;
	private String courseProperty;
	private String courseAttr;
	private String examType;
	private int courseCapacity;
	private int realCapacity;
	private String teacherInfo;
	private double studyTime;
	private List<String> classes;
	public String getCourseAliasName() {
		return courseAliasName;
	}
	public void setCourseAliasName(String courseAliasName) {
		this.courseAliasName = courseAliasName;
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
	public int getCourseCapacity() {
		return courseCapacity;
	}
	public void setCourseCapacity(int courseCapacity) {
		this.courseCapacity = courseCapacity;
	}
	public int getRealCapacity() {
		return realCapacity;
	}
	public void setRealCapacity(int realCapacity) {
		this.realCapacity = realCapacity;
	}
	public String getTeacherInfo() {
		return teacherInfo;
	}
	public void setTeacherInfo(String teacherInfo) {
		this.teacherInfo = teacherInfo;
	}
	public double getStudyTime() {
		return studyTime;
	}
	public void setStudyTime(double studyTime) {
		this.studyTime = studyTime;
	}
	public List<String> getClasses() {
		return classes;
	}
	public void setClasses(List<String> classes) {
		this.classes = classes;
	}
	
	
}
