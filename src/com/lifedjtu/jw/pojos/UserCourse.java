package com.lifedjtu.jw.pojos;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userId")
	private User user;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="courseInstanceId")
	private CourseInstance courseInstance;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="examInstanceId")
	private ExamInstance examInstance;
	
	private boolean scoreNoted;
	private boolean examNoted;
	
	
	
	public boolean isScoreNoted() {
		return scoreNoted;
	}


	public void setScoreNoted(boolean scoreNoted) {
		this.scoreNoted = scoreNoted;
	}


	public boolean isExamNoted() {
		return examNoted;
	}


	public void setExamNoted(boolean examNoted) {
		this.examNoted = examNoted;
	}


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
