package com.bit2025.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bit2025.mysite.service.GuestbookService;
import com.bit2025.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestBookController {

    @Autowired
    private GuestbookService guestbookService;

    @RequestMapping("")
    public String list(Model model) {
       List<GuestbookVo> list = guestbookService.getMessageList();
       model.addAttribute("list", list);
       return "guestbook/list";
    }

    @RequestMapping("/add")
    public String add(GuestbookVo guestbookVo) {
       guestbookService.addMessage(guestbookVo);
       return "redirect:/guestbook";
    }

    @RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
    public String delete(@PathVariable("id") Long id) {
       return "guestbook/delete";
    }

    @RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
    public String delete(GuestbookVo guestbookVo) {
       guestbookService.deleteMessage(guestbookVo);
       return "redirect:/guestbook";
    }

//  @RequestMapping(value = "delete", method = RequestMethod.POST)
//  public String delete(@RequestParam("id") Long id, @RequestParam(value="password", defaultValue="") String password) {
//     guestbookService.deleteMessage(id, password);
//     return "redirect:/guestbook";
//  }
}