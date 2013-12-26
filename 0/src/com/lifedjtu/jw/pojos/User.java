package com.lifedjtu.jw.pojos;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Access(AccessType.FIELD)
public class User extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3270505028982366472L;
	
	@Id
	private String userId;
	private String studentId;
	private String password;
	private String username;
	private String curSessionId;
	
	private String faculty;
	private String academy;
	private String privateKey;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getFaculty() {
		return faculty;
	}
	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}
	public String getAcademy() {
		return academy;
	}
	public void setAcademy(String academy) {
		this.academy = academy;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public User(){}
	public User(String studentId, String password){
		this.studentId = studentId;
		this.password = password;
	}
}
