package com.lifedjtu.jw.pojos;

import java.util.Date;

public class Exam extends EntityObject{
	private String examId;
	private String courseId;
	private String courseName;
	private String courseAlias;
	private Date examDate;
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
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
	public Date getExamDate() {
		return examDate;
	}
	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}
	public Exam(String examId, String courseId, String courseName,
			String courseAlias, Date examDate) {
		super();
		this.examId = examId;
		this.courseId = courseId;
		this.courseName = courseName;
		this.courseAlias = courseAlias;
		this.examDate = examDate;
	}
	
	public Exam(){}
}
