package com.lifedjtu.jw.pojos.dto;

import com.lifedjtu.jw.pojos.EntityObject;

public class ScoreDto extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3356565167455893684L;
	private String year;
	private String term;
	private String courseAliasName;
	private String courseSequence;
	private String courseName;
	private String courseAttr;
	private String courseGroup;
	private String courseMarks;
	private String normalScore;
	private String finalScore;
	private String totalScore;
	private String isPostponed;
	private String courseProperty;
	private String memo;
	private String teacherName;
	private String courseCategory;
	
	public ScoreDto(String year, String term, String courseAliasName,
			String courseSequence, String courseName, String courseAttr,
			String courseGroup, String courseMarks, String normalScore,
			String finalScore, String totalScore, String isPostponed,
			String courseProperty, String memo, String teacherName,
			String courseCategory) {
		super();
		this.year = year;
		this.term = term;
		this.courseAliasName = courseAliasName;
		this.courseSequence = courseSequence;
		this.courseName = courseName;
		this.courseAttr = courseAttr;
		this.courseGroup = courseGroup;
		this.courseMarks = courseMarks;
		this.normalScore = normalScore;
		this.finalScore = finalScore;
		this.totalScore = totalScore;
		this.isPostponed = isPostponed;
		this.courseProperty = courseProperty;
		this.memo = memo;
		this.teacherName = teacherName;
		this.courseCategory = courseCategory;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getCourseAliasName() {
		return courseAliasName;
	}
	public void setCourseAliasName(String courseAliasName) {
		this.courseAliasName = courseAliasName;
	}
	public String getCourseSequence() {
		return courseSequence;
	}
	public void setCourseSequence(String courseSequence) {
		this.courseSequence = courseSequence;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseAttr() {
		return courseAttr;
	}
	public void setCourseAttr(String courseAttr) {
		this.courseAttr = courseAttr;
	}
	public String getCourseGroup() {
		return courseGroup;
	}
	public void setCourseGroup(String courseGroup) {
		this.courseGroup = courseGroup;
	}
	public String getCourseMarks() {
		return courseMarks;
	}
	public void setCourseMarks(String courseMarks) {
		this.courseMarks = courseMarks;
	}
	public String getNormalScore() {
		return normalScore;
	}
	public void setNormalScore(String normalScore) {
		this.normalScore = normalScore;
	}
	public String getFinalScore() {
		return finalScore;
	}
	public void setFinalScore(String finalScore) {
		this.finalScore = finalScore;
	}
	public String getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}
	public String getIsPostponed() {
		return isPostponed;
	}
	public void setIsPostponed(String isPostponed) {
		this.isPostponed = isPostponed;
	}
	public String getCourseProperty() {
		return courseProperty;
	}
	public void setCourseProperty(String courseProperty) {
		this.courseProperty = courseProperty;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getCourseCategory() {
		return courseCategory;
	}
	public void setCourseCategory(String courseCategory) {
		this.courseCategory = courseCategory;
	}
	@Override
	public String toString() {
		return "Score [year=" + year + ", term=" + term + ", courseAliasName="
				+ courseAliasName + ", courseSequence=" + courseSequence
				+ ", courseName=" + courseName + ", courseAttr=" + courseAttr
				+ ", courseGroup=" + courseGroup + ", courseMarks="
				+ courseMarks + ", normalScore=" + normalScore
				+ ", finalScore=" + finalScore + ", totalScore=" + totalScore
				+ ", isPostponed=" + isPostponed + ", courseProperty="
				+ courseProperty + ", memo=" + memo + ", teacherName="
				+ teacherName + ", courseCategory=" + courseCategory + "]\n";
	}
	
	
}
