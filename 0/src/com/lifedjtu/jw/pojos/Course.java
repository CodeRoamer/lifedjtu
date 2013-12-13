package com.lifedjtu.jw.pojos;

import java.util.List;

public class Course extends EntityObject{
	private String aliasName;
	private CourseInfo courseInfo;
	private String courseName;
	private String teacherName;
	private double courseMarks;
	private List<CourseTakenItem> courseTakenItems;
	
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public CourseInfo getCourseInfo() {
		return courseInfo;
	}
	public void setCourseInfo(CourseInfo courseInfo) {
		this.courseInfo = courseInfo;
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
	public double getCourseMarks() {
		return courseMarks;
	}
	public void setCourseMarks(double courseMarks) {
		this.courseMarks = courseMarks;
	}
	public List<CourseTakenItem> getCourseTakenItems() {
		return courseTakenItems;
	}
	public void setCourseTakenItems(List<CourseTakenItem> courseTakenItems) {
		this.courseTakenItems = courseTakenItems;
	}
	
	
}
