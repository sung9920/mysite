package com.bit2025.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bit2025.mysite.security.Auth;
import com.bit2025.mysite.service.FileuploadService;
import com.bit2025.mysite.service.SiteService;
import com.bit2025.mysite.vo.SiteVo;

import jakarta.servlet.ServletContext;

@Controller
@RequestMapping("/admin")
@Auth(role="ADMIN")
public class AdminController {
	private SiteService siteService;
	private FileuploadService fileuploadService;
	private ServletContext servletContext;

	public AdminController(SiteService siteService, FileuploadService fileuploadService, ServletContext servletContext) {
		this.siteService = siteService;
		this.fileuploadService = fileuploadService;
		this.servletContext = servletContext;
	}

	@RequestMapping({"", "/"})
	public String main() {
		return "admin/main";
	}

	@RequestMapping("/main/update")
	public String mainUpdate(SiteVo vo, @RequestParam("file") MultipartFile multipartFile) {
		String profileURL = fileuploadService.restore(multipartFile);
		if(profileURL != null) {
			vo.setProfileURL(profileURL);
		}

		siteService.updateSite(vo);

		// update SiteVo bean in sevlet context
		servletContext.setAttribute("siteVo", vo);

		// update SiteVo bean in application context


		return "redirect:/admin";
	}

	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}

	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}

	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}

}