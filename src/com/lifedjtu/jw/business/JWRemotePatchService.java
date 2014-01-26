package com.lifedjtu.jw.business;

import com.lifedjtu.jw.pojos.dto.EvaList;

public interface JWRemotePatchService {
	public boolean evaluateAllCourses(String sessionId);
	public String tempSignin(String studentId, String password);
	
	public EvaList getEvaList(String session);
	public boolean evaluateCourse(String session, String courseHref);
}
