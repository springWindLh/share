package com.share.web.service;

import java.util.List;

import com.share.support.service.IBaseService;
import com.share.web.entity.School;

public interface ISchoolService extends IBaseService<School> {
	List<String> listProvinces();
	List<School> listSchools(String province,String schooltype);
}
