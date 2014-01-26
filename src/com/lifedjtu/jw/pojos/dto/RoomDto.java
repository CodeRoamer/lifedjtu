package com.lifedjtu.jw.pojos.dto;

import java.util.List;

import com.lifedjtu.jw.pojos.EntityObject;

public class RoomDto extends EntityObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4131101081801191227L;
	private List<RoomWeekInfo> roomWeekInfos;
	
	

	public RoomDto(List<RoomWeekInfo> roomWeekInfos) {
		super();
		this.roomWeekInfos = roomWeekInfos;
	}

	public List<RoomWeekInfo> getRoomWeekInfos() {
		return roomWeekInfos;
	}

	public void setRoomWeekInfos(List<RoomWeekInfo> roomWeekInfos) {
		this.roomWeekInfos = roomWeekInfos;
	}
	
	public String toString(){
		String s = "";
		for(int i=0;i<roomWeekInfos.size();i++){
			s += roomWeekInfos.get(i).toString();
		}
		return s;
	}
}
