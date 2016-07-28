package com.share.web.service;

import com.share.support.service.IBaseService;
import com.share.web.entity.Message;

public interface IMessageService extends IBaseService<Message>{
Integer save(Message message);
}
