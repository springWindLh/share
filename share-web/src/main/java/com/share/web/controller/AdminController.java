package com.share.web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.share.support.util.Md5Encrypter;
import com.share.web.entity.Role;
import com.share.web.entity.User;
import com.share.web.service.IUserService;

@Controller
@Scope("prototype")
public class AdminController extends BaseController {
	private static final Logger LOGGER = Logger.getLogger(AdminController.class);
	@Autowired
	IUserService userService;

	@RequestMapping(value = "/toAdmin", method = RequestMethod.GET)
	public String toAdmin() {
		return "redirect:/admin/login.html";
	}

	@RequestMapping(value = "admin_login", method = RequestMethod.POST)
	public ModelAndView adminLogin(@RequestBody User tempUser) {
		LOGGER.info("<" + tempUser.getName() + ">登录");
		User user = userService.verify(tempUser.getName(), Md5Encrypter.encrypt32(tempUser.getPassword()));
		if (user != null && user.getType().equals(Role.ADMIN.toString())) {
			if (!user.getValid()) {
				LOGGER.info("<" + tempUser.getName() + ">登录失败，此管理员账号状态为禁用");
				return modelAndView(false, "此账号已被禁用！");
			}
			getSession().setAttribute("admin", user);
			user.setLastLoginTime(currentTime);
			userService.update(user);
			return modelAndView(true);
		}
		return modelAndView(false);
	}

	@RequestMapping(value = "/admin_login_out", method = RequestMethod.POST)
	public ModelAndView adminLogin() {
		User admin = (User) getSession().getAttribute("admin");
		if (admin != null) {
			LOGGER.info("<" + admin.getName() + ">管理员退出");
			getSession().removeAttribute("admin");
			return modelAndView(true);
		}
		return modelAndView(false);
	}
}
