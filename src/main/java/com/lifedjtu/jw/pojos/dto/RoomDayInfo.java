package com.lifedjtu.jw.pojos.dto;

import java.util.List;

import com.lifedjtu.jw.pojos.EntityObject;

public class RoomDayInfo extends EntityObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3433230119840901809L;
	private List<String> infos;

	
	public RoomDayInfo(List<String> infos) {
		super();
		this.infos = infos;
	}

	public List<String> getInfos() {
		return infos;
	}

	public void setInfos(List<String> infos) {
		this.infos = infos;
	}
	
	public String toString(){
		String s = "";
		for(int i=0;i<infos.size();i++){
			if(infos.get(i).equals("")) s += "n ";
			else s += infos.get(i) + " ";
		}
		s += "\n";
		return s;
	}
	
}
