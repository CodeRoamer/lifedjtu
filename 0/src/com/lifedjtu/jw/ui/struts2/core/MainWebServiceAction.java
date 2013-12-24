package com.lifedjtu.jw.ui.struts2.core;

import com.lifedjtu.jw.business.JWRemotePatchService;
import com.lifedjtu.jw.pojos.dto.EvaList;
import com.opensymphony.xwork2.Action;

public class MainWebServiceAction {
	
	
	
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
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public EvaList getEvaList() {
		return evaList;
	}
	public void setEvaList(EvaList evaList) {
		this.evaList = evaList;
	}
	public String getCourseHref() {
		return courseHref;
	}
	public void setCourseHref(String courseHref) {
		this.courseHref = courseHref;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}

	private JWRemotePatchService jwRemotePatchService;
	
	private String studentId;
	private String password;
	
	private String sessionId;
	public String signin(){
		sessionId = jwRemotePatchService.tempSignin(studentId, password);
		return Action.SUCCESS;
	}
	
	private EvaList evaList;
	
	public String evaList(){
		evaList = jwRemotePatchService.getEvaList(sessionId);
		return Action.SUCCESS;
	}
	
	private String courseHref;
	
	private boolean status;
	public String evaluateCourse(){
		status = jwRemotePatchService.evaluateCourse(sessionId, courseHref);
		return Action.SUCCESS;
	}
}
