package com.gdu.cashbook.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
//@Controller:
//1.Spring bean등록, 객체가 자동으로 만들어준다 
//2.implements Servlet의 기능을 가지게 된다 
@Controller
public class MemberController {//http 서블릿을 사용할수있는 객체가 됨 
	@Autowired  //주입  new생성자 연산자 대신해서 객체만듬 
	private MemberService memberService;
	
	@GetMapping("/login") // 로그인 폼(login form)
	public String login() {
		return "login";
	}
	
	@PostMapping("/login")// 로그인 액션 (login Action) 
	public String login(LoginMember loginMember, HttpSession session) {// 먀갸HttpSession session = request.getSession();
		
		System.out.println(loginMember); // 콘솔 LoginMember [memberId=user1, memberPw=1234, getMemberId()=user1, getMemberPw()=1234,  확인할수있음
		LoginMember returnloginMember= memberService.login(loginMember);
		System.out.println("returnloginMember"+returnloginMember);
		if(returnloginMember ==null) { //빈값이면 로그인 못함(로그인 실패시)
			return "redirect:/login"; 
		}else{//로그인 성공시
			session.setAttribute("loginMember", returnloginMember);
			return "redirect:/"; // 인덱스로 감 
		}		
	}
	
	@GetMapping("/loginout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	
	
	//입력 (get 링크) 
	@GetMapping("/addMember")
	public String addMember() {
		return "addMember"; //addMember.html 페이지 보여줌 
	}
	
	//포스트 형식으로 넘겨 압ㄷ음
	@PostMapping("/addMember") 		//모든 잇풋 타입을 멤버로 받음 	 
	public String addMember(Member member) { //Command 객체, 도메인 객체 / 전부다 받아서 Member 타입으로 바꿔줌, (퓨)폼의  name이 vo안에 이름과 같아야됨		
		System.out.println(member.toString());
		memberService.addMember(member);
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
