package com.lifedjtu.jw.pojos.dto;

import java.util.List;

import com.lifedjtu.jw.util.extractor.DomElement;

public class StudentRegistry {
	private List<DomElement> th;
	
	private List<DomElement> td;

	
	public StudentRegistry(List<DomElement> th, List<DomElement> td) {
		super();
		this.th = th;
		this.td = td;
	}
	
	public List<DomElement> getTh() {
		return th;
	}

	
	public void setTh(List<DomElement> th) {
		this.th = th;
	}

	public List<DomElement> getTd() {
		return td;
	}

	public void setTd(List<DomElement> td) {
		this.td = td;
	}
		
	
}
