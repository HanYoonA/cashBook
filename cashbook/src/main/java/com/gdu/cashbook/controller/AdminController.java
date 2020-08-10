package com.gdu.cashbook.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.cashbook.service.AdminService;
import com.gdu.cashbook.vo.Member;

@Controller
public class AdminController {
	@Autowired private AdminService adminService;
	
	//회원리스트 폼 보여주기  
	@GetMapping("memberManagement")
	public String memberManagement(HttpSession session, Model model) {
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/";
		}
		//어드민이 볼수있는 회원 리스트 
		List<Member> memberList = adminService.selectMemberList();
		model.addAttribute("memberList", memberList);
		return "memberManagement";		
	}
	
	//회원
	
}
