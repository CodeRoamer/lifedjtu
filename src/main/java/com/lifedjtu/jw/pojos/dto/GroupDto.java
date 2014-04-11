package com.lifedjtu.jw.pojos.dto;

import java.util.List;

import com.lifedjtu.jw.pojos.EntityObject;

public class GroupDto extends EntityObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1044355363658540195L;
	
	private String id;
	private String groupName;
	
	private List<String> groupMembers; //simple studentId list

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<String> getGroupMembers() {
		return groupMembers;
	}

	public void setGroupMembers(List<String> groupMembers) {
		this.groupMembers = groupMembers;
	}

	
	
}
