package com.share.web.service;

import com.share.support.service.IBaseService;
import com.share.web.entity.MsgCount;

public interface IMsgCountService extends IBaseService<MsgCount> {
	MsgCount findByUserId(Integer userId);
}
