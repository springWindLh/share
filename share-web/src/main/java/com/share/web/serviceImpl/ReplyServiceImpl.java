package com.share.web.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.share.support.dao.IBaseDao;
import com.share.support.serviceImpl.BaseServiceImpl;
import com.share.web.dao.IReplyDao;
import com.share.web.entity.Reply;
import com.share.web.service.IReplyService;

@Service("replyService")
@Transactional
public class ReplyServiceImpl extends BaseServiceImpl<Reply> implements IReplyService{
	@Autowired
	private IReplyDao replyDao;

	@Override
	public IBaseDao<Reply, Integer> getDao() {
		return replyDao;
	}
}
