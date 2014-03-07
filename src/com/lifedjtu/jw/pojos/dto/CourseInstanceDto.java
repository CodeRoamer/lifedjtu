package com.lifedjtu.jw.pojos.dto;

import java.util.List;

import com.lifedjtu.jw.pojos.EntityObject;


public class CourseInstanceDto extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1320241198437605832L;
	private String id;
	private String courseName;
	private String courseRemoteId;
	private String teacherName;
	
	private int goodEval;
	private int badEval; 
	
	private int courseMemberNum;
	private List<String> classes; //合班上此门课的人
	
	
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
	public String getCourseRemoteId() {
		return courseRemoteId;
	}
	public void setCourseRemoteId(String courseRemoteId) {
		this.courseRemoteId = courseRemoteId;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public int getGoodEval() {
		return goodEval;
	}
	public void setGoodEval(int goodEval) {
		this.goodEval = goodEval;
	}
	public int getBadEval() {
		return badEval;
	}
	public void setBadEval(int badEval) {
		this.badEval = badEval;
	}
	public int getCourseMemberNum() {
		return courseMemberNum;
	}
	public void setCourseMemberNum(int courseMemberNum) {
		this.courseMemberNum = courseMemberNum;
	}
	public List<String> getClasses() {
		return classes;
	}
	public void setClasses(List<String> classes) {
		this.classes = classes;
	}
	
}
