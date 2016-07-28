package com.share.web.service;

import java.util.List;

import com.share.support.daoUtil.Page;
import com.share.support.service.IBaseService;
import com.share.web.entity.Stuff;

public interface IStuffService extends IBaseService<Stuff> {
	Boolean batchDelete(Integer...ids);
	List<Stuff> search(Page page,String keyWords,Integer schoolId);
}
