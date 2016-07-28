package com.share.web.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.share.support.daoUtil.Page;
import com.share.support.daoUtil.SearchCondition;
import com.share.support.util.Files;
import com.share.support.util.Json;
import com.share.support.util.Jsons;
import com.share.support.util.Maps;
import com.share.support.util.TextView;
import com.share.web.entity.School;
import com.share.web.entity.User;

public class BaseController {
	public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";
	private HttpServletRequest REQUEST;
	private HttpServletResponse RESPONSE;
	public Timestamp currentTime = new Timestamp(System.currentTimeMillis());

	public HttpServletRequest getRequest() {
		if (REQUEST == null) {
			REQUEST = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		}
		return REQUEST;
	}

	public HttpServletResponse getResponse() {
		return RESPONSE;
	}

	public HttpSession getSession() {
		return getRequest().getSession();
	}

	public User getCurrentUser() {
		User user = (User) getSession().getAttribute("user");
		return user;
	}

	public Integer getCurrentUserId() {
		User user = getCurrentUser();
		if (user != null) {
			return user.getId();
		}
		return -1;
	}

	public School getCurrentSchool() {
		School school = (School) getSession().getAttribute("school");
		return school;
	}

	public Integer getCurrentSchoolId() {
		School school = getCurrentSchool();
		if (school != null) {
			return school.getSchoolid();
		}
		return -1;
	}

	public String getRequestBody() {
		try {
			return Files.read(getRequest().getInputStream(), "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	SearchCondition searchCondition;

	public SearchCondition getSearchCondition() {
		if (searchCondition != null) {
			return searchCondition;
		} else {
			searchCondition = new SearchCondition();
		}
		searchCondition.setPage(getPage());
		String asc = getParameter("_asc");
		if (asc != null && !asc.isEmpty()) {
			searchCondition.setOrderBy(asc);
			searchCondition.setSort("ASC");
		}
		String desc = getParameter("_desc");
		if (desc != null && !desc.isEmpty()) {
			searchCondition.setOrderBy(desc);
			searchCondition.setSort("DESC");
		}
		String operator = getParameter("_operator");
		if (operator != null && !operator.isEmpty()) {
			searchCondition.setWhereOperator(operator);
		}
		String query = getParameter("_query");
		if (query != null && !query.isEmpty()) {
			Object[] fieldsAndValues = query.split(",");
			searchCondition.setWhereQuery(fieldsAndValues);
		}
		searchCondition.setFields(getParameter("_fields"));
		return searchCondition;
	}

	public Page getPage() {
		Page page = new Page();
		page.setPageNumber(getInt("_page", 1));
		page.setPageSize(getInt("_count", 1));
		page.setFrom(getInt("_from", null));
		page.setDoCount(getBoolean("_do_count", true));
		return page;
	}

	public Integer getInt(String key, Integer defaultValue) {
		String value = getParameter(key);
		if (value == null || value.isEmpty()) {
			return defaultValue;
		} else {
			return Integer.parseInt(value);
		}
	}

	public Boolean getBoolean(String key, Boolean defaultValue) {
		String value = getParameter(key);
		if (value == null || value.isEmpty()) {
			return defaultValue;
		} else {
			return Boolean.parseBoolean(value);
		}
	}

	public String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	public ModelAndView modelAndView(Boolean success) {
		return new ModelAndView(
				new TextView(Jsons.toJson(Maps.toMap("success", success)), APPLICATION_JSON_CHARSET_UTF_8));
	}

	public ModelAndView modelAndView(Integer id) {
		return new ModelAndView(
				new TextView(Jsons.toJson(Maps.toMap("id", id, "success", true)), APPLICATION_JSON_CHARSET_UTF_8));
	}

	public ModelAndView modelAndView(Integer id, String message) {
		return new ModelAndView(new TextView(Jsons.toJson(Maps.toMap("id", id, "success", true, "message", message)),
				APPLICATION_JSON_CHARSET_UTF_8));
	}

	public ModelAndView modelAndView(Boolean success, String msg) {
		return new ModelAndView(new TextView(Jsons.toJson(Maps.toMap("success", success, "message", msg)),
				APPLICATION_JSON_CHARSET_UTF_8));
	}

	public ModelAndView modelAndView(Object object) {
		return new ModelAndView(new TextView(Jsons.toJson(Maps.toMap("json", object, "success", true)),
				APPLICATION_JSON_CHARSET_UTF_8));
	}

	public ModelAndView modelAndView(Object object, Json json) {
		return new ModelAndView(new TextView(
				JSON.toJSONString(Maps.toMap("json", object, "success", true), json.getJsonFilters(),
						SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat),
				APPLICATION_JSON_CHARSET_UTF_8));
	}

	public ModelAndView modelAndView(List list, String listKey) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(listKey, list);
		if (getSearchCondition().getPage().getDoCount()) {
			map.put("_total", getSearchCondition().getPage().getRecordCount());
			map.put("_pageSize", getSearchCondition().getPage().getPageCount());
		}
		if (Json == null) {
			return new ModelAndView(
					new TextView(JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect,
							SerializerFeature.WriteDateUseDateFormat), APPLICATION_JSON_CHARSET_UTF_8));
		} else {
			return new ModelAndView(new TextView(JSON.toJSONString(map, Json.getJsonFilters(),
					SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat),
					APPLICATION_JSON_CHARSET_UTF_8));
		}

	}

	public Json Json;

	public Json getJSON() {
		if (Json == null) {
			Json = new Json();
		}
		return Json;
	}

}
