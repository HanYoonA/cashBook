package com.gdu.cashbook.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.cashbook.service.CashService;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class CashController {
	@Autowired private CashService cashService;
	
	@GetMapping("getCashListByDate")
	public String getCashListByDate(HttpSession session,Model model) {
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/";
		}
		//로그인 아이디 
		String loginMemberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		//오늘날짜를 구해서 우리가 원하는 문자열 형태로 변경 
		//Calendar today= Calendar.getInstance();//"yyyy-mm-dd"		
		Date today = new Date();
		SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd");		
		String strToday=sdf.format(today);//원래는 길게 나오는데  2020-05-19 로 만듬		
		System.out.println(strToday+"<---strToday");
		
		Cash cash = new Cash();// +id, +date 날짜형식 ("yyyy-mm-dd")로 바꿔야됨
		cash.setMemberId(loginMemberId);
		cash.setCashDate(strToday);
		
		List <Cash> cashList = cashService.getCashListByDate(cash);
		model.addAttribute("cashList",cashList);
		model.addAttribute("today", today);
		
		//디버깅 코드 
		 for(Cash c :cashList) {
			 System.out.println(c);
		 }				
		return "getCashListByDate";		
	}		
}
