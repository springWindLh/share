package com.share.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.share.web.entity.Message;
import com.share.web.entity.User;
import com.share.web.service.IMessageService;
import com.share.web.service.IUserService;

@Controller
@Scope("prototype")
@RequestMapping("/message")
public class MessageController extends BaseController {
	@Autowired
	private IMessageService messageService;
	@Autowired
	private IUserService userService;

	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView save(@RequestBody Message message) {
		message.setCreateTime(currentTime);
		message.setSender(getCurrentUser());
		return modelAndView(messageService.save(message));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView get(@PathVariable Integer id) {
		Message message = messageService.findByID(id);
		return modelAndView(message);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ModelAndView delete(@PathVariable Integer id) {
		Message message = messageService.findByID(id);
		User owner=userService.findByID(getCurrentUserId());
		owner.getMessages().remove(message);
		messageService.update(message);
		return modelAndView(true);
	}

	@RequestMapping(value = "/{id}",method = RequestMethod.PUT)
	public ModelAndView update(@RequestBody Message message) {
		messageService.update(message);
		return modelAndView(true);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list() {
		User user = userService.findByID(getCurrentUserId());
		List<Message> messages = new ArrayList<>(user.getMessages());
		return modelAndView(messages, "messages");
	}
}