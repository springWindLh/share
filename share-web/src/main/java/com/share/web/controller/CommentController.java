package com.share.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.share.web.entity.Comment;
import com.share.web.entity.User;
import com.share.web.service.ICommentService;

@Controller
@Scope("prototype")
@RequestMapping("/comment")
public class CommentController extends BaseController {
	@Autowired
	private ICommentService commentService;

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView save(@RequestBody Comment comment) {
		comment.setCreateTime(currentTime);
		comment.setUser(getCurrentUser());
		commentService.save(comment);
		return modelAndView(comment);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView get(@PathVariable Integer id) {
		Comment comment = commentService.findByID(id);
		return modelAndView(comment);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ModelAndView delete(@PathVariable Integer id) {
		Comment comment = commentService.findByID(id);
		commentService.delete(comment);
		return modelAndView(true);
	}

	@RequestMapping(value = "/{id}",method = RequestMethod.PUT)
	public ModelAndView update(@RequestBody Comment comment) {
		commentService.update(comment);
		return modelAndView(true);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(@RequestParam Integer stuffId) {
		getSearchCondition().setWhereOperator("=");
		getSearchCondition().setWhereQuery("stuff.id",stuffId);
		return modelAndView(commentService.list(getSearchCondition()),"comments");
	}
	
}