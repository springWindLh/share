package com.share.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	// @Override
	// public boolean preHandle(HttpServletRequest request,
	// HttpServletResponse response, Object handler) throws Exception {
	// if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
	// LoginValid loginValid = ((HandlerMethod) handler)
	// .getMethodAnnotation(LoginValid.class);
	// if (loginValid == null || loginValid.validate() == false) {
	// return true;
	// } else {
	// if (request.getSession().getAttribute("user") == null) {
	// response.sendRedirect("index.jsp");
	// return false;
	// } else {
	// return true;
	// }
	// }
	// } else {
	// return true;
	// }
	// }
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (request.getSession().getAttribute("user") == null) {
			response.sendRedirect("main.html");
			return false;
		}
		return true;
	}
}
