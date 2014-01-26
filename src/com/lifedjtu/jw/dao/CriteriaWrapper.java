package com.lifedjtu.jw.dao;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

public class CriteriaWrapper {
	private Conjunction conjunction;
	private CriteriaWrapper(){
		conjunction = Restrictions.conjunction();
	}
	
	public static CriteriaWrapper instance(){
		return new CriteriaWrapper();
	}
	
	public static CriteriaWrapper instance(Criterion... criterion){
		CriteriaWrapper wrapper = new CriteriaWrapper();
		wrapper.and(criterion);
		return wrapper;
	}
	
	//请只是用Restrictions来获取Criterion，Projections与Example等见其他类
	public CriteriaWrapper and(Criterion... criterions){
		Conjunction conjunction = Restrictions.conjunction();
		for(Criterion criterion : criterions){
			conjunction.add(criterion);
		}
		this.conjunction.add(conjunction);
		return this;
	}
	
	//请只是用Restrictions来获取Criterion，Projections与Example等见其他类
	public CriteriaWrapper or(Criterion... criterions){
		Disjunction disjunction = Restrictions.disjunction();
		for(Criterion criterion : criterions){
			disjunction.add(criterion);
		}
		conjunction.add(disjunction);
		return this;
	}
	
	public static CriteriaWrapper and(CriteriaWrapper... criteriaWrappers){
		CriteriaWrapper criteriaWrapper = new CriteriaWrapper();
		for(CriteriaWrapper cri : criteriaWrappers){
			criteriaWrapper.getCriteria().add(cri.getCriteria());
		}
		return criteriaWrapper;
	}
	
	public static CriteriaWrapper or(CriteriaWrapper... criteriaWrappers){
		CriteriaWrapper criteriaWrapper = new CriteriaWrapper();
		Disjunction disjunction = Restrictions.disjunction();
		for(CriteriaWrapper cri : criteriaWrappers){
			disjunction.add(cri.getCriteria());
		}
		criteriaWrapper.getCriteria().add(disjunction);
		return criteriaWrapper;
	}
	
	public Conjunction getCriteria(){
		return conjunction;
	}
}
