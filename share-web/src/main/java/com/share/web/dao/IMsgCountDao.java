package com.share.web.dao;

import com.share.support.dao.IBaseDao;
import com.share.web.entity.MsgCount;

public interface IMsgCountDao extends IBaseDao<MsgCount, Integer> {
	MsgCount findByUserId(Integer userId);
}
