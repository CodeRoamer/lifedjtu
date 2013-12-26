package com.lifedjtu.jw.dao;

import java.util.Collection;
import java.util.List;

import com.lifedjtu.jw.pojos.EntityObject;

public interface LifeDjtuDao<T extends EntityObject> {
	/**
	 * 添加一个entity
	 */
	void add(T paramT);
	void addMulti(Collection<T> paramTs);
	void addMultiOneByOne(Collection<T> paramTs);
	/**
	 * 	 update一个entity，使用save来执行update操作，会完全覆盖所有已知字段，不推荐使用
	 */
	void update(T paramT);

	/**
	 * 根据传递个命名参数来查找符合条件的表记录，然后执行update
	 * 根据JPQL来操作
	 * @param queryName JPQL语句名称   mapper 命名参数对象
	 */
	int updateFirstByParams(CriteriaWrapper criteriaWrapper, UpdateWrapper UpdateWrapper);
	int updateFirstByParams(String id, UpdateWrapper UpdateWrapper); 
	int updateMultiByParams(CriteriaWrapper criteriaWrapper, UpdateWrapper UpdateWrapper);
	int updateMultiByParams(CriteriaWrapper criteriaWrapper, Pageable pageable, Sortable sortable, UpdateWrapper updateWrapper);
	
	int upsert(String id, UpdateWrapper UpdateWrapper);
	int upsert(CriteriaWrapper criteriaWrapper, UpdateWrapper UpdateWrapper);
	/**
	 * 删除一个entity
	 */
	//void delete(String id);
	void delete(T paramT);
	/**
	 * 根据传递个命名参数来查找符合条件的表记录，然后执行delete
	 * 根据JPQL来操作
	 * @param queryName JPQL语句名称   mapper 命名参数对象
	 */
	void deleteByParams(CriteriaWrapper criteriaWrapper);
	
	/**
	 * 根据ID来查一个entity
	 */
	T findOneById(String id);
	T findOneProjectedById(String id, FieldFilter fieldFilter);
	
	/**
	 * 根据传递个命名参数来查找符合条件的表记录，只返回第一个符合条件的
	 * 根据JPQL来操作
	 * @param queryName JPQL语句名称   mapper 命名参数对象
	 */
	T findOneByParams(CriteriaWrapper criteriaWrapper);
	T findOneProjectedByParams(CriteriaWrapper criteriaWrapper, FieldFilter filFieldFilter);

	
	/**
	 * 根据传递个命名参数来查找符合条件的表记录，返回多个对象
	 * 根据JPQL来操作
	 * @param queryName JPQL语句名称   mapper 命名参数对象
	 */
	List<T> findByParams(CriteriaWrapper criteriaWrapper);
	List<T> findByParamsInOrder(CriteriaWrapper criteriaWrapper, Sortable sortable);
	/**
	 * 分页查询
	 */
	List<T> findByParamsInPage(CriteriaWrapper criteriaWrapper, Pageable pageable);
	List<T> findByParamsInPageInOrder(CriteriaWrapper criteriaWrapper, Pageable pageable, Sortable sortable);

	/**
	 * 投影查询，下同，返回一个DomainModel的集合
	 */
	List<T> findProjectedByParams(CriteriaWrapper criteriaWrapper, FieldFilter filter);
	List<T> findProjectedByParamsInOrder(CriteriaWrapper criteriaWrapper, FieldFilter filter, Sortable sortable);

	List<T> findProjectedByParamsInPage(CriteriaWrapper criteriaWrapper, FieldFilter filter, Pageable pageable);
	List<T> findProjectedByParamsInPageInOrder(CriteriaWrapper criteriaWrapper, FieldFilter filter, Pageable pageable, Sortable sortable);

	
	/**
	 * 根据传递多个ID来查找符合条件的表记录，返回多个对象
	 * 根据JPQL来操作
	 * @param ids 不定参数，主键
	 */
	List<T> findMultiByIds(String... ids);
	List<T> findMultiByIdsInOrder(Sortable sortable, String... ids);

	/**
	 * 返回某一个表的全部实体
	 */
	List<T> findAll();
	List<T> findAllInOrder(Sortable sortable);

	/**
	 * 分页查询
	 */
	List<T> findAllInPage(Pageable pageable);
	List<T> findAllInPageInOrder(Pageable pageable, Sortable sortable);

	
	/**
	 * 采用Criteria API，返回Tuple对象，通过调用tuple.get("field_name", Class<field_type> cls);来获取对应的字段值
	 * String不定参在这里指定需要投射的字段，
	 */
	List<T> findProjectedAll(String... fields);
	List<T> findProjectedAllInOrder(Sortable sortable, String... fields);

	List<T> findProjectedAll(FieldFilter filter);
	List<T> findProjectedAllInOrder(FieldFilter filter, Sortable sortable);

	
	/**
	 * 同上，添加分页机制
	 */
	List<T> findProjectedAllInPage(Pageable pageable,String... fields);
	List<T> findProjectedAllInPageInOrder(Pageable pageable,Sortable sortable, String... fields);

	List<T> findProjectedAllInPage(Pageable pageable, FieldFilter filter);
	List<T> findProjectedAllInPageInOrder(Pageable pageable, FieldFilter filter, Sortable sortable);


	/**
	 * 获取泛型类，父类调用无效，子类可调
	 */
	Class<T> getParameterizedClass();
	
	/*
	 * 获取数据库中数据个数
	 */
	long getCount();
}

