package com.lifedjtu.jw.pojos;

public class User extends EntityObject{
	private String studentId;
	private String password;
	
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
	public User(){}
	public User(String studentId, String password){
		this.studentId = studentId;
		this.password = password;
	}
}
