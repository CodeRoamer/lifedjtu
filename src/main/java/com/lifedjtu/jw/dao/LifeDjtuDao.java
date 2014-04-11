package com.lifedjtu.jw.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.lifedjtu.jw.pojos.EntityObject;

public interface LifeDjtuDao<T extends EntityObject> {

	/**
	 * 添加一个entity
	 * @param paramT entity
	 */
	void add(T paramT);
	/**
	 * 添加多个实体
	 * @param paramTs 多个entity
	 */
	void addMulti(Collection<T> paramTs);
	
	/**
	 * update一个entity，使用save来执行update操作，会完全覆盖所有已知字段
	 * @param paramT
	 */
	void update(T paramT);

	/**
	 * update查找到的第一个符合条件的实体
	 * @param id 实体id
	 * @param UpdateWrapper update操作
	 * @return 更新实体个数
	 */
	int updateFirstById(String id, UpdateWrapper UpdateWrapper); 
	
	/**
	 * 
	 * @param criteriaWrapper
	 * @param UpdateWrapper
	 * @return
	 */
	int updateMultiByParams(CriteriaWrapper criteriaWrapper, UpdateWrapper UpdateWrapper);
	/**
	 * 
	 * @param ids
	 * @param UpdateWrapper
	 * @return
	 */
	int updateMultiByIds(Collection<String> ids, UpdateWrapper UpdateWrapper);

	/**
	 * 尝试更新一个实体，如果实体不存在，添加它
	 * @param paramT entity
	 * @return
	 */
	void upsert(T paramT);
	/**
	 * 
	 * @param paramTs
	 * @return
	 */
	void upsertMulti(Collection<T> paramTs);
	
	/**
	 * 删除一个entity
	 * @param paramT 主键值不为null的实体
	 */
	void delete(T paramT);
	
	
	/**
	 * 根据传递个命名参数来查找若干符合条件的记录，然后执行delete
	 * @param criteriaWrapper 查询条件
	 */
	void deleteByParams(CriteriaWrapper criteriaWrapper);
	
	/**
	 * 根据ID来查一个entity
	 * @param id
	 * @return
	 */
	T findOneById(String id);
	/**
	 * 
	 * @param id
	 * @param projectionWrapper
	 * @return
	 */
	Tuple findOneProjectedById(String id, ProjectionWrapper projectionWrapper);
	
	/**
	 * 根据传递个命名参数来查找符合条件的表记录，只返回第一个符合条件的
	 * @param criteriaWrapper
	 * @return
	 */
	T findOneByParams(CriteriaWrapper criteriaWrapper);
	/**
	 * 
	 * @param criteriaWrapper
	 * @param projectionWrapper
	 * @return
	 */
	Tuple findOneProjectedByParams(CriteriaWrapper criteriaWrapper, ProjectionWrapper projectionWrapper);

	
	/**
	 * 根据传递个命名参数来查找符合条件的表记录，返回多个对象
	 * @param criteriaWrapper
	 * @return
	 */
	List<T> findByParams(CriteriaWrapper criteriaWrapper);
	/**
	 * 
	 * @param criteriaWrapper
	 * @param sortable
	 * @return
	 */
	List<T> findByParamsInOrder(CriteriaWrapper criteriaWrapper, Sortable sortable);
	
	/**
	 * 
	 * @param criteriaWrapper
	 * @param pageable
	 * @return
	 */
	List<T> findByParamsInPage(CriteriaWrapper criteriaWrapper, Pageable pageable);
	
	/**
	 * 
	 * @param criteriaWrapper
	 * @param pageable
	 * @param sortable
	 * @return
	 */
	List<T> findByParamsInPageInOrder(CriteriaWrapper criteriaWrapper, Pageable pageable, Sortable sortable);

	/**
	 * 投影查询，下同，返回一个Tuple的集合
	 * @param criteriaWrapper
	 * @param projectionWrapper
	 * @return
	 */
	List<Tuple> findProjectedByParams(CriteriaWrapper criteriaWrapper, ProjectionWrapper projectionWrapper);
	/**
	 * 
	 * @param criteriaWrapper
	 * @param projectionWrapper
	 * @param sortable
	 * @return
	 */
	List<Tuple> findProjectedByParamsInOrder(CriteriaWrapper criteriaWrapper, ProjectionWrapper projectionWrapper, Sortable sortable);

	/**
	 * 
	 * @param criteriaWrapper
	 * @param projectionWrapper
	 * @param pageable
	 * @return
	 */
	List<Tuple> findProjectedByParamsInPage(CriteriaWrapper criteriaWrapper, ProjectionWrapper projectionWrapper, Pageable pageable);
	
	/**
	 * 
	 * @param criteriaWrapper
	 * @param projectionWrapper
	 * @param pageable
	 * @param sortable
	 * @return
	 */
	List<Tuple> findProjectedByParamsInPageInOrder(CriteriaWrapper criteriaWrapper, ProjectionWrapper projectionWrapper, Pageable pageable, Sortable sortable);

	
	/**
	 * 根据传递多个ID来查找符合条件的表记录，返回多个对象
	 * @param ids 不定参数，主键
	 * @return 实体集合
	 */
	List<T> findByIds(String... ids);
	
	/**
	 * 
	 * @param sortable
	 * @param ids
	 * @return
	 */
	List<T> findByIdsInOrder(Sortable sortable, String... ids);

	/**
	 * 返回某一个表的全部实体
	 * @return
	 */
	List<T> findAll();
	
	/**
	 * 
	 * @param sortable
	 * @return
	 */
	List<T> findAllInOrder(Sortable sortable);

	/**
	 * 
	 * @param pageable
	 * @return
	 */
	List<T> findAllInPage(Pageable pageable);
	
	/**
	 * 
	 * @param pageable
	 * @param sortable
	 * @return
	 */
	List<T> findAllInPageInOrder(Pageable pageable, Sortable sortable);

	
	/**
	 * 采用Criteria API，返回Tuple对象，通过调用tuple.get("field_name", Class<field_type> cls);来获取对应的字段值
	 * String不定参在这里指定需要投射的字段，
	 * @param fields
	 * @return
	 */
	List<Tuple> findProjectedAll(String... fields);
	
	/**
	 * 
	 * @param sortable
	 * @param fields
	 * @return
	 */
	List<Tuple> findProjectedAllInOrder(Sortable sortable, String... fields);

	/**
	 * 
	 * @param projectionWrapper
	 * @return
	 */
	List<Tuple> findProjectedAll(ProjectionWrapper projectionWrapper);
	
	/**
	 * 
	 * @param projectionWrapper
	 * @param sortable
	 * @return
	 */
	List<Tuple> findProjectedAllInOrder(ProjectionWrapper projectionWrapper, Sortable sortable);

	
	/**
	 * 
	 * @param pageable
	 * @param fields
	 * @return
	 */
	List<Tuple> findProjectedAllInPage(Pageable pageable,String... fields);
	
	/**
	 * 
	 * @param pageable
	 * @param sortable
	 * @param fields
	 * @return
	 */
	List<Tuple> findProjectedAllInPageInOrder(Pageable pageable,Sortable sortable, String... fields);

	/**
	 * 
	 * @param pageable
	 * @param projectionWrapper
	 * @return
	 */
	List<Tuple> findProjectedAllInPage(Pageable pageable, ProjectionWrapper projectionWrapper);
	
	/**
	 * 
	 * @param pageable
	 * @param projectionWrapper
	 * @param sortable
	 * @return
	 */
	List<Tuple> findProjectedAllInPageInOrder(Pageable pageable, ProjectionWrapper projectionWrapper, Sortable sortable);

	/**
	 * 表联接查询，利用jpa实体中的annotation 
	 * @param propPair
	 * @param criteriaWrapper
	 * @return
	 */
	List<T> findByJoinedParams(Map<String, String> propPair, CriteriaWrapper criteriaWrapper);
	
	/**
	 * 
	 * @param propPair
	 * @param criteriaWrapper
	 * @param pageable
	 * @return
	 */
	List<T> findByJoinedParamsInPage(Map<String, String> propPair, CriteriaWrapper criteriaWrapper, Pageable pageable);
	
	/**
	 * 
	 * @param propPair
	 * @param criteriaWrapper
	 * @param sortable
	 * @return
	 */
	List<T> findByJoinedParamsInOrder(Map<String, String> propPair, CriteriaWrapper criteriaWrapper, Sortable sortable);
	
	/**
	 * 
	 * @param propPair
	 * @param criteriaWrapper
	 * @param pageable
	 * @param sortable
	 * @return
	 */
	List<T> findByJoinedParamsInPageInOrder(Map<String, String> propPair, CriteriaWrapper criteriaWrapper,Pageable pageable, Sortable sortable);
	
	/**
	 * 
	 * @param propPair
	 * @param criteriaWrapper
	 * @param projectionWrapper
	 * @return
	 */
	List<Tuple> findProjectedByJoinedParams(Map<String, String> propPair, CriteriaWrapper criteriaWrapper, ProjectionWrapper projectionWrapper);
	
	/**
	 * 
	 * @param propPair
	 * @param criteriaWrapper
	 * @param projectionWrapper
	 * @param pageable
	 * @return
	 */
	List<Tuple> findProjectedByJoinedParamsInPage(Map<String, String> propPair, CriteriaWrapper criteriaWrapper, ProjectionWrapper projectionWrapper, Pageable pageable);
	
	/**
	 * 
	 * @param propPair
	 * @param criteriaWrapper
	 * @param projectionWrapper
	 * @param sortable
	 * @return
	 */
	List<Tuple> findProjectedByJoinedParamsInOrder(Map<String, String> propPair, CriteriaWrapper criteriaWrapper, ProjectionWrapper projectionWrapper, Sortable sortable);
	
	/**
	 * 
	 * @param propPair
	 * @param criteriaWrapper
	 * @param projectionWrapper
	 * @param pageable
	 * @param sortable
	 * @return
	 */
	List<Tuple> findProjectedByJoinedParamsInPageInOrder(Map<String, String> propPair, CriteriaWrapper criteriaWrapper, ProjectionWrapper projectionWrapper, Pageable pageable, Sortable sortable);

	/**
	 * 
	 * @param propPair
	 * @param criteriaWrapper
	 * @return
	 */
	T findOneByJoinedParams(Map<String, String> propPair, CriteriaWrapper criteriaWrapper);
	
	/**
	 * 
	 * @param propPair
	 * @param criteriaWrapper
	 * @param projectionWrapper
	 * @return
	 */
	Tuple findOneProjectedByJoinedParams(Map<String, String> propPair, CriteriaWrapper criteriaWrapper, ProjectionWrapper projectionWrapper);
	
	/**
	 * 
	 * @param propPair
	 * @param criteriaWrapper
	 * @param projectionWrapper
	 * @param sortable
	 * @return
	 */
	Tuple findOneProjectedByJoinedParamsInOrder(Map<String, String> propPair, CriteriaWrapper criteriaWrapper, ProjectionWrapper projectionWrapper, Sortable sortable);
	/**
	 * 利用HQL执行命名查询，传入的参数为命名查询名称以及命名参数的键值对，高效率！
	 * @param queryName
	 * @param paramMapper
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List findByNamedQuery(String queryName, ParamMapper paramMapper);
	
	/**
	 * 获取泛型类，父类调用无效，子类可调
	 * @return
	 */
	Class<T> getParameterizedClass();
	
	/**
	 * 获取数据库中数据个数
	 * @return 返回long值,代表记录总数
	 */
	long getCount();
	
	/**
	 * 获取符合某种查询条件的数据库中的记录个数
	 * @param criteriaWrapper
	 * @return
	 */
	long getCountByParam(CriteriaWrapper criteriaWrapper);
}

