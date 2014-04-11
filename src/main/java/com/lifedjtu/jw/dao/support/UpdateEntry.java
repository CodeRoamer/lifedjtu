package com.lifedjtu.jw.dao.support;

public class UpdateEntry {
	private String propertyName;
	//inc, set
	private String action;
	private String value;
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public UpdateEntry(String propertyName, String action, String value) {
		super();
		this.propertyName = propertyName;
		this.action = action;
		this.value = value;
	}
	public UpdateEntry() {}
	
}
