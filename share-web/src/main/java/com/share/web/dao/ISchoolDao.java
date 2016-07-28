package com.share.web.dao;

import java.util.List;

import com.share.support.dao.IBaseDao;
import com.share.web.entity.School;

public interface ISchoolDao extends IBaseDao<School, Integer> {
	List<String> listProvinces();
	List<School> listSchools();
}
