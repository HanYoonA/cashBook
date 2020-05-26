package com.gdu.cashbook.controller;


import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.CashService;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.Category;
import com.gdu.cashbook.vo.DayAndPrice;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class CashController {
	@Autowired private CashService cashService;
	
	//cash 수입 지출 내용 추가 링크
	@GetMapping("addCash")
	public String addCash(HttpSession session, Model model) {
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/";
		}
		//카테고리는 여러개라서  리스트에 담아 모델에 넣어 보내줌
		List<Category>categoryList =cashService.selectCateogyList();
		model.addAttribute("categoryList", categoryList);
		return "addCash";		
	}
	
	//cash 수입 지출 내용 추가 액션 
	@PostMapping("addCash")
	public String addCash(HttpSession session, Cash cash) {
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/";
		}
		//세션의 담긴 멤버아이디를 구하기 위해서 세션은 오브젝트 타입이기에 LoginMember 타입으로 형변화해줌 
		//그리고나서  얻어온 memberId의 값을 스트링 변수에 담음-> 해당변수 값 cash에 넣기!  
		String memberId = ((LoginMember)(session.getAttribute("loginMember"))).getMemberId();
		cash.setMemberId(memberId);
		System.out.println(cash +"<------캐쉬 추가하기~~~!!!!!!!"+session.getAttribute("loginMember"));
		
		cashService.addCash(cash);
		
		return "redirect:/getCashListByDate";	
	}
	
	//달별로 
	@GetMapping("getCashListByMonth")
	public String getCashListByMonth(HttpSession session,
						Model model,							       //문자열로 넘어오면  LocalDate형으로 바꿈
						@RequestParam(value = "day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {
		
		//로그인 하면 리다이렉트..
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/";
		}
		System.out.println("day:"+day);
		//오늘날짜 cDay~! 
		Calendar cDay= Calendar.getInstance();//오늘날짜가 들어가게됨   
		System.out.println(cDay.get(Calendar.MONTH)+1+"<-----달출력"); //달 출력해보기 5
		
		if(day == null) {			 	
			day = LocalDate.now();			
		}else {
			//day를 cDay로형 변환
			/*
			 * LocalDate -> Calendar
			 * LocalDate -> Date-> Calendar
			 * LocalDate -> String-> Calendar
			 * LocalDate -> Calendar
			 */
			cDay.set(day.getYear(), day.getMonthValue()-1, day.getDayOfMonth());// 오늘날짜 에서  dayrkqtrhk 
		}
		
		/*
		 *0.오늘 LocalDate 타입
		 *1.오늘 LocalDate 타입 
		 *2.이번달의 마지막 일 
		 *3.이번달 1일의 요일 
		 */	
		
		//일별 수입 지출 총액 
		String memberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		int year = cDay.get(Calendar.YEAR);
		int month = cDay.get(Calendar.MONTH)+1; 		
		List<DayAndPrice> dayAndPriceList= cashService.getCashAndPriceList(memberId,year,month);
		System.out.println(dayAndPriceList+"<----dayAndPriceList");
		//출력되는지 보기 
		
		model.addAttribute("dayAndPriceList", dayAndPriceList);
		model.addAttribute("day",day);
		model.addAttribute("month",cDay.get(Calendar.MONTH)+1); //월
		model.addAttribute("lastDay",cDay.getActualMaximum(Calendar.DATE)); //마지막일, 오늘날짜의 제일 큰값
		
		Calendar firstDay= cDay;  //firstDay도 오늘날짜를 구해서  
		firstDay.set(Calendar.DATE, 1); //cDay에 일만 1일로 변경 		
		//요일구하기 
		//firstDay.get(Calendar.DAY_OF_WEEK); //값이 1이면 일요일, 2 월요일,..7이면 토요일 ,리턴값이 숫자기 때문에 저렇게 알아야돼 특정날짜의 요일을 구하는 함수! 
		System.out.println(firstDay.get(Calendar.YEAR)+","+(firstDay.get(Calendar.MONTH)+1)+","+firstDay.get(Calendar.DATE));
		System.out.println("firstDay.get(Calendar.DAY_OF_WEEK)--->" +firstDay.get(Calendar.DAY_OF_WEEK));// 1 일요일, 2월요일, ....7 토요일
		model.addAttribute("firstDayOfWeek", firstDay.get(Calendar.DAY_OF_WEEK));
		
		return "getCashListByMonth";
	}
	
	//일자별 삭제
	@GetMapping("removeCash")
	public String removeCash(HttpSession session, Cash cashNo) {
		cashService.removeCash(cashNo);
		return "redirect:/getCashListByDate";
		
	}
	
	//일자별 나오게 만듬 
	@GetMapping("getCashListByDate")
	public String getCashListByDate(HttpSession session,
						Model model,							//문자열로 넘어오면  LocalDate형으로 바꿈
						@RequestParam(value = "day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) { 
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}		
		
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
	
		Map<String, Object> map = cashService.getCashListByDate(cash);
		model.addAttribute("day", day);
		model.addAttribute("cashList",map.get("cashList"));
		model.addAttribute("cashKindSum",map.get("cashKindSum"));
	
		
		//디버깅 코드 
		// for(Cash c :cashList) {
		//	 System.out.println(c);
		// }
					
		return "getCashListByDate";	
				
	}		
}
