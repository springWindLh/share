package com.share.web.dao;

import java.util.List;

import com.share.support.dao.IBaseDao;
import com.share.support.daoUtil.Page;
import com.share.web.entity.Stuff;

public interface IStuffDao extends IBaseDao<Stuff, Integer> {
	List<Stuff> list(Page page,Boolean completed,Boolean deleted,String keyWords,Integer schoolId);
}
