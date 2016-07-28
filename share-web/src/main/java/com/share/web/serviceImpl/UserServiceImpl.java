package com.share.web.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.share.support.dao.IBaseDao;
import com.share.support.daoUtil.Page;
import com.share.support.serviceImpl.BaseServiceImpl;
import com.share.web.dao.IUserDao;
import com.share.web.entity.MsgCount;
import com.share.web.entity.User;
import com.share.web.service.IMsgCountService;
import com.share.web.service.IUserService;

@Service("userService")
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements
		IUserService {
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IMsgCountService msgCountService;

	@Override
	public IBaseDao<User, Integer> getDao() {
		return userDao;
	}

	@Override
	public Integer save(User user) {
		Integer userId = userDao.save(user);
		MsgCount msgCount = new MsgCount(userId, 0);
		msgCountService.save(msgCount);
		return userId;
	}

	@Transactional(readOnly = true)
	@Override
	public User verify(String nameOrEmail, String password) {
		String hqlByName = "FROM User WHERE name = ? AND password = ? AND deleted=false";
		String hqlByEmail = "FROM User WHERE email = ? AND password = ? AND deleted=false";
		List<User> users = userDao.listByHql(Page.ONE, hqlByName, nameOrEmail,
				password);
		User user = null;
		if (users.size() > 0) {
			user = users.get(0);
		} else {
			users = userDao.listByHql(Page.ONE, hqlByEmail, nameOrEmail,
					password);
			if (users.size() > 0) {
				user = users.get(0);
			}
		}
		return user != null ? user : null;
	}

	@Transactional(readOnly = true)
	@Override
	public User exist(String nameOrEmail) {
		String hqlByName = "FROM User WHERE name = ?";
		String hqlByEmail = "FROM User WHERE email = ?";
		List<User> users = userDao.listByHql(Page.ONE, hqlByName, nameOrEmail);
		User user = null;
		if (users.size() > 0) {
			user = users.get(0);
		} else {
			users = userDao.listByHql(Page.ONE, hqlByEmail, nameOrEmail);
			if (users.size() > 0) {
				user = users.get(0);
			}
		}
		return user != null ? user : null;
	}

	@Transactional(readOnly = true)
	@Override
	public User findByName(String name) {
		String hql = "FROM User WHERE name = ? AND deleted=false";
		List<User> users = userDao.listByHql(Page.ONE, hql, name);
		if (users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public User findByEmail(String email) {
		String hql = "FROM User WHERE email = ? AND deleted=false";
		List<User> users = userDao.listByHql(Page.ONE, hql, email);
		if (users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public User findByPhone(String phone) {
		String hql = "FROM User WHERE phone = ? AND deleted=false";
		List<User> users = userDao.listByHql(Page.ONE, hql, phone);
		if (users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

	@Override
	public Boolean updatePassword(Integer userId, String password) {
		return userDao.updateFields(userId, "password", password) > 0;
	}

	@Override
	public Boolean batchDelete(Integer... ids) {
		return 0 < userDao.updateField("deleted", true, ids);
	}

}
