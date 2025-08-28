package com.bit2025.mysite.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("a");
		if ("write".equals(action)) {
//			String name = request.getParameter("name");
//			String password = request.getParameter("password");
//			String message = request.getParameter("message");
//
//			GuestbookVo vo = new GuestbookVo();
//			vo.setName(name);
//			vo.setPassword(password);
//			vo.setMessage(message);
//
//			new GuestbookDao().insert(vo);

			request.getRequestDispatcher("/WEB-INF/views/board/write.jsp").forward(request, response);

		} else {
		//		List<BoardVo> list = new BoardDao().findAll();
//		request.setAttribute("list", list);

		request.getRequestDispatcher("/WEB-INF/views/board/list.jsp").forward(request, response);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
