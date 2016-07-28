package com.share.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.share.web.entity.User;

public class AdminLoginInterceptor extends HandlerInterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		User admin=(User)request.getSession().getAttribute("admin");
		if (admin == null) {
			response.sendRedirect("/admin/login.html");
			return false;
		}
		return true;
	}
}
