package com.bit2025.mysite.security;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.bit2025.mysite.vo.UserVo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 1. Handler 종류 확인
		if(!(handler instanceof HandlerMethod)) {
			// DefaultServletHttpRequestHandler 타입인 경우
			// DefaultServletHttpRequestHandler가 처리하는 경우(정적자원, /assets/**)
			return true;
		}

		// 2. casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;

		// 3. @Auth 가져오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);

		// 4. 만약, @Auth가 없다면
		if(auth == null) {
			return true;
		}

		// @Auth가 있기 때문에 인증(Authentication) 여부 확인
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");

		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}

		return true;
	}

}
