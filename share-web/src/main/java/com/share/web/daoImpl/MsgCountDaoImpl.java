package com.share.web.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.share.support.daoImpl.BaseDaoImpl;
import com.share.support.daoUtil.Page;
import com.share.web.dao.IMsgCountDao;
import com.share.web.entity.MsgCount;

@Repository("msgCountDao")
public class MsgCountDaoImpl extends BaseDaoImpl<MsgCount> implements
		IMsgCountDao {

	@Override
	public MsgCount findByUserId(Integer userId) {
		String hql = "FROM MsgCount WHERE userId=?";
		List<MsgCount> msgCounts = listByHql(Page.ONE, hql, userId);
		MsgCount msgCount;
		if (msgCounts.size() > 0) {
			msgCount = msgCounts.get(0);
		} else {
			msgCount = null;
		}
		return msgCount;
	}

}
