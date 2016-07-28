package com.share.web.daoImpl;

import org.springframework.stereotype.Repository;

import com.share.support.daoImpl.BaseDaoImpl;
import com.share.web.dao.IMessageDao;
import com.share.web.entity.Message;
@Repository("messageDao")
public class MessageDaoImpl extends BaseDaoImpl<Message> implements IMessageDao{
	
}
