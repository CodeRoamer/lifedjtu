package com.lifedjtu.jw.ui.struts2.core;

import static com.lifedjtu.jw.util.extractor.Extractor.$;

import com.lifedjtu.jw.util.FetchResponse;
import com.lifedjtu.jw.util.MapMaker;
import com.lifedjtu.jw.util.URLFetcher;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private String studentId;
	private String password;
	
	private String message;
	
	public String signin(){
		FetchResponse response = URLFetcher.fetchURLByPost("202.199.128.21/academic/j_acegi_security_check", null, MapMaker.instance("j_username", studentId).param("j_password", password).toMap());
		FetchResponse response2 = URLFetcher.fetchURLByGet("202.199.128.21/academic/showHeader.do", response.getSessionId());
		if(response2.getStatusCode()!=200){
			message = "登陆失败或已经超时，重新登陆！";
		}else {
			message = $("#greeting", response2.getResponseBody()).get(0).getText();
		}
		return Action.SUCCESS;
	}
}
