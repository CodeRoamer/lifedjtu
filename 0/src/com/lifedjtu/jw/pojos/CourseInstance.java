package com.lifedjtu.jw.pojos;

public class CourseInstance extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6529968217761140613L;
	private String id;
	private String courseName;
	private String courseRemoteId;
	private String courseAlias;
	private String courseId;
	private String teacherName;
	
	//startWeek, endWeek, weekDay, segments, roomName, roomId
	//startWeek=7&endWeek=18&weekDay=4&segments=3|4&roomName=J219&roomId=fde123dk1;next item...
	private String courseTakenInfo;
	private int year;
	private int term;
	
	private int goodEval;
	private int badEval;
	
	public CourseInstance() {}

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

	public int getGoodEval() {
		return goodEval;
	}

	public void setGoodEval(int goodEval) {
		this.goodEval = goodEval;
	}

	public int getBadEval() {
		return badEval;
	}

	public void setBadEval(int badEval) {
		this.badEval = badEval;
	}

	public String getCourseTakenInfo() {
		return courseTakenInfo;
	}

	public void setCourseTakenInfo(String courseTakenInfo) {
		this.courseTakenInfo = courseTakenInfo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseRemoteId() {
		return courseRemoteId;
	}

	public void setCourseRemoteId(String courseRemoteId) {
		this.courseRemoteId = courseRemoteId;
	}

	public String getCourseAlias() {
		return courseAlias;
	}

	public void setCourseAlias(String courseAlias) {
		this.courseAlias = courseAlias;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	
}
