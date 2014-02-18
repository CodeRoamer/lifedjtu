package com.lifedjtu.jw.pojos;

import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
@Access(AccessType.FIELD)
public class User extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3270505028982366472L;
	
	@Id
	private String id;
	private String studentId;
	private String password;
	private String privateKey;

	private String curSessionId;
	private Date curSessionDate;
	
	private String username;
	private String gender;
	private Date birthDate;
	private String province;
	
	private int grade;
	private String academy;
	private String major;
	private String cls;
	private String area;
	
	private String nickname;
	private boolean userReady = false;
	
	//backup feature! user location
	private double longtitude;
	private double latitude;
	
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private List<UserCourse> userCourses;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCurSessionId() {
		return curSessionId;
	}
	public void setCurSessionId(String curSessionId) {
		this.curSessionId = curSessionId;
	}
	
	public String getAcademy() {
		return academy;
	}

	public void setAcademy(String academy) {
		this.academy = academy;
	}

	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	
	public List<UserCourse> getUserCourses() {
		return userCourses;
	}

	public void setUserCourses(List<UserCourse> userCourses) {
		this.userCourses = userCourses;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Date getCurSessionDate() {
		return curSessionDate;
	}

	public void setCurSessionDate(Date curSessionDate) {
		this.curSessionDate = curSessionDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public boolean isUserReady() {
		return userReady;
	}

	public void setUserReady(boolean userReady) {
		this.userReady = userReady;
	}

	public User(){}
	
	public User(String id){
		this.id = id;
	}
	
	public User(String studentId, String password){
		this.studentId = studentId;
		this.password = password;
	}
}
