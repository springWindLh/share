package com.share.web.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.share.support.dao.IBaseDao;
import com.share.support.serviceImpl.BaseServiceImpl;
import com.share.web.dao.ITypeDao;
import com.share.web.entity.Type;
import com.share.web.service.ITypeService;

@Service("typeService")
@Transactional
public class TypeServiceImpl extends BaseServiceImpl<Type> implements
		ITypeService {
	@Autowired
	private ITypeDao typeDao;

	@Override
	public IBaseDao<Type, Integer> getDao() {
		return typeDao;
	}

	@Override
	public Boolean batchDelete(Integer... ids) {
		return 0 < typeDao.deleteByIds(ids);
	}
}
