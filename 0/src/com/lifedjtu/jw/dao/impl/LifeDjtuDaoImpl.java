package com.lifedjtu.jw.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.lifedjtu.jw.dao.CriteriaWrapper;
import com.lifedjtu.jw.dao.LifeDjtuDao;
import com.lifedjtu.jw.dao.Pageable;
import com.lifedjtu.jw.dao.ProjectionWrapper;
import com.lifedjtu.jw.dao.QueryWrapper;
import com.lifedjtu.jw.dao.Sortable;
import com.lifedjtu.jw.dao.UpdateWrapper;
import com.lifedjtu.jw.pojos.EntityObject;

@SuppressWarnings("unchecked")
public class LifeDjtuDaoImpl<T extends EntityObject> implements LifeDjtuDao<T> {
	
	private Class<T> cls;
	protected HibernateTemplate hibernateTemplate;
	//请务必使用getSession()来获取Session
	//private Session session;
	/**
	 * Default constructor. 构造函数不传参，但是很重要，为继承的子类抽出泛型的Class对象，以便于 传给DAO方法
	 */
	public LifeDjtuDaoImpl() {
		@SuppressWarnings("rawtypes")
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
	
	
	
	//OK!!!
	@Override
	public void add(T paramT) {
		hibernateTemplate.save(paramT);
	}

	@Override
	public void addMulti(Collection<T> paramTs) {
		hibernateTemplate.saveOrUpdateAll(paramTs);
	}

	@Override
	public void addMultiOneByOne(Collection<T> paramTs) {
		for(T paraT : paramTs){
			hibernateTemplate.save(paraT);
		}
	}

	@Override
	public void update(T paramT) {
		hibernateTemplate.update(paramT);
	}

	@Override
	public int updateFirstByParams(CriteriaWrapper criteriaWrapper,
			UpdateWrapper UpdateWrapper) {
		return 0;
	}

	@Override
	public int updateFirstByParams(String id, UpdateWrapper UpdateWrapper) {
		CriteriaWrapper criteriaWrapper = CriteriaWrapper.instance().and(Restrictions.eq("id", id));
		return wrapBatchUpdate(criteriaWrapper, UpdateWrapper);
	}

	@Override
	public int updateMultiByParams(CriteriaWrapper criteriaWrapper,
			UpdateWrapper UpdateWrapper) {
		return wrapBatchUpdate(criteriaWrapper, UpdateWrapper);

	}


	@Override
	public void delete(T paramT) {
		hibernateTemplate.delete(paramT);
	}

	//OK!!!
	@Override
	public void deleteByParams(CriteriaWrapper criteriaWrapper) {
		getSession().createQuery("delete from "+cls.getSimpleName()+" where "+criteriaWrapper.getCriteria().toString()).executeUpdate();
	}

	@Override
	public T findOneById(String id) {
		return hibernateTemplate.get(cls, id);
	}

	
	@Override
	public List<T> findAll() {
		return hibernateTemplate.loadAll(cls);
	}


	@Override
	public T findOneProjectedById(String id, ProjectionWrapper projectionWrapper) {
		return wrapQueryOne(CriteriaWrapper.instance().and(Restrictions.eq("id", id)), projectionWrapper, null);
	}




	@Override
	public T findOneByParams(CriteriaWrapper criteriaWrapper) {
		return wrapQueryOne(criteriaWrapper, null, null);
	}




	@Override
	public T findOneProjectedByParams(CriteriaWrapper criteriaWrapper,
			ProjectionWrapper projectionWrapper) {
		return wrapQueryOne(criteriaWrapper, projectionWrapper, null);

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
	public List<T> findProjectedByParams(CriteriaWrapper criteriaWrapper,
			ProjectionWrapper projectionWrapper) {
		return wrapQueryList(criteriaWrapper, projectionWrapper, null, null);

	}




	@Override
	public List<T> findProjectedByParamsInOrder(
			CriteriaWrapper criteriaWrapper,
			ProjectionWrapper projectionWrapper, Sortable sortable) {
		return wrapQueryList(criteriaWrapper, projectionWrapper, sortable, null);

	}




	@Override
	public List<T> findProjectedByParamsInPage(CriteriaWrapper criteriaWrapper,
			ProjectionWrapper projectionWrapper, Pageable pageable) {
		return wrapQueryList(criteriaWrapper, projectionWrapper, null, pageable);

	}




	@Override
	public List<T> findProjectedByParamsInPageInOrder(
			CriteriaWrapper criteriaWrapper,
			ProjectionWrapper projectionWrapper, Pageable pageable,
			Sortable sortable) {
		return wrapQueryList(criteriaWrapper, projectionWrapper, sortable, pageable);
	}




	@Override
	public List<T> findMultiByIds(String... ids) {
		return wrapQueryList(CriteriaWrapper.instance().and(Restrictions.in("id", ids)), null, null, null);
	}




	@Override
	public List<T> findMultiByIdsInOrder(Sortable sortable, String... ids) {
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
	public List<T> findProjectedAll(String... fields) {
		return wrapQueryList(null, ProjectionWrapper.instance().fields(fields), null, null);
	}




	@Override
	public List<T> findProjectedAllInOrder(Sortable sortable, String... fields) {
		return wrapQueryList(null, ProjectionWrapper.instance().fields(fields), sortable, null);

	}




	@Override
	public List<T> findProjectedAll(ProjectionWrapper projectionWrapper) {
		return wrapQueryList(null, projectionWrapper, null, null);

	}




	@Override
	public List<T> findProjectedAllInOrder(ProjectionWrapper projectionWrapper,
			Sortable sortable) {
		return wrapQueryList(null, projectionWrapper, sortable, null);

	}




	@Override
	public List<T> findProjectedAllInPage(Pageable pageable, String... fields) {
		return wrapQueryList(null, ProjectionWrapper.instance().fields(fields), null, pageable);
	}




	@Override
	public List<T> findProjectedAllInPageInOrder(Pageable pageable,
			Sortable sortable, String... fields) {
		return wrapQueryList(null, ProjectionWrapper.instance().fields(fields), sortable, pageable);
	}




	@Override
	public List<T> findProjectedAllInPage(Pageable pageable,
			ProjectionWrapper projectionWrapper) {
		return wrapQueryList(null, projectionWrapper, null, pageable);

	}




	@Override
	public List<T> findProjectedAllInPageInOrder(Pageable pageable,
			ProjectionWrapper projectionWrapper, Sortable sortable) {
		return wrapQueryList(null, projectionWrapper, sortable, pageable);
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


	private int wrapBatchUpdate(CriteriaWrapper criteriaWrapper, UpdateWrapper updateWrapper){
		return getSession().createQuery(updateWrapper.getUpdate(cls, criteriaWrapper)).executeUpdate();
	}
	
	private List<T> wrapQueryList(CriteriaWrapper criteriaWrapper, ProjectionWrapper projectionWrapper, Sortable sortable, Pageable pageable){
		DetachedCriteria detachedCriteria = QueryWrapper.from(cls).addCriteria(criteriaWrapper).addProjection(projectionWrapper).addOrder(sortable).getCriteria();
		if(pageable==null){
			return hibernateTemplate.findByCriteria(detachedCriteria); 
		}else{
			return hibernateTemplate.findByCriteria(detachedCriteria, pageable.getOffset(), pageable.getPageSize());
		}
	}

	private T wrapQueryOne(CriteriaWrapper criteriaWrapper, ProjectionWrapper projectionWrapper, Sortable sortable){
		List<T> list =  wrapQueryList(criteriaWrapper, projectionWrapper, sortable, Pageable.inPage(0,1));
		if(list!=null&&list.size()!=0){
			return list.get(0);
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


