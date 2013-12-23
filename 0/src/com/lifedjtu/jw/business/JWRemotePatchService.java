package com.lifedjtu.jw.business;

public interface JWRemotePatchService {
	public boolean evaluateAllCourses(String sessionId);
	public String tempSignin(String studentId, String password);
}
