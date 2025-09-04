package com.bit2025.mysite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bit2025.mysite.service.BoardService;
import com.bit2025.mysite.vo.BoardVo;
import com.bit2025.mysite.vo.UserVo;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @RequestMapping("")
    public String list(@RequestParam(value="page", defaultValue="1") Integer page,
    					@RequestParam(value="kwd", defaultValue="") String keyword,
    					Model model) {

       Map<String, Object> map = boardService.getBoardList(page, keyword);

       model.addAttribute("map", map);
       model.addAttribute("keyword", keyword);
       return "board/list";
    }

    @RequestMapping(value="/write", method=RequestMethod.GET)
    public String write() {
       return "board/write";
    }

    @RequestMapping(value="/write", method=RequestMethod.POST)
    public String write(HttpSession session, BoardVo boardVo) {

    	UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}

		boardVo.setUser_id(authUser.getId());
    	boardService.writeBoard(boardVo);
       return "redirect:/board";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
    	boardService.deleteBoard(id);
       return "redirect:/board";
    }

    @RequestMapping("/view/{id}")
    public String view(@PathVariable("id") Long id, Model model) {
    	BoardVo boardVo = boardService.viewBoard(id);
    	model.addAttribute(boardVo);
       return "board/view";
    }

    @RequestMapping(value="/modify/{id}", method=RequestMethod.GET)
    public String modify(@PathVariable("id") Long id) {
    	boardService.viewBoard(id);
       return "board/modify";
    }

    @RequestMapping(value="/modify/{id}", method=RequestMethod.POST)
    public String modify(BoardVo boardVo, @RequestParam("id") Long id) {
    	boardService.updateBoard(boardVo);
       return "redirect:/view/{id}";
    }
}