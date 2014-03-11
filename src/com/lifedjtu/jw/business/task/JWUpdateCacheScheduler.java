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
	
	//为系统留一个后门，如果自动更新教室信息失败，可以手动更新
	public void updateRoomTakenInfo(String sessionId);
	
	
	public boolean updateSameCourseGroupInfo();  //主调方法
	public boolean updateSameClassGroupInfoByCourse(String courseId); 
	public boolean cleanupInstantMessage(); //主调方法
	//新增的scheduler方法
	public void updateIMSystem();
}
