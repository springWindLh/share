package com.share.web.service;

import com.share.support.service.IBaseService;
import com.share.web.entity.User;

public interface IUserService extends IBaseService<User> {
	
	User verify(String nameOrEmail, String password);

	User findByName(String name);

	User findByEmail(String email);

	User findByPhone(String phone);

	Boolean updatePassword(Integer userId, String password);

	User exist(String nameOrEmail);
	
	Boolean batchDelete(Integer...ids);
}
