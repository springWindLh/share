package com.share.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.share.web.entity.MsgCount;
import com.share.web.service.IMsgCountService;

@Controller
@Scope("prototype")
@RequestMapping("/msgCount")
public class MsgCountController extends BaseController {
	@Autowired
	private IMsgCountService msgCountService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView get(@PathVariable Integer id) {
		MsgCount msgCount = msgCountService.findByUserId(id);
		return modelAndView(msgCount);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ModelAndView delete(@PathVariable Integer id) {
		MsgCount msgCount = msgCountService.findByID(id);
		msgCountService.update(msgCount);
		return modelAndView(true);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ModelAndView update(@RequestBody MsgCount msgCount) {
		msgCountService.update(msgCount);
		return modelAndView(true);
	}

}
