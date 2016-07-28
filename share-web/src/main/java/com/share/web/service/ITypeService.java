package com.share.web.service;

import com.share.support.service.IBaseService;
import com.share.web.entity.Type;

public interface ITypeService extends IBaseService<Type>{
	Boolean batchDelete(Integer...ids);
}
