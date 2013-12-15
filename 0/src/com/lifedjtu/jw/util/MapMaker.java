package com.lifedjtu.jw.util;

import java.util.HashMap;
import java.util.Map;

public class MapMaker {
	private Map<String, String> params;
	
	private MapMaker(){
		params = new HashMap<String, String>();
	}
	
	private MapMaker(String key, String value){
		params = new HashMap<String, String>();
		params.put(key, value);
	}
	
	public static MapMaker instance(String key, String value){
		return new MapMaker(key, value); 
	}
	
	public MapMaker param(String key, String value){
		params.put(key, value);
		return this;
	}
	
	public Map<String, String> toMap(){
		return params;
	}
}
