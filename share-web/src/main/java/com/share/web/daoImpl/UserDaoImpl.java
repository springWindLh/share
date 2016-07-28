package com.share.web.daoImpl;

import org.springframework.stereotype.Repository;

import com.share.support.daoImpl.BaseDaoImpl;
import com.share.web.dao.IUserDao;
import com.share.web.entity.User;
@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao{

}
