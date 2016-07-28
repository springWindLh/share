package com.share.web.service;

import java.util.List;

import com.share.support.daoUtil.Page;
import com.share.support.service.IBaseService;
import com.share.web.entity.ExchangeStuff;

public interface IExchangeStuffService extends IBaseService<ExchangeStuff>{
	Boolean batchDelete(Integer...ids);
	List<ExchangeStuff> search(Page page,String keyWords);
}
