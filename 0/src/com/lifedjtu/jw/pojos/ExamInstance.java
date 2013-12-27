package com.lifedjtu.jw.pojos;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class ExamInstance extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7828941619416271997L;

	private String examInstanceId;
	@Id
	private String id;
	@OneToOne
	@JoinColumn(name="courseInstanceId")
	private CourseInstance courseInstance;
	private String courseName;
	@OneToOne
	@JoinColumn(name="roomId")
	private Room room; //可以通过这个外键连接教室表，获取教室的详细信息，例如位置和大小
	private String roomName;
	private boolean scoreOut;
	
	private Date examDate;
	private int lastedMinutes;
	private String examStatus;
	
	
	@OneToMany(mappedBy="examInstance",fetch=FetchType.LAZY)
	private List<UserCourse> userCourses;
	
	
	
	public int getLastedMinutes() {
		return lastedMinutes;
	}
	public void setLastedMinutes(int lastedMinutes) {
		this.lastedMinutes = lastedMinutes;
	}
	public boolean isScoreOut() {
		return scoreOut;
	}
	public void setScoreOut(boolean scoreOut) {
		this.scoreOut = scoreOut;
	}
	public String getExamInstanceId() {
		return examInstanceId;
	}
	public void setExamInstanceId(String examInstanceId) {
		this.examInstanceId = examInstanceId;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public CourseInstance getCourseInstance() {
		return courseInstance;
	}
	public void setCourseInstance(CourseInstance courseInstance) {
		this.courseInstance = courseInstance;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public Date getExamDate() {
		return examDate;
	}
	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}
	public String getExamStatus() {
		return examStatus;
	}
	public void setExamStatus(String examStatus) {
		this.examStatus = examStatus;
	}
	public List<UserCourse> getUserCourses() {
		return userCourses;
	}
	public void setUserCourses(List<UserCourse> userCourses) {
		this.userCourses = userCourses;
	}
	
	
}
