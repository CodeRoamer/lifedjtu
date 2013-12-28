package com.lifedjtu.jw.business.task;

public interface JWUpdateCacheScheduler {
	public boolean updateAreaInfo(String sessionId);//主调方法
	
	public boolean updateBuildingInfo(String sessionId);//主调方法
	public boolean updateBuildingEntryInfo(String sessionId, String areaId);
	
	public boolean updateRoomInfo(String sessionId);//主调方法
	public boolean updateRoomEntryInfo(String sessionId, String buildingId);
	
	
	public boolean updateRoomTakenItem(String sessionId);//主调方法
	public boolean updateRoomTakenItemByBuilding(String session, String buildingId,int week, int weekday);
	
	/**
	 * 以下方法行为需再考虑
	 */
	public boolean updateCourseInfo(String sessionId);
	
	public boolean updateCourseInstanceInfo(String sessionId);
	
	public boolean updateExamInfo(String sessionId);
	
	public boolean updateExamInstanceInfo(String sessionId);
}
