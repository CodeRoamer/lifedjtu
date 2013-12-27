package com.lifedjtu.jw.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.lifedjtu.jw.dao.support.UpdateEntry;


/**
 *  ParamMapper封装了多个命名参数，他的存在主要是为了快速构造命名参数串，
 *  通过调用
 *   ParamMapper.instance(key,value).add(key,value)
 *   来生成键值对，通常传递给QueryWrapper
 */
public class UpdateWrapper {
	private List<UpdateEntry> update;
	
	private UpdateWrapper() {
		update = new ArrayList<UpdateEntry>();
	}

	public UpdateWrapper inc(String key, Number value){
		update.add(new UpdateEntry(key, "inc", value+""));
		return this;
	}
	
	
	
	public UpdateWrapper set(String key, Object value ){
		update.add(new UpdateEntry(key, "set", value+""));
		return this;
	}
	
	public static UpdateWrapper instance(){
		UpdateWrapper updateWrapper = new UpdateWrapper();
		return updateWrapper;
	}

	
	public <T> String  getUpdate(Class<T> cls, CriteriaWrapper criteriaWrapper){
		Iterator<UpdateEntry> iterator = update.iterator();
		UpdateEntry entry = iterator.next();
		
		StringBuilder hql = new StringBuilder();
		hql.append("update "+cls.getSimpleName()+" set "+entry.getPropertyName()+" = "+
		((entry.getAction().equals("inc"))?entry.getPropertyName()+"+":"")+"'"+entry.getValue()+"'");
		
		while(iterator.hasNext()){
			entry = iterator.next();
			hql.append(","+entry.getPropertyName()+" = "+((entry.getAction().equals("inc"))?entry.getPropertyName()+"+":"")+"'"+entry.getValue()+"'");
		}
		hql.append(" where "+criteriaWrapper.getCriteria().toString());
		return hql.toString();
	}
}
