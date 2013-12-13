package com.lifedjtu.jw.pojos;

public class Room extends EntityObject{
	private String id;
	private String name;
	private int seatNumber;
	private int courseQuantity;
	private int examQuantity;
	private String roomType;
	private int[] takenCondition;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}
	public int getCourseQuantity() {
		return courseQuantity;
	}
	public void setCourseQuantity(int courseQuantity) {
		this.courseQuantity = courseQuantity;
	}
	public int getExamQuantity() {
		return examQuantity;
	}
	public void setExamQuantity(int examQuantity) {
		this.examQuantity = examQuantity;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public int[] getTakenCondition() {
		return takenCondition;
	}
	public void setTakenCondition(int[] takenCondition) {
		this.takenCondition = takenCondition;
	}
	public Room(String name, int seatNumber, int courseQuantity,
			int examQuantity, String roomType, int[] takenCondition) {
		super();
		this.name = name;
		this.seatNumber = seatNumber;
		this.courseQuantity = courseQuantity;
		this.examQuantity = examQuantity;
		this.roomType = roomType;
		this.takenCondition = takenCondition;
	}
	
	public Room() {}
}
