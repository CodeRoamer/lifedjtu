package com.lifedjtu.jw.pojos;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class IMGroupUser extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4300739263830974691L;
	
	@Id
	private String id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="imGroupId")
	private IMGroup imGroup;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userId")
	private User user;
	
	private String studentId;

	private long timestamp;
	
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public IMGroup getImGroup() {
		return imGroup;
	}

	public void setImGroup(IMGroup imGroup) {
		this.imGroup = imGroup;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	public IMGroupUser(){
		timestamp = System.currentTimeMillis();
	}
	
}
