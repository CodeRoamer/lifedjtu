package com.lifedjtu.jw.dao;

import java.util.ArrayList;
import java.util.List;

public class Tuple {

	private List<Object> values;
	
	public Tuple(Object[] vals){
		values = new ArrayList<Object>();
		for(Object object : vals){
			values.add(object);
		}
	}
	
	public Tuple(Object vals){
		values = new ArrayList<Object>();
		for(Object object : (Object[])vals){
			values.add(object);
		}
	}
	
	public int size(){
		return values.size();
	}
	
	public void add(Object object){
		values.add(object);
	}
	
	public Object get(int index){
		return values.get(index);
	}
	
	public String toString(){
		return values.toString();
	}
}
