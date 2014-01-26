package com.lifedjtu.jw.pojos.dto;

import java.util.List;

import com.lifedjtu.jw.pojos.EntityObject;

public class BuildingDto extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6075987659581623717L;
	private List<RoomInfoDto> roomInfoDtos;

	public BuildingDto(List<RoomInfoDto> roomInfoDtos) {
		super();
		this.roomInfoDtos = roomInfoDtos;
	}

	public List<RoomInfoDto> getRoomInfoDtos() {
		return roomInfoDtos;
	}

	public void setRoomInfoDtos(List<RoomInfoDto> roomInfoDtos) {
		this.roomInfoDtos = roomInfoDtos;
	}
	
	public String toString(){
		return roomInfoDtos.toString();
	}
}
