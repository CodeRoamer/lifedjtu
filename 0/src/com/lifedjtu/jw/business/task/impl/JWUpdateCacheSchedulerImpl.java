package com.lifedjtu.jw.business.task.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lifedjtu.jw.business.JWRemoteService;
import com.lifedjtu.jw.business.task.JWUpdateCacheScheduler;
import com.lifedjtu.jw.dao.impl.AreaDao;
import com.lifedjtu.jw.dao.impl.BuildingDao;
import com.lifedjtu.jw.dao.impl.RoomDao;

@Component("jwUpdateCacheScheduler")
public class JWUpdateCacheSchedulerImpl implements JWUpdateCacheScheduler{
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private BuildingDao buildingDao;
	@Autowired
	private RoomDao roomDao;
	@Autowired
	private JWRemoteService jwRemoteService;
	
	
	@Override
	public void updateRoomInfo() {

	}


	
	public AreaDao getAreaDao() {
		return areaDao;
	}


	public void setAreaDao(AreaDao areaDao) {
		this.areaDao = areaDao;
	}


	public BuildingDao getBuildingDao() {
		return buildingDao;
	}


	public void setBuildingDao(BuildingDao buildingDao) {
		this.buildingDao = buildingDao;
	}


	public RoomDao getRoomDao() {
		return roomDao;
	}


	public void setRoomDao(RoomDao roomDao) {
		this.roomDao = roomDao;
	}


	public JWRemoteService getJwRemoteService() {
		return jwRemoteService;
	}


	public void setJwRemoteService(JWRemoteService jwRemoteService) {
		this.jwRemoteService = jwRemoteService;
	}
	
}
