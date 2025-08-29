package com.bit2025.mysite.controller;

import java.io.IOException;
import java.util.List;

import com.bit2025.mysite.dao.BoardDao;
import com.bit2025.mysite.vo.BoardVo;
import com.bit2025.mysite.vo.UserVo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("a");

		if ("writeform".equals(action)) {
			HttpSession session = request.getSession();
			if(session == null) {
				response.sendRedirect(request.getContextPath());
				return;
			}

			UserVo authUser = (UserVo)session.getAttribute("authUser");
			if(authUser == null) {
				response.sendRedirect(request.getContextPath());
				return;
			}

			request.getRequestDispatcher("/WEB-INF/views/board/write.jsp").forward(request, response);

		} else if ("write".equals(action)) {
			HttpSession session = request.getSession();
			if(session == null) {
				response.sendRedirect(request.getContextPath());
				return;
			}

			UserVo authUser = (UserVo)session.getAttribute("authUser");
			if(authUser == null) {
				response.sendRedirect(request.getContextPath());
				return;
			}

			String title = request.getParameter("title");
			String content = request.getParameter("content");
			Long id = authUser.getId();

			if(!request.getParameter("parentId").isEmpty() ) {
			Long parentId = Long.parseLong(request.getParameter("parentId"));

			BoardVo parent = new BoardDao().findById(parentId);
			parent.setTitle(title);
			parent.setContents(content);
			parent.setUser_id(id);

			new BoardDao().insertReply(parent);

			} else {
			BoardVo vo = new BoardVo();
			vo.setTitle(title);
			vo.setContents(content);
			vo.setUser_id(id);

			new BoardDao().insert(vo);
			}

			response.sendRedirect(request.getContextPath() + "/board");

		} else if ("view".equals(action)) {
			Long id = Long.parseLong(request.getParameter("id"));

			new BoardDao().updateHit(id);

			BoardVo boardVo = new BoardDao().findById(id);
			request.setAttribute("boardVo", boardVo);
			request.getRequestDispatcher("/WEB-INF/views/board/view.jsp").forward(request, response);

		} else if ("modifyform".equals(action)) {
			HttpSession session = request.getSession();
			if(session == null) {
				response.sendRedirect(request.getContextPath());
				return;
			}

			UserVo authUser = (UserVo)session.getAttribute("authUser");
			if(authUser == null) {
				response.sendRedirect(request.getContextPath());
				return;
			}

			Long id = Long.parseLong(request.getParameter("id"));

			BoardVo boardVo = new BoardDao().findById(id);
			request.setAttribute("boardVo", boardVo);
			request.getRequestDispatcher("/WEB-INF/views/board/modify.jsp").forward(request, response);

		} else if ("modify".equals(action)) {
			HttpSession session = request.getSession();
			if(session == null) {
				response.sendRedirect(request.getContextPath());
				return;
			}

			UserVo authUser = (UserVo)session.getAttribute("authUser");
			if(authUser == null) {
				response.sendRedirect(request.getContextPath());
				return;
			}

			String title = request.getParameter("title");
			String content = request.getParameter("content");
			Long id = Long.parseLong(request.getParameter("id"));

			BoardVo vo = new BoardVo();
			vo.setTitle(title);
			vo.setContents(content);
			vo.setUser_id(id);
			new BoardDao().updateBoard(vo);

			response.sendRedirect(request.getContextPath() + "/board?a=view&id=" + id);

		} else if ("delete".equals(action)) {
			HttpSession session = request.getSession();
			if(session == null) {
				response.sendRedirect(request.getContextPath());
				return;
			}

			UserVo authUser = (UserVo)session.getAttribute("authUser");
			if(authUser == null) {
				response.sendRedirect(request.getContextPath());
				return;
			}

			Long id = Long.parseLong(request.getParameter("id"));

			new BoardDao().deleteById(id);

			response.sendRedirect(request.getContextPath() + "/board");

		} else {

			List<BoardVo> list = new BoardDao().findAll();
			request.setAttribute("list", list);

			request.getRequestDispatcher("/WEB-INF/views/board/list.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
