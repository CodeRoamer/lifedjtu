package com.lifedjtu.jw.dao;

import java.util.ArrayList;
import java.util.List;

public class ParamMapper {
	private List<String> paramNames;
	private List<Object> paramValues;
	
	private ParamMapper(){
		paramNames = new ArrayList<String>();
		paramValues = new ArrayList<Object>();
	}
	
	public static ParamMapper param(String key, Object value){
		ParamMapper paramMapper = new ParamMapper();
		paramMapper.add(key, value);
		return paramMapper;
	}
	
	public ParamMapper add(String key, Object value){
		paramNames.add(key);
		paramValues.add(value);
		return this;
	}

	public List<String> getParamNames() {
		return paramNames;
	}

	public void setParamNames(List<String> paramNames) {
		this.paramNames = paramNames;
	}

	public List<Object> getParamValues() {
		return paramValues;
	}

	public void setParamValues(List<Object> paramValues) {
		this.paramValues = paramValues;
	}
	
	public String[] getKeyArray(){
		return paramNames.toArray(new String[0]);
	}
	public Object[] getValueArray(){
		return paramValues.toArray(new Object[0]);
	}
}
