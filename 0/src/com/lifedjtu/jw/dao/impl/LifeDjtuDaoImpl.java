package com.lifedjtu.jw.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.lifedjtu.jw.dao.CriteriaWrapper;
import com.lifedjtu.jw.dao.FieldFilter;
import com.lifedjtu.jw.dao.LifeDjtuDao;
import com.lifedjtu.jw.dao.Pageable;
import com.lifedjtu.jw.dao.Sortable;
import com.lifedjtu.jw.dao.UpdateWrapper;

public class LifeDjtuDaoImpl<T> implements LifeDjtuDao<T> {
	
	private Class<T> cls;
	private HibernateTemplate hibernateTemplate;
	/**
	 * Default constructor. 构造函数不传参，但是很重要，为继承的子类抽出泛型的Class对象，以便于 传给DAO方法
	 */
	@SuppressWarnings("unchecked")
	public LifeDjtuDaoImpl() {
		@SuppressWarnings("rawtypes")
		Class clazz = getClass();

		while (clazz != Object.class) {
			Type t = clazz.getGenericSuperclass();
			if (t instanceof ParameterizedType) {
				Type[] args = ((ParameterizedType) t).getActualTypeArguments();
				if (args[0] instanceof Class) {
					this.setCls((Class<T>) args[0]);
					break;
				}
			}
			clazz = clazz.getSuperclass();
		}		

	}
	
	
	
	
	@Override
	public void add(T paramT) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(paramT);
	}

	@Override
	public void addMulti(Collection<T> paramTs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMultiOneByOne(Collection<T> paramTs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(T paramT) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int updateFirstByParams(CriteriaWrapper criteriaWrapper,
			UpdateWrapper UpdateWrapper) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateFirstByParams(String id, UpdateWrapper UpdateWrapper) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateMultiByParams(CriteriaWrapper criteriaWrapper,
			UpdateWrapper UpdateWrapper) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateMultiByParams(CriteriaWrapper criteriaWrapper,
			Pageable pageable, Sortable sortable, UpdateWrapper updateWrapper) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int upsert(String id, UpdateWrapper UpdateWrapper) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int upsert(CriteriaWrapper criteriaWrapper,
			UpdateWrapper UpdateWrapper) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(T paramT) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteByParams(CriteriaWrapper criteriaWrapper) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T findOneById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T findOneProjectedById(String id, FieldFilter fieldFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T findOneByParams(CriteriaWrapper criteriaWrapper) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T findOneProjectedByParams(CriteriaWrapper criteriaWrapper,
			FieldFilter filFieldFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findByParams(CriteriaWrapper criteriaWrapper) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findByParamsInOrder(CriteriaWrapper criteriaWrapper,
			Sortable sortable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findByParamsInPage(CriteriaWrapper criteriaWrapper,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findByParamsInPageInOrder(CriteriaWrapper criteriaWrapper,
			Pageable pageable, Sortable sortable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findProjectedByParams(CriteriaWrapper criteriaWrapper,
			FieldFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findProjectedByParamsInOrder(
			CriteriaWrapper criteriaWrapper, FieldFilter filter,
			Sortable sortable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findProjectedByParamsInPage(CriteriaWrapper criteriaWrapper,
			FieldFilter filter, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findProjectedByParamsInPageInOrder(
			CriteriaWrapper criteriaWrapper, FieldFilter filter,
			Pageable pageable, Sortable sortable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findMultiByIds(String... ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findMultiByIdsInOrder(Sortable sortable, String... ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findAllInOrder(Sortable sortable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findAllInPage(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findAllInPageInOrder(Pageable pageable, Sortable sortable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findProjectedAll(String... fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findProjectedAllInOrder(Sortable sortable, String... fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findProjectedAll(FieldFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findProjectedAllInOrder(FieldFilter filter, Sortable sortable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findProjectedAllInPage(Pageable pageable, String... fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findProjectedAllInPageInOrder(Pageable pageable,
			Sortable sortable, String... fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findProjectedAllInPage(Pageable pageable, FieldFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findProjectedAllInPageInOrder(Pageable pageable,
			FieldFilter filter, Sortable sortable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<T> getParameterizedClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getCount() {
		// TODO Auto-generated method stub
		return 0;
	}




	public Class<T> getCls() {
		return cls;
	}




	public void setCls(Class<T> cls) {
		this.cls = cls;
	}
	
}
