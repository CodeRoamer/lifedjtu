package com.lifedjtu.jw.pojos;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class IMGroup extends EntityObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7192873268752846281L;

	@Id
	private String id;
	
	//groupFlag can be 0 or 1, means sameClass or sameCourse
	private int groupFlag;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="courseInstanceId",nullable=true)
	private CourseInstance courseInstance;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="courseId",nullable=true)
	private Course course;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getGroupFlag() {
		return groupFlag;
	}
	public void setGroupFlag(int groupFlag) {
		this.groupFlag = groupFlag;
	}
	public CourseInstance getCourseInstance() {
		return courseInstance;
	}
	public void setCourseInstance(CourseInstance courseInstance) {
		this.courseInstance = courseInstance;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	
	
}
