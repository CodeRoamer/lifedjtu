package com.lifedjtu.jw.business.task;

public interface JWUpdateCacheScheduler {
	public boolean updateAreaInfo(String sessionId);//主调方法
	
	public boolean updateBuildingInfo(String sessionId);//主调方法
	public boolean updateBuildingEntryInfo(String sessionId, String areaId);
	
	public boolean updateRoomInfo(String sessionId);//主调方法
	public boolean updateRoomEntryInfo(String sessionId, String buildingId);
	
	
	public boolean updateRoomTakenItem(String sessionId);//主调方法
	public boolean updateRoomTakenItemByBuilding(String session, String buildingId,int week, int weekday);
	
	public void updateSchoolInfo(); //scheduler方法的入口
}
