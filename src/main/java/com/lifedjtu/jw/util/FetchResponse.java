package com.lifedjtu.jw.util;

import java.io.InputStream;
import java.util.Map;

import com.lifedjtu.jw.pojos.EntityObject;

public class FetchResponse  extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8557873776470056483L;
	//statusCode至关重要，通过设置不允许转发，如果登陆超时，或者登陆失败，在想访问其他页面时，会出现302转发码，
	//如果正常，则会返回200，除此之外404，500均视为失败
	private int statusCode;
	//响应体
	private String responseBody;
	//保存响应头部
	private Map<String, String> responseHeader;
	//存储当前与教务在线官网的会话ID
	private String sessionId;
	
	private InputStream inputStream;
	
	
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
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
