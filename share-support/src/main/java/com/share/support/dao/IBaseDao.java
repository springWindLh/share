package com.share.support.dao;

import java.util.List;

import org.hibernate.Session;

import com.share.support.daoUtil.Page;
import com.share.support.daoUtil.SearchCondition;

public interface IBaseDao<T, ID> {
	/** 存储实体类 */
	Integer save(T entity);

	/** 保存或更新实体类 **/
	public T merge(T entity);

	/** 删除实体类 */
	void delete(T entity);

	/** 更新实体类 */
	void update(T entity);

	/** 存储或更新实体类 */
	void saveOrUpdate(T entity);

	/** 持久化实体类 **/
	void persist(T entity);

	/** 根据ID获取实体 */
	T findByID(ID id);

	/** 获取所有实体 */
	List<T> list(Class<T> entityClass);

	/** 带参数的hql查询 */
	List<T> listByHql(Page page, String hqlString, Object... params);

	/** 带参数的hql删除 */
	Integer deleteByHql(String hqlString, Object... params);

	/** 根据sql查询 */
	List<T> listBySql(Page page, String sqlString, Object... params);

	/** 根据sql更新 **/
	int updateBySql(String sqlString, Object... params);

	/** 根据hql更新 **/
	int updateByHql(String hqlString, Object... params);

	/** 分页查询 **/
	List<T> list(Page page);

	/** 条件查询 **/
	List<T> list(SearchCondition searchCondition);

	List<T> list(ID... ids);

	Integer deleteByIds(ID... ids);
	
	Integer batchDeleteByColumn(String column,String key,Object...params);

	/** 获取记录数 **/
	Integer count();

	Integer count(SearchCondition searchCondition);

	/** 获取session **/
	Session getSession();

	/** 根据数据库表列名更新 **/
	Integer updateColumn(String column, Object value, ID... ids);

	Integer updateColumns(ID id, Object... columnsAndValues);

	/** 根据类属性名更新 **/
	Integer updateField(String field, Object value, ID... ids);

	Integer updateFields(ID id, Object... columnsAndValues);
}