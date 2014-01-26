package com.lifedjtu.jw.dao;

import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

public class ProjectionWrapper {
	private ProjectionList projectionList;
	private ProjectionWrapper(){
		projectionList = Projections.projectionList();
	}
	
	public static ProjectionWrapper instance(){
		return new ProjectionWrapper();
	}
	
	public static ProjectionWrapper instance(Projection... projection){
		ProjectionWrapper wrapper = new ProjectionWrapper();
		wrapper.add(projection);
		return wrapper;
	}
	
	//投影查询，返回部分字段
	public ProjectionWrapper fields(String... fieldNames){
		for(String fieldName : fieldNames){
			this.add(Projections.property(fieldName));
		}
		return this;
	}
	
	public ProjectionWrapper add(Projection... projections){
		for(Projection projection : projections){
			projectionList.add(projection);
		}
		return this;
	}
	
	public ProjectionList getProjectionList(){
		return projectionList;
	}
}
