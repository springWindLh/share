package com.share.web.dao;

import java.util.List;

import com.share.support.dao.IBaseDao;
import com.share.support.daoUtil.Page;
import com.share.web.entity.ExchangeStuff;

public interface IExchangeStuffDao extends IBaseDao<ExchangeStuff, Integer> {
	List<ExchangeStuff> list(Page page,Boolean completed,Boolean deleted,String keyWords);
}
