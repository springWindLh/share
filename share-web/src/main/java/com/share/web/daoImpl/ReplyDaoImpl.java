package com.share.web.daoImpl;

import org.springframework.stereotype.Repository;

import com.share.support.daoImpl.BaseDaoImpl;
import com.share.web.dao.IReplyDao;
import com.share.web.entity.Reply;
@Repository("replyDao")
public class ReplyDaoImpl extends BaseDaoImpl<Reply> implements IReplyDao{

}
