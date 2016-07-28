package com.share.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.share.support.util.Jsons;
import com.share.support.util.Nums;
import com.share.web.entity.Type;
import com.share.web.service.ITypeService;

@Controller
@Scope("prototype")
@RequestMapping("/type")
public class TypeController extends BaseController {
	@Autowired
	private ITypeService typeService;

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView save(@RequestBody Type type) {
		return modelAndView(typeService.save(type));
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list() {
		getJSON().exclusion(Type.class, "stuffs");
		return modelAndView(typeService.list(getSearchCondition()),"types");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView get(@PathVariable Integer id) {
		Type type = typeService.findByID(id);
		return modelAndView(type);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ModelAndView delete(@PathVariable Integer id) {
		Type type = typeService.findByID(id);
		typeService.delete(type);
		return modelAndView(true);
	}

	@RequestMapping(value = "/{id}",method = RequestMethod.PUT)
	public ModelAndView update(@RequestBody Type type) {
		typeService.update(type);
		return modelAndView(true);
	}
	@RequestMapping(value = "batch_delete", method = RequestMethod.POST)
	public ModelAndView batchDelete() {
		Map paramsMap = Jsons.toMap(getRequestBody());
		String ids = paramsMap.get("ids").toString();
		typeService.batchDelete(Nums.toIntArray(ids));
		return modelAndView(true);
	}
}
