package com.lifedjtu.jw.pojos;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class InstantMessage extends EntityObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4475605610228911551L;
	@Id
	private String id;
	
	//destination只能是studentId
	private String messageDes;
	//Source也只能是studentId
	private String messageSource;
	
	//可以是任意的，如果用户不指定，默认是学号
	private String givenName;
	
	//标记消息是否来自群组
	@Column(name="imGroupFlag")
	private boolean imGroupFlag;
	
	@ManyToOne
	@JoinColumn(name="imGroupId")
	private IMGroup imGroup;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date messageDate;
	
	private String messageContent;
	
	//已读为1，未读为0
	private boolean readFlag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessageDes() {
		return messageDes;
	}

	public void setMessageDes(String messageDes) {
		this.messageDes = messageDes;
	}

	public String getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(String messageSource) {
		this.messageSource = messageSource;
	}

	public boolean getImGroupFlag() {
		return imGroupFlag;
	}

	public void setImGroupFlag(boolean imGroupFlag) {
		this.imGroupFlag = imGroupFlag;
	}

	public IMGroup getImGroup() {
		return imGroup;
	}

	public void setImGroup(IMGroup imGroup) {
		this.imGroup = imGroup;
	}

	public Date getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}

	public boolean getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(boolean readFlag) {
		this.readFlag = readFlag;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	
	
}
