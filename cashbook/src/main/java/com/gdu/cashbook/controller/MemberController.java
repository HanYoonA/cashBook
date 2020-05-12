package com.gdu.cashbook.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.cashbook.vo.Member;

@Controller
public class MemberController {
	@GetMapping("/addMember")
	public String addMember() {
		return "addMember";
	}
	
	@PostMapping("/addMember")
	public String addMember(Member member) { //Command 객체, 도메인 객체
		//모든 잇풋 타입을 멤버로 받음 			
		System.out.println(member.toString());
		//System.out.println(member); //to String오버라이딩 되어있어서 member.
		return "redirect:/index";
	}
	/*
	@PostMapping("/addMember")
	public String addMember(@RequestParam("memberId") String memberId,
							@RequestParam("memberPw") String memberPw,
							@RequestParam("memberName") String memberName,
							@RequestParam("memberAddr") String memberAddr,
							@RequestParam("memberPhone") String memberPhone,
							@RequestParam("memberEmail") String memberEmail){	
		return "redirect:/";						
	}
	*/
}
