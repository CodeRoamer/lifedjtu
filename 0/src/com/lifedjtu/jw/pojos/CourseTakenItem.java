package com.lifedjtu.jw.pojos;

public class CourseTakenItem extends EntityObject{
	private int fromWeek;
	private int toWeek;
	private int weekday;
	private int[] takenCondition;
	private String roomName;
	public int getFromWeek() {
		return fromWeek;
	}
	public void setFromWeek(int fromWeek) {
		this.fromWeek = fromWeek;
	}
	public int getToWeek() {
		return toWeek;
	}
	public void setToWeek(int toWeek) {
		this.toWeek = toWeek;
	}
	public int getWeekday() {
		return weekday;
	}
	public void setWeekday(int weekday) {
		this.weekday = weekday;
	}
	public int[] getTakenCondition() {
		return takenCondition;
	}
	public void setTakenCondition(int[] takenCondition) {
		this.takenCondition = takenCondition;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	
}
