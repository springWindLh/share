package com.share.web.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.share.support.dao.IBaseDao;
import com.share.support.serviceImpl.BaseServiceImpl;
import com.share.web.dao.ICommentDao;
import com.share.web.entity.Comment;
import com.share.web.service.ICommentService;

@Service("commentService")
@Transactional
public class CommentServiceImpl extends BaseServiceImpl<Comment> implements ICommentService{
	@Autowired
	private ICommentDao commentDao;

	@Override
	public IBaseDao<Comment, Integer> getDao() {
		return commentDao;
	}
}
