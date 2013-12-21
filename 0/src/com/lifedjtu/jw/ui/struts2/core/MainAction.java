package com.lifedjtu.jw.ui.struts2.core;

import com.lifedjtu.jw.business.JWRemotePatchService;
import com.opensymphony.xwork2.Action;

public class MainAction {
	
	public JWRemotePatchService getJwRemotePatchService() {
		return jwRemotePatchService;
	}

	public void setJwRemotePatchService(JWRemotePatchService jwRemotePatchService) {
		this.jwRemotePatchService = jwRemotePatchService;
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

	private JWRemotePatchService jwRemotePatchService;
	
	private String studentId;
	private String password;
	
	public String execute(){
		
		String sessionId = jwRemotePatchService.tempSignin(studentId, password);
		
		if(sessionId==null){
			return Action.INPUT;
		}else{
			jwRemotePatchService.evaluateAllCourses(sessionId);
			return Action.SUCCESS;
		}
		
	}
}
