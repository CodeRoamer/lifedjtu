package com.lifedjtu.jw.util;

import java.util.Map;

import com.lifedjtu.jw.pojos.EntityObject;

public class FetchResponse  extends EntityObject{
	private int statusCode;
	private String responseBody;
	private Map<String, String> responseHeader;
	private String sessionId;
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
	public Map<String, String> getResponseHeader() {
		return responseHeader;
	}
	public void setResponseHeader(Map<String, String> responseHeader) {
		this.responseHeader = responseHeader;
	}
	
	
}
