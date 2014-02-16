package com.lifedjtu.jw.pojos;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class SystemNotice extends EntityObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7807145346068126632L;
	
	@Id
	private String id;
	@ManyToOne
	@JoinColumn(name="toUserId")
	private User toUser;
	
	private String title;
	private String content;
	
	@Temporal(TemporalType.TIME)
	private Date date;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
}
