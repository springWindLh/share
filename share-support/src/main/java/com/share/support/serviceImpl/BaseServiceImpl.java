package com.share.support.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.share.support.dao.IBaseDao;
import com.share.support.daoUtil.SearchCondition;
import com.share.support.service.IBaseService;

@Service("baseService")
@Transactional
public class BaseServiceImpl<T> implements IBaseService<T> {
	public IBaseDao<T, Integer> getDao() {
		throw new RuntimeException("dao not init");
	}

	@Override
	public Integer save(T entity) {
		return getDao().save(entity);
	}

	@Override
	public void delete(T entity) {
		getDao().delete(entity);
	}

	@Override
	public void update(T entity) {
		getDao().update(entity);
	}

	@Transactional(readOnly = true)
	@Override
	public T findByID(Integer id) {
		return getDao().findByID(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<T> list(SearchCondition searchCondition) {
		return getDao().list(searchCondition);
	}

	@Override
	public T merge(T entity) {
		return getDao().merge(entity);
	}

	@Override
	public void saveOrUpdate(T entity) {
		getDao().saveOrUpdate(entity);
	}

	@Override
	public void persist(T entity) {
		getDao().persist(entity);
	}

	@Override
	public Integer deleteByIds(Integer... ids) {
		return getDao().deleteByIds(ids);
	}

	@Override
	public Integer count(SearchCondition searchCondition) {
		return getDao().count(searchCondition);
	}

}
