package com.share.web.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.share.support.daoImpl.BaseDaoImpl;
import com.share.support.daoUtil.Page;
import com.share.web.dao.IExchangeStuffDao;
import com.share.web.entity.ExchangeStuff;

@Repository("exchangeStuffDao")
public class ExchangeStuffDaoImpl extends BaseDaoImpl<ExchangeStuff> implements IExchangeStuffDao{

	@Override
	public List<ExchangeStuff> list(Page page, Boolean completed,
			Boolean deleted, String keyWords) {
		String hqlString = "FROM ExchangeStuff WHERE completed=? and deleted=? and title like ? ORDER BY createTime DESC";
		return listByHql(page, hqlString, completed, deleted, "%" + keyWords
				+ "%");
	}

}
