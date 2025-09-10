package com.bit2025.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

	@RequestMapping({"/", "/main"})
	public String index() {
		return "main/index";
	}

	@ResponseBody
	@RequestMapping("/msg01")
	public String message01() {
		return "hello world";
	}

	@ResponseBody
	@RequestMapping("/msg02")
	public String message02() {
		return "헬로우 월드";
	}
}
