package com.lifedjtu.jw.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.lifedjtu.jw.dao.CriteriaWrapper;
import com.lifedjtu.jw.dao.LifeDjtuDao;
import com.lifedjtu.jw.dao.Pageable;
import com.lifedjtu.jw.dao.ParamMapper;
import com.lifedjtu.jw.dao.ProjectionWrapper;
import com.lifedjtu.jw.dao.QueryWrapper;
import com.lifedjtu.jw.dao.Sortable;
import com.lifedjtu.jw.dao.Tuple;
import com.lifedjtu.jw.dao.UpdateWrapper;
import com.lifedjtu.jw.pojos.EntityObject;

@SuppressWarnings({"unchecked","rawtypes"})
public class LifeDjtuDaoImpl<T extends EntityObject> implements LifeDjtuDao<T> {
	
	private Class<T> cls;
	protected HibernateTemplate hibernateTemplate;
	
	
	/**
	 * Default constructor. 构造函数不传参，但是很重要，为继承的子类抽出泛型的Class对象，以便于 传给DAO方法
	 */
	public LifeDjtuDaoImpl() {
		Class clazz = getClass();

		while (clazz != Object.class) {
			Type t = clazz.getGenericSuperclass();
			if (t instanceof ParameterizedType) {
				Type[] args = ((ParameterizedType) t).getActualTypeArguments();
				if (args[0] instanceof Class) {
					cls = ((Class<T>) args[0]);
					break;
				}
			}
			clazz = clazz.getSuperclass();
		}		

	}
	
	
	
	
	@Override
	public void add(T paramT) {
		hibernateTemplate.persist(paramT);
	}

	@Override
	public void addMulti(Collection<T> paramTs) {
		for(T paramT : paramTs){
			add(paramT);
		}
	}

	@Override
	public void update(T paramT) {
		hibernateTemplate.update(paramT);
	}
	
	@Override
	public void upsert(T paramT) {
		hibernateTemplate.saveOrUpdate(paramT);
	}



	@Override
	public void upsertMulti(Collection<T> paramTs) {
		hibernateTemplate.saveOrUpdateAll(paramTs);
	}

	//-----------------------------update 不稳定部分 --------------------------------
	
	@Override
	public int updateFirstById(String id, UpdateWrapper UpdateWrapper) {
		CriteriaWrapper criteriaWrapper = CriteriaWrapper.instance().and(Restrictions.eq("id", id));
		return wrapBatchUpdate(criteriaWrapper, UpdateWrapper);
	}

	@Override
	public int updateMultiByParams(CriteriaWrapper criteriaWrapper,
			UpdateWrapper UpdateWrapper) {
		return wrapBatchUpdate(criteriaWrapper, UpdateWrapper);

	}

	@Override
	public int updateMultiByIds(Collection<String> ids,
			UpdateWrapper UpdateWrapper) {
		CriteriaWrapper criteriaWrapper = CriteriaWrapper.instance().and(Restrictions.in("id", ids));
		return wrapBatchUpdate(criteriaWrapper, UpdateWrapper);
	}
	

	//-----------------------------update 不稳定部分结束 --------------------------------

	
	@Override
	public void delete(T paramT) {
		hibernateTemplate.delete(paramT);
	}

	//-----------------------------delete 不稳定部分 --------------------------------
	@Override
	public void deleteByParams(CriteriaWrapper criteriaWrapper) {
		getSession().createQuery("delete from "+cls.getSimpleName()+" where "+criteriaWrapper.getCriteria().toString()).executeUpdate();
	}
	//-----------------------------delete 不稳定部分结束 --------------------------------
	

	@Override
	public T findOneById(String id) {
		return hibernateTemplate.get(cls, id);
	}

	
	@Override
	public List<T> findAll() {
		return hibernateTemplate.loadAll(cls);
	}


	@Override
	public Tuple findOneProjectedById(String id, ProjectionWrapper projectionWrapper) {
		return wrapQueryProjectedOne(CriteriaWrapper.instance().and(Restrictions.eq("id", id)), projectionWrapper, null);
	}




	@Override
	public T findOneByParams(CriteriaWrapper criteriaWrapper) {
		return wrapQueryOne(criteriaWrapper, null, null);
	}




	@Override
	public Tuple findOneProjectedByParams(CriteriaWrapper criteriaWrapper,
			ProjectionWrapper projectionWrapper) {
		return wrapQueryProjectedOne(criteriaWrapper, projectionWrapper, null);

	}




	@Override
	public List<T> findByParams(CriteriaWrapper criteriaWrapper) {
		return wrapQueryList(criteriaWrapper, null, null, null);

	}




	@Override
	public List<T> findByParamsInOrder(CriteriaWrapper criteriaWrapper,
			Sortable sortable) {
		return wrapQueryList(criteriaWrapper, null, sortable, null);

	}




	@Override
	public List<T> findByParamsInPage(CriteriaWrapper criteriaWrapper,
			Pageable pageable) {
		return wrapQueryList(criteriaWrapper, null, null, pageable);

	}




	@Override
	public List<T> findByParamsInPageInOrder(CriteriaWrapper criteriaWrapper,
			Pageable pageable, Sortable sortable) {
		return wrapQueryList(criteriaWrapper, null, sortable, pageable);
	}




	@Override
	public List<Tuple> findProjectedByParams(CriteriaWrapper criteriaWrapper,
			ProjectionWrapper projectionWrapper) {
		return wrapTuple(wrapQueryList(criteriaWrapper, projectionWrapper, null, null));

	}




	@Override
	public List<Tuple> findProjectedByParamsInOrder(
			CriteriaWrapper criteriaWrapper,
			ProjectionWrapper projectionWrapper, Sortable sortable) {
		return wrapTuple(wrapQueryList(criteriaWrapper, projectionWrapper, sortable, null));

	}




	@Override
	public List<Tuple> findProjectedByParamsInPage(CriteriaWrapper criteriaWrapper,
			ProjectionWrapper projectionWrapper, Pageable pageable) {
		return wrapTuple(wrapQueryList(criteriaWrapper, projectionWrapper, null, pageable));

	}




	@Override
	public List<Tuple> findProjectedByParamsInPageInOrder(
			CriteriaWrapper criteriaWrapper,
			ProjectionWrapper projectionWrapper, Pageable pageable,
			Sortable sortable) {
		return wrapTuple(wrapQueryList(criteriaWrapper, projectionWrapper, sortable, pageable));
	}




	@Override
	public List<T> findByIds(String... ids) {
		return wrapQueryList(CriteriaWrapper.instance().and(Restrictions.in("id", ids)), null, null, null);
	}




	@Override
	public List<T> findByIdsInOrder(Sortable sortable, String... ids) {
		return wrapQueryList(CriteriaWrapper.instance().and(Restrictions.in("id", ids)), null, sortable, null);
	}




	@Override
	public List<T> findAllInOrder(Sortable sortable) {
		return wrapQueryList(null, null, sortable, null);
	}




	@Override
	public List<T> findAllInPage(Pageable pageable) {
		return wrapQueryList(null, null, null, pageable);

	}




	@Override
	public List<T> findAllInPageInOrder(Pageable pageable, Sortable sortable) {
		return wrapQueryList(null, null, sortable, pageable);

	}




	@Override
	public List<Tuple> findProjectedAll(String... fields) {
		return wrapTuple(wrapQueryList(null, ProjectionWrapper.instance().fields(fields), null, null));
	}




	@Override
	public List<Tuple> findProjectedAllInOrder(Sortable sortable, String... fields) {
		return wrapTuple(wrapQueryList(null, ProjectionWrapper.instance().fields(fields), sortable, null));

	}




	@Override
	public List<Tuple> findProjectedAll(ProjectionWrapper projectionWrapper) {
		return wrapTuple(wrapQueryList(null, projectionWrapper, null, null));

	}




	@Override
	public List<Tuple> findProjectedAllInOrder(ProjectionWrapper projectionWrapper,
			Sortable sortable) {
		return wrapTuple(wrapQueryList(null, projectionWrapper, sortable, null));

	}




	@Override
	public List<Tuple> findProjectedAllInPage(Pageable pageable, String... fields) {
		return wrapTuple(wrapQueryList(null, ProjectionWrapper.instance().fields(fields), null, pageable));
	}




	@Override
	public List<Tuple> findProjectedAllInPageInOrder(Pageable pageable,
			Sortable sortable, String... fields) {
		return wrapTuple(wrapQueryList(null, ProjectionWrapper.instance().fields(fields), sortable, pageable));
	}




	@Override
	public List<Tuple> findProjectedAllInPage(Pageable pageable,
			ProjectionWrapper projectionWrapper) {
		return wrapTuple(wrapQueryList(null, projectionWrapper, null, pageable));

	}

	@Override
	public List<Tuple> findProjectedAllInPageInOrder(Pageable pageable,
			ProjectionWrapper projectionWrapper, Sortable sortable) {
		return wrapTuple(wrapQueryList(null, projectionWrapper, sortable, pageable));
	}
	

	@Override
	public List<T> findByJoinedParams(Map<String, String> propPair, CriteriaWrapper criteriaWrapper) {
		return wrapJoinedQueryList(propPair, criteriaWrapper, null, null, null);
	}

	@Override
	public T findOneByJoinedParams(Map<String, String> propPair, CriteriaWrapper criteriaWrapper) {
		return wrapJoinedQueryOne(propPair, criteriaWrapper, null, null);
	}
	

	@Override
	public List<T> findByJoinedParamsInPage(Map<String, String> propPair,
			CriteriaWrapper criteriaWrapper, Pageable pageable) {
		return wrapJoinedQueryList(propPair, criteriaWrapper, null, null, pageable);
	}



	@Override
	public List<T> findByJoinedParamsInOrder(Map<String, String> propPair,
			CriteriaWrapper criteriaWrapper, Sortable sortable) {
		return wrapJoinedQueryList(propPair, criteriaWrapper, null, sortable, null);
	}



	@Override
	public List<T> findByJoinedParamsInPageInOrder(
			Map<String, String> propPair, CriteriaWrapper criteriaWrapper,
			Pageable pageable, Sortable sortable) {
		return wrapJoinedQueryList(propPair, criteriaWrapper, null, sortable, pageable);
	}



	@Override
	public List<Tuple> findProjectedByJoinedParams(
			Map<String, String> propPair, CriteriaWrapper criteriaWrapper,
			ProjectionWrapper projectionWrapper) {
		return wrapTuple(wrapJoinedQueryList(propPair, criteriaWrapper, projectionWrapper, null, null));
	}



	@Override
	public List<Tuple> findProjectedByJoinedParamsInPage(
			Map<String, String> propPair, CriteriaWrapper criteriaWrapper,
			ProjectionWrapper projectionWrapper, Pageable pageable) {
		return wrapTuple(wrapJoinedQueryList(propPair, criteriaWrapper, projectionWrapper, null, pageable));
	}



	@Override
	public List<Tuple> findProjectedByJoinedParamsInOrder(
			Map<String, String> propPair, CriteriaWrapper criteriaWrapper,
			ProjectionWrapper projectionWrapper, Sortable sortable) {
		return wrapTuple(wrapJoinedQueryList(propPair, criteriaWrapper, projectionWrapper, sortable, null));

	}



	@Override
	public List<Tuple> findProjectedByJoinedParamsInPageInOrder(
			Map<String, String> propPair, CriteriaWrapper criteriaWrapper,
			ProjectionWrapper projectionWrapper, Pageable pageable,
			Sortable sortable) {
		return wrapTuple(wrapJoinedQueryList(propPair, criteriaWrapper, projectionWrapper, sortable, pageable));
	}


	@Override
	public Tuple findOneProjectedByJoinedParams(Map<String, String> propPair,
			CriteriaWrapper criteriaWrapper, ProjectionWrapper projectionWrapper) {
		return wrapJoinedQueryProjectedOne(propPair, criteriaWrapper, projectionWrapper, null);
	}


	@Override
	public Tuple findOneProjectedByJoinedParamsInOrder(
			Map<String, String> propPair, CriteriaWrapper criteriaWrapper,
			ProjectionWrapper projectionWrapper, Sortable sortable) {
		return wrapJoinedQueryProjectedOne(propPair, criteriaWrapper, projectionWrapper, sortable);
	}
	
	@Override
	public List findByNamedQuery(String queryName, ParamMapper paramMapper) {
		return hibernateTemplate.findByNamedQueryAndNamedParam(queryName, paramMapper.getKeyArray(), paramMapper.getValueArray());
	}
	
	@Override
	public Class<T> getParameterizedClass() {
		return cls;
	}

	@Override
	public long getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getCountByParam(CriteriaWrapper criteriaWrapper) {
		// TODO Auto-generated method stub
		return 0;
	}

   
	private int wrapBatchUpdate(CriteriaWrapper criteriaWrapper, UpdateWrapper updateWrapper){
		return getSession().createQuery(updateWrapper.getUpdate(cls, criteriaWrapper)).executeUpdate();
	}
	
	/**
	 * 利用QueryWrapper生成可以被spring的hibernateTemplate所利用的DetachedCriteria对象
	 * @param criteriaWrapper
	 * @param projectionWrapper
	 * @param sortable
	 * @param pageable
	 * @return 查询结合List
	 */
	private List wrapQueryList(CriteriaWrapper criteriaWrapper, ProjectionWrapper projectionWrapper, Sortable sortable, Pageable pageable){
		DetachedCriteria detachedCriteria = QueryWrapper.from(cls).addCriteria(criteriaWrapper).addProjection(projectionWrapper).addOrder(sortable).getCriteria();
		if(pageable==null){
			return hibernateTemplate.findByCriteria(detachedCriteria); 
		}else{
			return hibernateTemplate.findByCriteria(detachedCriteria, pageable.getOffset(), pageable.getPageSize());
		}
	}
	
	private List<Tuple> wrapTuple(List rawList){
		List<Tuple> tuples = new ArrayList<Tuple>();
		for(Object object : rawList){
			tuples.add(new Tuple(object));
		}
		return tuples;
	}

	private T wrapQueryOne(CriteriaWrapper criteriaWrapper, ProjectionWrapper projectionWrapper, Sortable sortable){
		List<T> list =  wrapQueryList(criteriaWrapper, projectionWrapper, sortable, Pageable.inPage(0,1));
		if(list!=null&&list.size()!=0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	private Tuple wrapQueryProjectedOne(CriteriaWrapper criteriaWrapper, ProjectionWrapper projectionWrapper, Sortable sortable){
		List list =  wrapQueryList(criteriaWrapper, projectionWrapper, sortable, Pageable.inPage(0,1));
		if(list!=null&&list.size()!=0){
			return new Tuple((Object[])list.get(0));
		}else{
			return null;
		}
	}

	private List wrapJoinedQueryList(Map<String, String> propPair, CriteriaWrapper criteriaWrapper, ProjectionWrapper projectionWrapper, Sortable sortable, Pageable pageable){
		DetachedCriteria detachedCriteria = QueryWrapper.from(cls).join(propPair).addCriteria(criteriaWrapper).addProjection(projectionWrapper).addOrder(sortable).getCriteria();
		if(pageable==null){
			return hibernateTemplate.findByCriteria(detachedCriteria); 
		}else{
			return hibernateTemplate.findByCriteria(detachedCriteria, pageable.getOffset(), pageable.getPageSize());
		}
	} 
	
	private T wrapJoinedQueryOne(Map<String, String> propPair, CriteriaWrapper criteriaWrapper, ProjectionWrapper projectionWrapper, Sortable sortable){
		List<T> list =  wrapJoinedQueryList(propPair, criteriaWrapper, projectionWrapper, sortable, Pageable.inPage(0,1));
		if(list!=null&&list.size()!=0){
			return list.get(0);
		}else{
			return null;
		}
	} 

	private Tuple wrapJoinedQueryProjectedOne(Map<String, String> propPair, CriteriaWrapper criteriaWrapper, ProjectionWrapper projectionWrapper, Sortable sortable){
		List list =  wrapJoinedQueryList(propPair, criteriaWrapper, projectionWrapper, sortable, Pageable.inPage(0,1));
		if(list!=null&&list.size()!=0){
			return new Tuple((Object[])list.get(0));
		}else{
			return null;
		}
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate){
		this.hibernateTemplate = hibernateTemplate;
	}


	public Session getSession() {
		return hibernateTemplate.getSessionFactory().getCurrentSession();
	}


	
}


