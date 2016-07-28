package com.share.web.daoImpl;

import org.springframework.stereotype.Repository;

import com.share.support.daoImpl.BaseDaoImpl;
import com.share.web.dao.ICommentDao;
import com.share.web.entity.Comment;

@Repository("commentDao")
public class CommentDaoImpl extends BaseDaoImpl<Comment> implements ICommentDao{

}
