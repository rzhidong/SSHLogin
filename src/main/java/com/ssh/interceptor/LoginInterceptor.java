package com.ssh.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {

	// 请求完毕，输出之前
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception exception) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("afterCompletion...");
	}

	// controller之后
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView mav)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("postHandle...");
	}

	// controller之前
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("preHandle...");
		String idStr = (String)request.getSession().getAttribute("id");
		if (idStr != null) {
			return true;
		}else if (idStr == null) {
			response.sendRedirect(request.getContextPath());
			return false;
		}
		return false;
		
	}

}
