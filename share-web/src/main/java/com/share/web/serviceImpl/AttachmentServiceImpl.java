package com.share.web.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.share.support.dao.IBaseDao;
import com.share.support.serviceImpl.BaseServiceImpl;
import com.share.web.dao.IAttachmentDao;
import com.share.web.entity.Attachment;
import com.share.web.service.IAttachmentService;

@Service("attachmentService")
@Transactional
public class AttachmentServiceImpl extends BaseServiceImpl<Attachment>
		implements IAttachmentService {
	@Autowired
	private IAttachmentDao attachmentDao;

	@Override
	public IBaseDao<Attachment, Integer> getDao() {
		return attachmentDao;
	}
}
