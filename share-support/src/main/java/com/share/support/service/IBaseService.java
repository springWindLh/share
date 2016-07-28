package com.share.support.service;

import java.util.List;

import com.share.support.daoUtil.SearchCondition;

public interface IBaseService<T> {
	Integer save(T entity);

	void saveOrUpdate(T entity);

	void delete(T entity);
	
	Integer deleteByIds(Integer... ids);

	void update(T entity);
	
	void persist(T entity);

	T merge(T entity);

	T findByID(Integer id);

	List<T> list(SearchCondition searchCondition);
	
	Integer count(SearchCondition searchCondition);
}
