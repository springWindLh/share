package com.share.web.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.share.support.dao.IBaseDao;
import com.share.support.daoUtil.Page;
import com.share.support.serviceImpl.BaseServiceImpl;
import com.share.web.dao.IStuffDao;
import com.share.web.entity.Stuff;
import com.share.web.service.IStuffService;

@Service("stuffService")
@Transactional
public class StuffServiceImpl extends BaseServiceImpl<Stuff> implements
		IStuffService {
	@Autowired
	private IStuffDao stuffDao;

	@Override
	public IBaseDao<Stuff, Integer> getDao() {
		return stuffDao;
	}

	@Override
	public Boolean batchDelete(Integer... ids) {
		return 0 < stuffDao.updateField("deleted", true, ids);
	}

	@Transactional(readOnly=true)
	@Override
	public List<Stuff> search(Page page,String keyWords,Integer schoolId) {
		return stuffDao.list(page, false, false, keyWords,schoolId);
	}

}
