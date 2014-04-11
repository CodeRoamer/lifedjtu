package com.lifedjtu.jw.pojos.dto;

import com.lifedjtu.jw.pojos.EntityObject;

public class DjtuDate extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -920548212148005735L;
	private int year;
	private int month;
	private int day;
	private int schoolYear;
	private int term;
	private int week;
	private int weekDay;
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getTerm() {
		return term;
	}
	public void setTerm(int term) {
		this.term = term;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public int getWeekDay() {
		return weekDay;
	}
	public void setWeekDay(int weekDay) {
		this.weekDay = weekDay;
	}
	
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	
	
	public int getSchoolYear() {
		return schoolYear;
	}
	public void setSchoolYear(int schoolYear) {
		this.schoolYear = schoolYear;
	}
	public DjtuDate(int year, int month, int day, int schoolYear, int term, int week,
			int weekDay) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.schoolYear = schoolYear;
		this.term = term;
		this.week = week;
		this.weekDay = weekDay;
	}
	public DjtuDate() {}
}
