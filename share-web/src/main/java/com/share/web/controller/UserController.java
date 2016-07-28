package com.share.web.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.share.support.daoUtil.Page;
import com.share.support.util.Files;
import com.share.support.util.Jsons;
import com.share.support.util.Md5Encrypter;
import com.share.support.util.Nums;
import com.share.web.entity.Attachment;
import com.share.web.entity.Role;
import com.share.web.entity.School;
import com.share.web.entity.User;
import com.share.web.service.IAttachmentService;
import com.share.web.service.ISchoolService;
import com.share.web.service.IUserService;

@Controller
@Scope("prototype")
@RequestMapping("/user")
public class UserController extends BaseController {
	private static final Logger LOGGER = Logger.getLogger(UserController.class);
	@Autowired
	private IUserService userService;
	@Autowired
	private IAttachmentService attachmentService;
	@Autowired
	private ISchoolService schoolService;

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView save(@RequestBody User user) {
		LOGGER.info("<" + user.getName() + ">注册");
		if (userService.findByName(user.getName()) != null) {
			LOGGER.info("<" + user.getName() + ">注册失败->用户名重复");
			return modelAndView(false, "此用户名已被使用！");
		}
		if (userService.findByEmail(user.getEmail()) != null) {
			LOGGER.info("<" + user.getName() + ">注册失败->邮箱重复");
			return modelAndView(false, "此邮箱已被注册！");
		}
		if (userService.findByPhone(user.getPhone()) != null) {
			LOGGER.info("<" + user.getName() + ">注册失败->手机号重复");
			return modelAndView(false, "此手机号已被注册！");
		}
		user.setSchool(getCurrentSchool());
		user.setCreateTime(currentTime);
		user.setType(Role.USER.toString());
		user.setPassword(Md5Encrypter.encrypt32(user.getPassword()));
		user.setValid(true);
		user.setDeleted(false);
		return modelAndView(userService.save(user));
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list() {
		getSearchCondition().setWhereOperator("=");
		getSearchCondition().setWhereQuery("deleted", false);
		return modelAndView(userService.list(getSearchCondition()), "users");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView get(@PathVariable Integer id) {
		User user = userService.findByID(id);
		return modelAndView(user);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ModelAndView delete(@PathVariable Integer id) {
		User user = userService.findByID(id);
		userService.delete(user);
		return modelAndView(true);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ModelAndView update(@RequestBody User user) {
		LOGGER.info("<" + user.getName() + ">更新");
		userService.update(user);
		return modelAndView(true);
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ModelAndView login(@RequestBody User tempUser) {
		LOGGER.info("<" + tempUser.getName() + ">登录");
		User user = userService.verify(tempUser.getName(), Md5Encrypter.encrypt32(tempUser.getPassword()));
		if (user != null) {
			if (!user.getValid()) {
				LOGGER.info("<" + tempUser.getName() + ">登录失败->账号状态为禁用");
				return modelAndView(false, "此账号已被禁用！");
			}
			getSession().setAttribute("user", user);
			School school=schoolService.findByID(user.getSchool().getSchoolid());
			getSession().setAttribute("school", school);
			user.setLastLoginTime(currentTime);
			userService.update(user);
			return modelAndView(user.getId());
		}
		return modelAndView(false);
	}

	@RequestMapping(value = "loginOut", method = RequestMethod.POST)
	public ModelAndView loginOut() {
		User user = (User) getSession().getAttribute("user");
		if (user != null) {
			LOGGER.info("<" + user.getName() + ">退出");
			getSession().removeAttribute("user");
			getSession().removeAttribute("school");
			return modelAndView(true);
		}
		return modelAndView(false);
	}

	@RequestMapping(value = "update_password", method = RequestMethod.POST)
	public ModelAndView updatePassword() {
		Map paramsMap = Jsons.toMap(getRequestBody());
		String oldPassword = paramsMap.get("oldPassword").toString();
		String newPassword = paramsMap.get("newPassword").toString();
		User user = userService.findByID(getCurrentUserId());
		if (user != null && user.getPassword().equals(Md5Encrypter.encrypt32(oldPassword))) {
			userService.updatePassword(getCurrentUserId(), Md5Encrypter.encrypt32(newPassword));
			LOGGER.info("<" + user.getName() + ">修改密码");
			return modelAndView(true);
		} else {
			return modelAndView(false, "原密码错误！");
		}
	}

	@RequestMapping(value = "upload_headImg", method = RequestMethod.POST)
	public ModelAndView uploadHeadImg(MultipartFile file) throws FileNotFoundException {
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		if (!suffix.equalsIgnoreCase(".JPEG") && !suffix.equalsIgnoreCase(".JPG") && !suffix.equalsIgnoreCase(".GIF")
				&& !suffix.equalsIgnoreCase(".PNG")) {
			return modelAndView(false, "文件格式错误，请选择正确的图片格式文件！");
		}
		Attachment attachment = new Attachment();
		String path = getRequest().getSession().getServletContext().getRealPath("/sources/img/head");
		String headImgName = Files.setFileName(file.getOriginalFilename());
		File newFile = new File(path, headImgName);
		FileOutputStream outputStream = new FileOutputStream(newFile);
		try {
			Files.write(file.getBytes(), outputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		attachment.setCaption("用户头像#" + getCurrentUserId());
		attachment.setCreateTime(currentTime);
		attachment.setPath(headImgName);
		attachment.setSize((int) file.getSize() / 1024);
		attachmentService.save(attachment);
		getCurrentUser().setHeadImg(attachment);
		userService.update(getCurrentUser());
		return modelAndView(attachment);
	}

	@RequestMapping(value = "find_back_password", method = RequestMethod.POST)
	public ModelAndView findBackPassword() throws EmailException {
		Map paramMap = Jsons.toMap(getRequestBody());
		User user = userService.exist(paramMap.get("name").toString());
		if (user != null) {
			String pwd = new SimpleDateFormat("HHmmssSS").format(new Timestamp(System.currentTimeMillis()))
					+ new Random().nextInt(1000);
			LOGGER.info("<" + user.getName() + ">找回密码");
			SimpleEmail email = new SimpleEmail();
			email.setHostName("smtp.exmail.qq.com");
			email.setAuthentication("luohuan@zdiso.com", "297750341lh");
			email.addTo(user.getEmail());
			email.setFrom("luohuan@zdiso.com", "校园资源分享平台");
			email.setSubject("校园资源分享平台<系统邮件——找回密码>");
			email.setMsg("校园资源分享平台<找回密码>通知:亲爱的" + user.getName() + "，系统为您生成了一个随机密码：" + pwd + " 请尽快登录修改为自己熟悉的密码。");
			email.send();
			userService.updatePassword(user.getId(), Md5Encrypter.encrypt32(pwd));
			return modelAndView(true);
		}
		return modelAndView(false);
	}

	@RequestMapping(value = "batch_delete", method = RequestMethod.POST)
	public ModelAndView batchDelete() {
		Map paramsMap = Jsons.toMap(getRequestBody());
		String ids = paramsMap.get("ids").toString();
		userService.batchDelete(Nums.toIntArray(ids));
		LOGGER.info("批量删除用户，用户ID列表{" + ids + "}");
		return modelAndView(true);
	}

	@RequestMapping(value = "count_by_sex", method = RequestMethod.GET)
	public ModelAndView countBySex() {
		getSearchCondition().setWhereOperator("=");
		getSearchCondition().setWhereQuery("sex", "男");
		getSearchCondition().setPage(Page.MAX);
		Integer boyCount = userService.count(getSearchCondition());
		getSearchCondition().setWhereQuery("sex", "女");
		Integer girlCount = userService.count(getSearchCondition());
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("name", "男生");
		map.put("value", boyCount);
		map.put("color", "#6699CC");
		list.add(map);
		map = new HashMap<>();
		map.put("name", "女生");
		map.put("value", girlCount);
		map.put("color", "#FF9999");
		list.add(map);
		return modelAndView(list, "userSexCountData");
	}
}
