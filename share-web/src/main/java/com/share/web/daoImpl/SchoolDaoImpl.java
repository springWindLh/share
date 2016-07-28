package com.share.web.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import com.share.support.daoImpl.BaseDaoImpl;
import com.share.web.dao.ISchoolDao;
import com.share.web.entity.School;

@Repository("schoolDao")
public class SchoolDaoImpl extends BaseDaoImpl<School>implements ISchoolDao {

	@Override
	public List<String> listProvinces() {
		String sql = "select province from school group by province";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		return sqlQuery.list();
	}

	@Override
	public List<School> listSchools() {
		String sql = "select id,schoolname,schooltype from school";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		return sqlQuery.list();
	}
}
