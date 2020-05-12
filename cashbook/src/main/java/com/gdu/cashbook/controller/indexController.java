package com.gdu.cashbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {//indexController 를 빈으로 만듬 
	@GetMapping("/index")
	public String index() {
		return "index";
	}
}
