package com.lifedjtu.jw.pojos.dto;

import java.util.Date;

import com.lifedjtu.jw.pojos.EntityObject;

public class ExamTimeEntry extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1331993155693057259L;
	private Date date;
	private int lastedMinutes;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getLastedMinutes() {
		return lastedMinutes;
	}
	public void setLastedMinutes(int lastedMinutes) {
		this.lastedMinutes = lastedMinutes;
	}
	public ExamTimeEntry(Date date, int lastedMinutes) {
		super();
		this.date = date;
		this.lastedMinutes = lastedMinutes;
	}
	
	public ExamTimeEntry() {}
}
