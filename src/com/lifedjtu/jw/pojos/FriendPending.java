package com.lifedjtu.jw.pojos;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class FriendPending extends EntityObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 739184124186293428L;
	@Id
	private String id;
	
	@ManyToOne
	@JoinColumn(name="requestSourceId")
	private User requestSource;
	
	private String requestSourceName;
	private String requestSourceStudentId;
	
	@ManyToOne
	@JoinColumn(name="requestDesId")
	private User requestDes;
	
	private String requestDesName;
	private String requestDesStudentId;
	
	private String requestContent;

	private int requestStatus;
	//0为未读，1为已读
	private int requestSourceReadFlag;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getRequestSource() {
		return requestSource;
	}

	public void setRequestSource(User requestSource) {
		this.requestSource = requestSource;
	}

	public String getRequestSourceName() {
		return requestSourceName;
	}

	public void setRequestSourceName(String requestSourceName) {
		this.requestSourceName = requestSourceName;
	}

	public String getRequestSourceStudentId() {
		return requestSourceStudentId;
	}

	public void setRequestSourceStudentId(String requestSourceStudentId) {
		this.requestSourceStudentId = requestSourceStudentId;
	}

	public User getRequestDes() {
		return requestDes;
	}

	public void setRequestDes(User requestDes) {
		this.requestDes = requestDes;
	}

	public String getRequestDesName() {
		return requestDesName;
	}

	public void setRequestDesName(String requestDesName) {
		this.requestDesName = requestDesName;
	}

	public String getRequestDesStudentId() {
		return requestDesStudentId;
	}

	public void setRequestDesStudentId(String requestDesStudentId) {
		this.requestDesStudentId = requestDesStudentId;
	}

	public String getRequestContent() {
		return requestContent;
	}

	public void setRequestContent(String requestContent) {
		this.requestContent = requestContent;
	}

	public int getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(int requestStatus) {
		this.requestStatus = requestStatus;
	}

	public int getRequestSourceReadFlag() {
		return requestSourceReadFlag;
	}

	public void setRequestSourceReadFlag(int requestSourceReadFlag) {
		this.requestSourceReadFlag = requestSourceReadFlag;
	}
	
	
}
