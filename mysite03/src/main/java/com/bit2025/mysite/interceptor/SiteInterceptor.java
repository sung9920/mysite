package com.bit2025.mysite.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import com.bit2025.mysite.service.SiteService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SiteInterceptor implements HandlerInterceptor {
	private SiteService siteService;
	
	public SiteIntercecptor 
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		return true;
	}

}
