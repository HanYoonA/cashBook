package com.gdu.cashbook.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.CashService;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class CashController {
	@Autowired private CashService cashService;
	
	@GetMapping("getCashListByDate")
	public String getCashListByDate(HttpSession session,
						Model model,							//문자열로 넘어오면  LocalDate형으로 바꿈
						@RequestParam(value = "day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) { 
	
		if(day ==null) {
			day = LocalDate.now();
		}
		 System.out.println(day + "<--day");
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/";
		}
		
		//로그인 아이디 
		String loginMemberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		
		Cash cash = new Cash();// +id, +date 날짜형식 ("yyyy-mm-dd")로 바꿔야됨
		cash.setMemberId(loginMemberId);
		cash.setCashDate(day.toString()); 		
		System.out.println(day.toString()+"<-------day.toString()");		
	
		List <Cash> cashList = cashService.getCashListByDate(cash);
		model.addAttribute("cashList",cashList);
		model.addAttribute("day", day);
		
		//디버깅 코드 
		 for(Cash c :cashList) {
			 System.out.println(c);
		 }
					
		return "getCashListByDate";	
				
	}		
}
