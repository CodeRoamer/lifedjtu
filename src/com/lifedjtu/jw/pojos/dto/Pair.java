package com.lifedjtu.jw.pojos.dto;

import com.lifedjtu.jw.pojos.EntityObject;

public class Pair<T,U> extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5929014279995004731L;
	private T key;
	private U value;
	public T getKey() {
		return key;
	}
	public void setKey(T key) {
		this.key = key;
	}
	public U getValue() {
		return value;
	}
	public void setValue(U value) {
		this.value = value;
	}
	public Pair(T key, U value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	public Pair() {}
	
	
}
