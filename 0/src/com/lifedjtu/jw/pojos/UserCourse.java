package com.lifedjtu.jw.pojos;

public class UserCourse extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3690794133607720170L;
	private String id;
	private String userId;
	private String courseInstanceId;
	private String examInstanceId;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCourseInstanceId() {
		return courseInstanceId;
	}
	public void setCourseInstanceId(String courseInstanceId) {
		this.courseInstanceId = courseInstanceId;
	}
	public String getExamInstanceId() {
		return examInstanceId;
	}
	public void setExamInstanceId(String examInstanceId) {
		this.examInstanceId = examInstanceId;
	}
	public UserCourse(String userId, String courseInstanceId,
			String examInstanceId) {
		super();
		this.userId = userId;
		this.courseInstanceId = courseInstanceId;
		this.examInstanceId = examInstanceId;
	}
	
	public UserCourse() {}
}
