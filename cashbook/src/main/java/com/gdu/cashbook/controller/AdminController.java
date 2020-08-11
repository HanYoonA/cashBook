package com.gdu.cashbook.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.AdminService;
import com.gdu.cashbook.vo.Member;

@Controller
public class AdminController {
	@Autowired private AdminService adminService;
	
	//회원리스트 폼 보여주기  
	@GetMapping("memberManagement")
	public String memberManagement(HttpSession session, Model model, @RequestParam(value="currentPage", defaultValue = "1") int currentPage) {
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/";
		}
		
		//커런트 패이지 확인
		System.out.println(currentPage+"<--현재페이지currentPage");
		
		//리스트 및 마지막페이지  받기 
		Map<String, Object> map = adminService.selectMemberList(currentPage);
		
		//모델에 담기 
		model.addAttribute("list", map.get("list"));
		model.addAttribute("lastPage", map.get("lastPage"));
		model.addAttribute("currentPage", currentPage);
		System.out.println(map.get("list")+"<--컨트롤러 받아온 리스트값");
		
		//어드민이 볼수있는 회원 리스트 
		//List<Member> memberList = adminService.selectMemberList();
		//model.addAttribute("memberList", memberList);
		return "memberManagement";		
	}
	
	//회원 상세보기 
	@GetMapping("memberManagementOne")
	public String memberManagementOne(HttpSession session, Model model,@RequestParam("memberId")String memberId) {
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/";
		}
		//어드민이 볼수있는 회원 리스트 
		Member member=adminService.selectMemberOne(memberId);
		model.addAttribute("member", member);		
		return "memberManagementOne";		
	}
	
}
