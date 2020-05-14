package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {//indexController 를 빈으로 만듬 
	@GetMapping( {"/","/index"}) // http://localhost 검색해도 index 나오고 ,  http://localhost/index 검색해도 index 나옴 
	public String index() {
		return "index";
	}
	
	@GetMapping("/home")
	public String home(HttpSession session) {
		if(session.getAttribute("loginMember")==null) {
			return"redirect:/login"; // response.sendredirect 기능
		}
		return "home"; // 로그인 되어있으면 홈으로 보내줄거임 
	}
}
