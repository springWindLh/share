package com.share.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.share.web.entity.Reply;
import com.share.web.service.IReplyService;

@Controller
@Scope("prototype")
@RequestMapping("/reply")
public class ReplyController extends BaseController {
	@Autowired
	private IReplyService replyService;

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView save(@RequestBody Reply reply) {
		reply.setCreateTime(currentTime);
		reply.setAuthor(getCurrentUser());
		reply.setReplyer(reply.getComment().getUser());
		replyService.save(reply);
		return modelAndView(reply);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView get(@PathVariable Integer id) {
		Reply reply = replyService.findByID(id);
		return modelAndView(reply);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ModelAndView delete(@PathVariable Integer id) {
		Reply reply = replyService.findByID(id);
		replyService.delete(reply);
		return modelAndView(true);
	}

	@RequestMapping(value = "/{id}",method = RequestMethod.PUT)
	public ModelAndView update(@RequestBody Reply reply) {
		replyService.update(reply);
		return modelAndView(true);
	}
}