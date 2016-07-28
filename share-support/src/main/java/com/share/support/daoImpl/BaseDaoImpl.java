package com.share.support.daoImpl;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.springframework.beans.factory.annotation.Autowired;

import com.share.support.dao.IBaseDao;
import com.share.support.daoUtil.Page;
import com.share.support.daoUtil.SearchCondition;
import com.share.support.util.Maps;

@SuppressWarnings("unchecked")
public class BaseDaoImpl<T> implements IBaseDao<T, Integer> {

	private Class<T> entityType;

	private Class<T> getEntityType() {
		if (null == entityType) {
			entityType = (Class<T>) ((ParameterizedType) getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
		}
		return entityType;
	}

	private String getEntityName() {
		return getEntityType().getName();
	}

	private String getTableName(Class<?> entityType) {
		ClassMetadata classMetadata = sessionFactory
				.getClassMetadata(entityType);
		return ((SingleTableEntityPersister) classMetadata).getTableName();
	}

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Integer save(T entity) {
		return (Integer) getSession().save(entity);
	}

	@Override
	public void delete(T entity) {
		getSession().delete(entity);
	}

	@Override
	public void update(T entity) {
		getSession().update(entity);
	}

	@Override
	public void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
	}

	@Override
	public T findByID(Integer id) {
		return (T) getSession().get(getEntityType(), id);
	}

	@Override
	public List<T> list(Class<T> entityClass) {
		return getSession().createCriteria(entityClass).list();
	}

	@Override
	public List<T> listBySql(Page page, String sqlString, Object... params) {
		SQLQuery sqlQuery = getSession().createSQLQuery(sqlString);
		for (int i = 0; i < params.length; i++) {
			sqlQuery.setParameter(i, params[i]);
		}
		setPage(sqlQuery, page);
		return sqlQuery.list();
	}

	@Override
	public List<T> listByHql(Page page, String hqlString, Object... params) {
		Query query = getSession().createQuery(hqlString);
		setParams(query, params);
		setPage(query, page);
		return query.list();
	}

	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<T> list(Page page) {
		Query query = getSession().createQuery("FROM " + getEntityName());
		setPage(query, page);
		return query.list();
	}

	public Query setPage(Query query, Page page) {
		return (page == null || page.getPageSize() < 0) ? query : query
				.setFirstResult(page.getFrom()).setMaxResults(
						page.getPageSize());
	}

	@Override
	public List<T> list(Integer... ids) {
		Query query = getSession().createQuery(
				"FROM " + getEntityName() + " WHERE id IN (:ids)");
		query.setParameterList("ids", ids);
		return query.list();
	}

	@Override
	public Integer deleteByIds(Integer... ids) {
		Query query = getSession().createQuery(
				"DELETE FROM " + getEntityName() + " WHERE id IN (:ids)");
		query.setParameterList("ids", ids);
		return query.executeUpdate();
	}

	@Override
	public Integer deleteByHql(String hqlString, Object... params) {
		Query query = getSession().createQuery(hqlString);
		return setParams(query, params).executeUpdate();
	}

	@Override
	public T merge(T entity) {
		return (T) getSession().merge(entity);
	}

	@Override
	public Integer updateColumn(String column, Object value, Integer... ids) {
		String sql = "UPDATE " + getTableName(getEntityType()) + " SET "
				+ column + "=:value WHERE id IN (:ids) ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter("value", value);
		sqlQuery.setParameterList("ids", ids);
		return sqlQuery.executeUpdate();
	}

	@Override
	public Integer updateColumns(Integer id, Object... columnsAndValues) {
		Map<String, Object> FieldsAndValues = setFieldsAndValues(", ",
				columnsAndValues);
		String sqlString = "UPDATE " + getTableName(getEntityType()) + " SET "
				+ FieldsAndValues.get("query") + " WHERE id = " + id + " ";
		Map<String, Object> args = (Map<String, Object>) FieldsAndValues
				.get("args");
		return updateBySql(sqlString, args);
	}

	private Map<String, Object> setFieldsAndValues(String separator,
			Object... fieldAndValues) {
		if (fieldAndValues.length >= 2) {
			StringBuffer stringBuffer = new StringBuffer();
			Map<String, Object> args = new HashMap<>();
			stringBuffer.append(fieldAndValues[0]).append(" =:$1");
			args.put("$1", fieldAndValues[1]);
			for (int i = 2; i < fieldAndValues.length - 1; i = i + 2) {
				Object field = fieldAndValues[i];
				Object value = fieldAndValues[i + 1];
				int index = (i / 2) + 1;
				stringBuffer.append(separator).append(field).append("=:$")
						.append(index).append(" ");
				args.put("$" + index, value);
			}
			return Maps.toMap("query", stringBuffer.toString(), "args", args);
		} else {
			throw new RuntimeException("参数个数必须为偶数");
		}
	}

	@Override
	public int updateBySql(String sqlString, Object... params) {
		SQLQuery sqlQuery = getSession().createSQLQuery(sqlString);
		sqlQuery = (SQLQuery) setParams(sqlQuery, params);
		return sqlQuery.executeUpdate();
	}

	@Override
	public int updateByHql(String hqlString, Object... params) {
		Query query = getSession().createQuery(hqlString);
		return setParams(query, params).executeUpdate();
	}

	private Query setParams(Query query, Object... params) {
		if (params != null && params[0] instanceof Map) {
			Map<String, Object> paramsMap = (Map<String, Object>) params[0];
			for (Entry<String, Object> entry : paramsMap.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}

		} else if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query;
	}

	@Override
	public Integer updateField(String field, Object value, Integer... ids) {
		String hql = "UPDATE " + getEntityName() + " SET " + field
				+ "=:value WHERE id IN (:ids) ";
		Query query = getSession().createQuery(hql);
		query.setParameter("value", value);
		query.setParameterList("ids", ids);
		return query.executeUpdate();
	}

	@Override
	public Integer updateFields(Integer id, Object... fieldsAndValues) {
		Map<String, Object> FieldsAndValues = setFieldsAndValues(", ",
				fieldsAndValues);
		String hqlString = "UPDATE " + getEntityName() + " SET "
				+ FieldsAndValues.get("query") + " WHERE id = " + id + " ";
		Map<String, Object> args = (Map<String, Object>) FieldsAndValues
				.get("args");
		return updateByHql(hqlString, args);
	}

	@Override
	public List<T> list(SearchCondition searchCondition) {
		String hqlQuery = searchCondition.getSelectQuery() + " FROM "
				+ getEntityName() + searchCondition.getWhereQuery()
				+ searchCondition.getOrderByQuery();
		Query query = getSession().createQuery(hqlQuery);
		searchCondition.getPage().setRecordCount(count(searchCondition));
		setPage(query, searchCondition.getPage());
		return query.list();
	}

	@Override
	public void persist(T entity) {
		getSession().persist(entity);
	}

	@Override
	public Integer count() {
		String hqlQuery = " SELECT COUNT(*) FROM " + getEntityName();
		Query query = getSession().createQuery(hqlQuery);
		return ((Number) query.uniqueResult()).intValue();
	}

	@Override
	public Integer count(SearchCondition searchCondition) {
		String hqlQuery = " SELECT COUNT(*) FROM  " + getEntityName()
				+ searchCondition.getWhereQuery();
		Query query = getSession().createQuery(hqlQuery);
		return ((Number) query.uniqueResult()).intValue();
	}

	@Override
	public Integer batchDeleteByColumn(String column, String key,
			Object... params) {
		SQLQuery sqlQuery = getSession().createSQLQuery(
				"DELETE FROM " + getTableName(getEntityType()) + " WHERE " + column + " IN (:"
						+ key + ")");
		sqlQuery.setParameterList(key, params);
		return sqlQuery.executeUpdate();
	}

}
