package com.lifedjtu.jw.pojos;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Friend extends EntityObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3739127277101009711L;
	
	@Id
	private String id;
	
	//朋友是谁
	@ManyToOne
	@JoinColumn(name="friendId")
	private User friend;
	
	private String friendStudentId;
	
	//是谁的朋友
	@ManyToOne
	@JoinColumn(name="toUserId")
	private User toUser ;
	
	private String toUserStudentId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getFriend() {
		return friend;
	}

	public void setFriend(User friend) {
		this.friend = friend;
	}

	public String getFriendStudentId() {
		return friendStudentId;
	}

	public void setFriendStudentId(String friendStudentId) {
		this.friendStudentId = friendStudentId;
	}

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	public String getToUserStudentId() {
		return toUserStudentId;
	}

	public void setToUserStudentId(String toUserStudentId) {
		this.toUserStudentId = toUserStudentId;
	}

	
}
