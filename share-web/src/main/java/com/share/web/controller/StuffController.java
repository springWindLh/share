package com.share.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.share.support.daoUtil.Page;
import com.share.support.util.Jsons;
import com.share.support.util.Nums;
import com.share.web.entity.Comment;
import com.share.web.entity.Stuff;
import com.share.web.entity.Type;
import com.share.web.service.IStuffService;
import com.share.web.service.ITypeService;

@Controller
@Scope("prototype")
@RequestMapping("/stuff")
public class StuffController extends BaseController {
	@Autowired
	private IStuffService stuffService;
	@Autowired private ITypeService typeService;

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView save(@RequestBody Stuff stuff) {
		stuff.setSchool(getCurrentSchool());
		stuff.setCreateTime(currentTime);
		stuff.setUser(getCurrentUser());
		stuff.setCompleted(false);
		stuff.setDeleted(false);
		stuffService.save(stuff);
		return modelAndView(true);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(Boolean free) {
		getSearchCondition().setWhereOperator("=");
		if (free != null) {
			getSearchCondition().setWhereQuery("free", free);
		}
		if (getCurrentSchoolId()!=-1) {
			getSearchCondition().setWhereQuery("school.schoolid",getCurrentSchoolId());
		}
		getSearchCondition().setWhereQuery("deleted", false);
		getSearchCondition().setOrderBy("createTime");
		getSearchCondition().setSort("DESC");
		getJSON().exclusion(Comment.class, "replies");
		return modelAndView(stuffService.list(getSearchCondition()), "stuffs");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView get(@PathVariable Integer id) {
		Stuff stuff = stuffService.findByID(id);
		return modelAndView(stuff, getJSON());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ModelAndView delete(@PathVariable Integer id) {
		Stuff stuff = stuffService.findByID(id);
		stuffService.delete(stuff);
		return modelAndView(true);
	}

	@RequestMapping(value = "batch_delete", method = RequestMethod.POST)
	public ModelAndView batchDelete() {
		Map paramsMap = Jsons.toMap(getRequestBody());
		String ids = paramsMap.get("ids").toString();
		stuffService.batchDelete(Nums.toIntArray(ids));
		return modelAndView(true);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ModelAndView update(@RequestBody Stuff stuff) {
		stuffService.update(stuff);
		return modelAndView(true);
	}

	@RequestMapping(value = "search", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam String keyWords) {
		return modelAndView(stuffService.search(getPage(), keyWords,getCurrentSchoolId()), "stuffs");
	}
	

	@RequestMapping(value = "count_by_type", method = RequestMethod.GET)
	public ModelAndView countBySex() {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map;
		getSearchCondition().setPage(Page.MAX);
		List<Type> types=typeService.list(getSearchCondition());
		for (Type type : types) {
			getSearchCondition().setWhereOperator("=");
			getSearchCondition().setWhereQuery("type.name", type.getName());
			Integer count=stuffService.count(getSearchCondition());
			map = new HashMap<>();
			map.put("name",type.getName());
			map.put("value", count);
			map.put("color","rgb("+new Random().nextInt(256)+","+new Random().nextInt(256)+","+new Random().nextInt(256)+")");
			list.add(map);
		}
		return modelAndView(list, "stuffTypeCountData");
	}
}