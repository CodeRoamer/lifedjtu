package com.lifedjtu.jw.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;

public class Sortable {
	public final static int ASCEND = 0;
	public final static int DESCEND = 1;
	
	private Map<String, Integer> sortMap = new HashMap<String, Integer>();
	
	private Sortable(){
		
	}
	
	private Sortable(String field, int sort){
		sortMap.put(field, sort);
	}
	
	public static Sortable instance(String field, int sort){
		Sortable sortable = new Sortable(field, sort);
		return sortable;
	}
	
	public Sortable and(String field, int sort){
		this.sortMap.put(field, sort);
		return this;
	}
	
	public Map<String, Integer> getSortInfo(){
		return sortMap;
	}
	
	public List<Order> toSort(){
		List<Order> list = new ArrayList<Order>();
		
		for(Map.Entry<String, Integer> entry : sortMap.entrySet()){
			Order order = null;
			if(entry.getValue()==ASCEND){
				order = Order.asc(entry.getKey());
			}else{
				order = Order.desc(entry.getKey());
			}
			
			list.add(order);
		}
		
		return list;
	}
}
