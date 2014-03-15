package com.lifedjtu.jw.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tuple implements Iterable<Object>{

private List<Object> values;
	
	public Tuple(Object[] vals){
		values = new ArrayList<Object>();
		for(Object object : vals){
			values.add(object);
		}
	}
	
	public Tuple(Object vals){
		values = new ArrayList<Object>();

		if(vals instanceof Object[]){
			for(Object object : (Object[])vals){
				values.add(object);
			}
		}else if(vals instanceof Object){
			values.add(vals);
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

	@Override
	public Iterator<Object> iterator() {
		return values.iterator();
	}
}
