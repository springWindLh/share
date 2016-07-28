package com.share.web.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.share.support.dao.IBaseDao;
import com.share.support.serviceImpl.BaseServiceImpl;
import com.share.web.dao.IMessageDao;
import com.share.web.entity.Message;
import com.share.web.entity.MsgCount;
import com.share.web.entity.User;
import com.share.web.service.IMessageService;
import com.share.web.service.IMsgCountService;
import com.share.web.service.IUserService;

@Service("messageService")
@Transactional
public class MessageServiceImpl extends BaseServiceImpl<Message> implements
		IMessageService {
	@Autowired
	private IMessageDao messageDao;
	@Autowired
	private IUserService userService;
	@Autowired
	private IMsgCountService msgCountService;

	@Override
	public IBaseDao<Message, Integer> getDao() {
		return messageDao;
	}

	@Override
	public Integer save(Message message) {
		User sender=userService.findByID(message.getSender().getId());
		sender.getMessages().add(message);
		User accepter=userService.findByID(message.getAccepter().getId());
		accepter.getMessages().add(message);
		MsgCount msgCount=msgCountService.findByUserId(accepter.getId());
		msgCount.setCount(msgCount.getCount()+1);
		msgCountService.update(msgCount);
		return messageDao.save(message);
	}
}
