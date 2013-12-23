package com.lifedjtu.jw.pojos;

import java.util.List;

public class Course extends EntityObject{
	private String aliasName;
	private String courseNumber;
	private String courseName;
	private String teacherName;
	private String courseMarks;
	private String courseAttr;
	private String examType;
	private String examAttr;
	private String isDelayed;
	private List<CourseTakenItem> courseTakenItems;
	
	public Course(String aliasName, String courseNumber, String courseName,
			String teacherName, String courseMarks, String courseAttr,
			String examType, String examAttr, String isDelayed,
			List<CourseTakenItem> courseTakenItems) {
		super();
		this.aliasName = aliasName;
		this.courseNumber = courseNumber;
		this.courseName = courseName;
		this.teacherName = teacherName;
		this.courseMarks = courseMarks;
		this.courseAttr = courseAttr;
		this.examType = examType;
		this.examAttr = examAttr;
		this.isDelayed = isDelayed;
		this.courseTakenItems = courseTakenItems;
	}
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public String getCourseNumber() {
		return courseNumber;
	}
	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getCourseMarks() {
		return courseMarks;
	}
	public void setCourseMarks(String courseMarks) {
		this.courseMarks = courseMarks;
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
	public String getExamAttr() {
		return examAttr;
	}
	public void setExamAttr(String examAttr) {
		this.examAttr = examAttr;
	}
	public String isDelayed() {
		return isDelayed;
	}
	public void setDelayed(String isDelayed) {
		this.isDelayed = isDelayed;
	}
	public List<CourseTakenItem> getCourseTakenItems() {
		return courseTakenItems;
	}
	public void setCourseTakenItems(List<CourseTakenItem> courseTakenItems) {
		this.courseTakenItems = courseTakenItems;
	}
	@Override
	public String toString() {
		return "Course [aliasName=" + aliasName + ", courseNumber="
				+ courseNumber + ", courseName=" + courseName
				+ ", teacherName=" + teacherName + ", courseMarks="
				+ courseMarks + ", courseAttr=" + courseAttr + ", examType="
				+ examType + ", examAttr=" + examAttr + ", isDelayed="
				+ isDelayed + ", courseTakenItems=" + courseTakenItems + "]\n";
	}
	
	
	
}
