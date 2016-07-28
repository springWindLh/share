package com.share.web.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.share.support.dao.IBaseDao;
import com.share.support.daoUtil.Page;
import com.share.support.serviceImpl.BaseServiceImpl;
import com.share.web.dao.IExchangeStuffDao;
import com.share.web.entity.ExchangeStuff;
import com.share.web.service.IExchangeStuffService;

@Service("exchangeStuffService")
public class ExchangeStuffServiceImpl extends BaseServiceImpl<ExchangeStuff>
		implements IExchangeStuffService {
	@Autowired
	private IExchangeStuffDao exchangeStuffDao;

	@Override
	public IBaseDao<ExchangeStuff, Integer> getDao() {
		return exchangeStuffDao;
	}

	@Override
	public Boolean batchDelete(Integer... ids) {
		return 0 < exchangeStuffDao.updateField("deleted", true, ids);
	}

	@Transactional(readOnly = true)
	@Override
	public List<ExchangeStuff> search(Page page, String keyWords) {
		return exchangeStuffDao.list(page, false, false, keyWords);
	}
}
