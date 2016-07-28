package com.share.web.daoImpl;

import org.springframework.stereotype.Repository;

import com.share.support.daoImpl.BaseDaoImpl;
import com.share.web.dao.IAttachmentDao;
import com.share.web.entity.Attachment;

@Repository("attachmentDao")
public class AttachmentDaoImpl extends BaseDaoImpl<Attachment> implements IAttachmentDao{

}
