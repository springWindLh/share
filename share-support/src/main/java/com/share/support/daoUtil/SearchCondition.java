package com.share.support.daoUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.share.support.util.Maps;

public class SearchCondition {
	private String fields;

	private Page page;

	private String orderBy;

	private String sort;

	private List<Object> whereQuery=new ArrayList<>();
	
	private String whereOperator;
	

	public String getWhereOperator() {
		return whereOperator;
	}

	public void setWhereOperator(String whereOperator) {
		this.whereOperator = whereOperator;
	}

	public void setWhereQuery(Object...fieldsAndValues) {
		for (Object object : fieldsAndValues) {
			this.whereQuery.add(object);
		}
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getSelectQuery() {
		if (fields == null || fields.equals("")) {
			return "";
		}
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("SELECT ");
		String[] fieldsArray = fields.split(",");
		for (String field : fieldsArray) {
			stringBuffer.append(field).append(" AS ").append(field).append(",");
		}
		stringBuffer.deleteCharAt(stringBuffer.lastIndexOf(","));
		return stringBuffer.toString();
	}

	public String getOrderByQuery() {
		StringBuffer orderByHqlBuffer = new StringBuffer();
		if (orderBy != null) {
			orderByHqlBuffer.append(" ORDER BY ").append(orderBy).append(" ")
					.append(sort).append(" ");
		}
		return orderByHqlBuffer.toString();
	}

	public String getWhereQuery() {
		Map<String, Object> map = Maps.toMap(whereQuery.toArray());
		StringBuffer whereQueryBuffer = new StringBuffer();
		if (map.size() > 0) {
			whereQueryBuffer.append(" WHERE ");
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				whereQueryBuffer.append(entry.getKey()).append(whereOperator).append((entry.getValue() instanceof String)?"'"+entry.getValue()+"'":entry.getValue()).append(" and ");
			}
			whereQueryBuffer.delete(whereQueryBuffer.lastIndexOf(" and "),whereQueryBuffer.length());
		}
		return whereQueryBuffer.toString();
	}

}
