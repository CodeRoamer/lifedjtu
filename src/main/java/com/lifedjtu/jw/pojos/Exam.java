package com.lifedjtu.jw.pojos;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Exam extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2155939337330675743L;
	@Id
	private String id;
	@OneToOne
	@JoinColumn(name="courseId")
	private Course course;
	private String courseName;
	private String courseAlias;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
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

	
	public Exam(){}
}
