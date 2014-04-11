package com.lifedjtu.jw.pojos.dto;

import java.util.List;

import com.lifedjtu.jw.pojos.EntityObject;

public class RoomWeekInfo extends EntityObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1592556055790521421L;
	private List<RoomDayInfo> roomDayInfos;
	

	public RoomWeekInfo(List<RoomDayInfo> roomDayInfos) {
		super();
		this.roomDayInfos = roomDayInfos;
	}

	public List<RoomDayInfo> getRoomDayInfos() {
		return roomDayInfos;
	}

	public void setRoomDayInfos(List<RoomDayInfo> roomDayInfos) {
		this.roomDayInfos = roomDayInfos;
	}
	
	public String toString(){
		String s = "";
		for(int i=0;i<roomDayInfos.size();i++){
			s += "周" + (i+1) + roomDayInfos.get(i).toString();
		}
		return s;
	}
}
