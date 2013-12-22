package com.lifedjtu.jw.pojos.dto;

import java.util.List;

import com.lifedjtu.jw.pojos.EntityObject;


public class StudentRegistry extends EntityObject{
	private List<String> titles;
	private List<String> values;
	public StudentRegistry(List<String> titles, List<String> values) {
		super();
		this.titles = titles;
		this.values = values;
	}
	public List<String> getTitles() {
		return titles;
	}
	public void setTitles(List<String> titles) {
		this.titles = titles;
	}
	public List<String> getValues() {
		return values;
	}
	public void setValues(List<String> values) {
		this.values = values;
	}
	
	public String toString(){
		String s = "";
		for(int i=0;i<titles.size();i++){
			s += titles.get(i) + ":" + values.get(i) + "\n";
		}
		return s;
	}
	
}
