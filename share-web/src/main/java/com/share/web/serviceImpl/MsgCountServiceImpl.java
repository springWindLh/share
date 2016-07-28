package com.share.web.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.share.support.dao.IBaseDao;
import com.share.support.serviceImpl.BaseServiceImpl;
import com.share.web.dao.IMsgCountDao;
import com.share.web.entity.MsgCount;
import com.share.web.service.IMsgCountService;

@Service("msgCountService")
@Transactional
public class MsgCountServiceImpl extends BaseServiceImpl<MsgCount> implements
		IMsgCountService {
	@Autowired
	private IMsgCountDao msgCountDao;

	@Override
	public IBaseDao<MsgCount, Integer> getDao() {
		return msgCountDao;
	}

	@Transactional(readOnly = true)
	@Override
	public MsgCount findByUserId(Integer userId) {
		return msgCountDao.findByUserId(userId);
	}

}
