package com.share.web.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.share.support.daoImpl.BaseDaoImpl;
import com.share.support.daoUtil.Page;
import com.share.web.dao.IStuffDao;
import com.share.web.entity.Stuff;

@Repository("stuffDao")
public class StuffDaoImpl extends BaseDaoImpl<Stuff> implements IStuffDao {

	@Override
	public List<Stuff> list(Page page, Boolean completed, Boolean deleted,
			String keyWords,Integer schoolId) {
		String hqlString;
		if (schoolId==-1) {
			hqlString= "FROM Stuff WHERE completed=? and deleted=? and title like ? ORDER BY createTime DESC";
			return listByHql(page, hqlString, completed, deleted, "%" + keyWords
					+ "%");
		}else {
			hqlString= "FROM Stuff WHERE completed=? and deleted=? and title like ? and school.schoolid=? ORDER BY createTime DESC";
			return listByHql(page, hqlString, completed, deleted, "%" + keyWords
					+ "%",schoolId);
		}
	}

}
