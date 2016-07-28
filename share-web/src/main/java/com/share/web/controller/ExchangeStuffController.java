package com.share.web.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.share.support.util.Jsons;
import com.share.support.util.Nums;
import com.share.web.entity.ExchangeStuff;
import com.share.web.service.IExchangeStuffService;
import com.share.web.service.ITypeService;

@Controller
@Scope("prototype")
@RequestMapping("/exchangeStuff")
public class ExchangeStuffController extends BaseController {
	@Autowired
	private IExchangeStuffService exchangeStuffService;

	@Autowired
	ITypeService typeService;

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView save() {
		Map paramsMap = Jsons.toMap(getRequestBody());
		ExchangeStuff exchangeStuff = Jsons.fromJson(ExchangeStuff.class, paramsMap.get("exchangeStuff").toString());
		if (exchangeStuff.getId() == null) {
			exchangeStuff.setSchool(getCurrentSchool());
			exchangeStuff.setCreateTime(currentTime);
			exchangeStuff.setUser(getCurrentUser());
			exchangeStuff.setCompleted(false);
			exchangeStuff.setDeleted(false);
			exchangeStuffService.save(exchangeStuff);
			if (paramsMap.get("targetId") != null) {
				Integer targetId = Nums.toInt(paramsMap.get("targetId"));
				ExchangeStuff target = exchangeStuffService.findByID(targetId);
				target.getExchangeStuffs().add(exchangeStuff);
				exchangeStuffService.update(target);
			}
			return modelAndView(exchangeStuff.getId());
		}
		if (paramsMap.get("targetId") != null) {
			Integer targetId = Nums.toInt(paramsMap.get("targetId"));
			ExchangeStuff target = exchangeStuffService.findByID(targetId);
			target.getExchangeStuffs().add(exchangeStuff);
			exchangeStuffService.update(target);
		}
		return modelAndView(exchangeStuff.getId());
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(Boolean free) {
		getSearchCondition().setWhereOperator("=");
		if (getCurrentSchoolId()!=-1) {
			getSearchCondition().setWhereQuery("school.schoolid",getCurrentSchoolId());
		}
		getSearchCondition().setWhereQuery("deleted", false);
		return modelAndView(exchangeStuffService.list(getSearchCondition()), "exchangeStuffs");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView get(@PathVariable Integer id) {
		ExchangeStuff exchangeStuff = exchangeStuffService.findByID(id);
		return modelAndView(exchangeStuff);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ModelAndView delete(@PathVariable Integer id) {
		ExchangeStuff exchangeStuff = exchangeStuffService.findByID(id);
		exchangeStuffService.delete(exchangeStuff);
		return modelAndView(true);
	}

	@RequestMapping(value = "delete_for_reply", method = RequestMethod.POST)
	public ModelAndView deleteForReply() {
		Map paramsMap = Jsons.toMap(getRequestBody());
		String targetId = paramsMap.get("targetId").toString();
		String exchangeStuffId = paramsMap.get("exchangeStuffId").toString();
		ExchangeStuff targetES = exchangeStuffService.findByID(Nums.toInt(targetId));
		ExchangeStuff exchangeStuff = exchangeStuffService.findByID(Nums.toInt(exchangeStuffId));
		targetES.getExchangeStuffs().remove(exchangeStuff);
		exchangeStuffService.update(targetES);
		return modelAndView(true);
	}

	@RequestMapping(value = "batch_delete", method = RequestMethod.POST)
	public ModelAndView batchDelete() {
		Map paramsMap = Jsons.toMap(getRequestBody());
		String ids = paramsMap.get("ids").toString();
		exchangeStuffService.batchDelete(Nums.toIntArray(ids));
		return modelAndView(true);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ModelAndView update(@RequestBody ExchangeStuff exchangeStuff) {
		exchangeStuffService.update(exchangeStuff);
		return modelAndView(true);
	}

	@RequestMapping(value = "/listReplys/{id}", method = RequestMethod.GET)
	public ModelAndView listReplys(@PathVariable Integer id) {
		ExchangeStuff exchangeStuff = exchangeStuffService.findByID(id);
		return modelAndView(new ArrayList<>(exchangeStuff.getExchangeStuffs()), "exchangeStuffRelys");
	}

	@RequestMapping(value = "/deal", method = RequestMethod.POST)
	public ModelAndView listReplys(@RequestBody ExchangeStuff dealExchangeStuff) {
		ExchangeStuff targetExchangeStuff = dealExchangeStuff.getExchangeStuffs().iterator().next();
		HashSet<ExchangeStuff> set = new HashSet<>();
		set.add(dealExchangeStuff);
		targetExchangeStuff.setExchangeStuffs(set);
		exchangeStuffService.update(targetExchangeStuff);
		exchangeStuffService.update(dealExchangeStuff);
		return modelAndView(true);
	}

	@RequestMapping(value = "search", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam String keyWords) {
		return modelAndView(exchangeStuffService.search(getPage(), keyWords), "exchangeStuffs");
	}

}
