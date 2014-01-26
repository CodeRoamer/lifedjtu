package com.lifedjtu.jw.pojos;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserCourse extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3690794133607720170L;
	@Id
	private String id;
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	@ManyToOne
	@JoinColumn(name="courseInstanceId")
	private CourseInstance courseInstance;
	@ManyToOne
	@JoinColumn(name="examInstanceId")
	private ExamInstance examInstance;
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public CourseInstance getCourseInstance() {
		return courseInstance;
	}


	public void setCourseInstance(CourseInstance courseInstance) {
		this.courseInstance = courseInstance;
	}


	public ExamInstance getExamInstance() {
		return examInstance;
	}


	public void setExamInstance(ExamInstance examInstance) {
		this.examInstance = examInstance;
	}


	public UserCourse() {}
}
