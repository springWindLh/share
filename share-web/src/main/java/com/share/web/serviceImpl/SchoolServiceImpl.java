package com.share.web.serviceImpl;

import com.share.support.dao.IBaseDao;
import com.share.support.daoUtil.Page;
import com.share.support.daoUtil.SearchCondition;
import com.share.support.serviceImpl.BaseServiceImpl;
import com.share.web.dao.ISchoolDao;
import com.share.web.entity.School;
import com.share.web.service.ISchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("schoolService")
@Transactional
public class SchoolServiceImpl extends BaseServiceImpl<School> implements ISchoolService {
    @Autowired
    private ISchoolDao schoolDao;

    @Override
    public IBaseDao<School, Integer> getDao() {
        return schoolDao;
    }

    @Override
    public List<String> listProvinces() {
        return schoolDao.listProvinces();
    }

    @Override
    public List<School> listSchools(String province, String schooltype) {
        SearchCondition condition = new SearchCondition();
        condition.setPage(Page.MAX);
        if (schooltype != null && !schooltype.isEmpty()) {
            condition.setWhereQuery("province", province, "schooltype", schooltype);
        } else {
            condition.setWhereQuery("province", province);
        }
        condition.setWhereOperator("=");
        return schoolDao.list(condition);
    }
}
