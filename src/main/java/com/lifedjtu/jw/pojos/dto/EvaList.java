package com.lifedjtu.jw.pojos.dto;

import java.util.ArrayList;
import java.util.List;

import com.lifedjtu.jw.pojos.EntityObject;

public class EvaList extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5028148300929709944L;
	private List<EvaEntry> entries;

	public List<EvaEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<EvaEntry> entries) {
		this.entries = entries;
	}

	public EvaList(List<EvaEntry> entries) {
		super();
		this.entries = entries;
	}
	
	public void addEntry(EvaEntry entry){
		entries.add(entry);
		
	}
	
	public int size(){
		return entries.size();
	}
	
	public EvaList() {
		entries = new ArrayList<EvaEntry>();
	}
}
