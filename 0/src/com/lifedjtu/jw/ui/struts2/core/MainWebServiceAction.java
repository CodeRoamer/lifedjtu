package com.lifedjtu.jw.ui.struts2.core;

import com.opensymphony.xwork2.Action;

public class MainWebServiceAction {
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

	private String studentId;
	private String password;
	public String signin(){
		
		return Action.SUCCESS;
	}
}
