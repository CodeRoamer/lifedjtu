package com.lifedjtu.jw.business.task;

public interface JWUpdateCacheAsyncer {

	/**
	 * 以下方法行为需再考虑
	 */
	public boolean updateCourseInfo(String sessionId);
	
	public boolean updateCourseInstanceInfo(String sessionId);
	
	public boolean updateExamInfo(String sessionId);
	
	public boolean updateExamInstanceInfo(String sessionId);
}
