package com.lifedjtu.jw.pojos;

public class User extends EntityObject{
	private String id;
	private String studentId;
	private String password;
	
	private String username;
	private String curSessionId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCurSessionId() {
		return curSessionId;
	}
	public void setCurSessionId(String curSessionId) {
		this.curSessionId = curSessionId;
	}
	public User(){}
	public User(String studentId, String password){
		this.studentId = studentId;
		this.password = password;
	}
}
