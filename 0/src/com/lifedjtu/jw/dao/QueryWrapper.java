package com.lifedjtu.jw.dao;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

@SuppressWarnings("rawtypes")
public class QueryWrapper {
	private DetachedCriteria criteria;
	private QueryWrapper(Class cls){
		criteria = DetachedCriteria.forClass(cls);
	}
	
	public static QueryWrapper from(Class cls){
		return new QueryWrapper(cls);
	}
	
	public QueryWrapper join(String mappedField, String alias){
		criteria.createAlias(mappedField, alias);
		return this;
	}
	
	public QueryWrapper addCriteria(CriteriaWrapper wrapper){
		if(wrapper!=null)
			criteria.add(wrapper.getCriteria());
		return this;
	}
	
	public QueryWrapper addProjection(ProjectionWrapper projectionWrapper){
		if(projectionWrapper!=null)
			criteria.setProjection(projectionWrapper.getProjectionList());
		return this;
	}
	
	public QueryWrapper addOrder(Sortable sortable){
		if(sortable!=null)
			for(Order order : sortable.toSort()){
				criteria.addOrder(order);
			}
		return this;
	}
	
	
	public DetachedCriteria getCriteria(){
		return criteria;
	}
}
