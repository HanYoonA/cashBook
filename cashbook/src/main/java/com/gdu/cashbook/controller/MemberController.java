package com.gdu.cashbook.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.MemberForm;
//@Controller:
//1.Spring bean등록, 객체가 자동으로 만들어준다 
//2.implements Servlet의 기능을 가지게 된다 
@Controller
public class MemberController {//http 서블릿을 사용할수있는 객체가 됨 
	@Autowired  //주입  new생성자 연산자 대신해서 객체만듬 
	private MemberService memberService;
	
	//비번찾기 액션 
	@PostMapping("/findMemberPw")
	public String findMemberPw(HttpSession session, Model model, Member member) {
		System.out.println(member);
		int row= memberService.getMemberPw(member);
		String msg = "아이디와 메일을  확인하세요";
		if(row == 1) {
			msg="비밀번호를 입력한 메일로 전송하였습니다 "; 			
		}
		model.addAttribute("msg",msg);
		return "memberPwView";
	}
			
	//비번찾기findMemberPw 
	@GetMapping("/findMemberPw")
	public String findMemberPw(HttpSession session) {
		if(session.getAttribute("loginMember")!= null){ 
			return "redirect:/";
		}				
		return "findMemberPw"; 
	}
	

	// 아이디 찾기 
	@GetMapping("/findMemberId")
	public String findMemberId(HttpSession session) {
		if(session.getAttribute("loginMember")!= null){ 
			return "redirect:/";
		}				
		return "findMemberId"; 
	}
	
	//아이디 찾기 액션 
	@PostMapping("/findMemberId")	
	public String findMemberId(HttpSession session,Model model ,Member member) {
		//로그인 상태면
		if(session.getAttribute("loginMember")!= null){ 
			return "redirect:/";
		}			
		String memberIdPart= memberService.getMemberIdByMember(member);
		System.out.println(memberIdPart+"<-----memberIdpart");
		model.addAttribute("memberIdPart", memberIdPart);		
		return "memberIdView";
	}
	
	
	//회원정보 수정폼 보여주기  
	@GetMapping("/modifyMember")
	public String modifyMember(HttpSession session,Model model ) {
		//로그인 상태 아니면 홈
		if(session.getAttribute("loginMember")== null){ 
			return "redirect:/";
		}
		
		Member member= memberService.getMemberOne((LoginMember)(session.getAttribute("loginMember")));
		System.out.println("수정확인중"+member);
		model.addAttribute("member", member);
				
		return "updateMember";		
	}		
	
	
	//회원정보 수정액션 
	@PostMapping("/modifyMember")
	public String modifyMember(HttpSession session, MemberForm memberForm) {
	//로그인 상태 아니면 홈
		if(session.getAttribute("loginMember")== null){ 
			return "redirect:/";
		}
		System.out.println(memberForm + "<----memberForm 받아온값 확인");
		MultipartFile mf = memberForm.getMemberPic();
		String originName= mf.getOriginalFilename(); // 파일형의 이름을 스트링형으로 바꿔주는 메소드	
		System.out.println(originName+"<----originName 디버깅 ");
		System.out.println(memberForm.getMemberPic() + "<--(memberForm.getMemberPic()");
		
		//멤버폼에서 넘겨받은 프로필 사진이 빈값이 아니거나 originNameㅇ; 공백이 아닐경우! 
		//"image/png" 파일로는 사용 가능하나 그게 아니라면 
		if (memberForm.getMemberPic() != null && !originName.equals("")) {
			if (!memberForm.getMemberPic().getContentType().equals("image/png")
					&& !memberForm.getMemberPic().getContentType().equals("image/jpeg")
					&& !memberForm.getMemberPic().getContentType().equals("image/gif")) {
				return "redirect:/modifyMember?imgMsg=n";
			}
		}				
		
		//받아온 사진이 없으면(프로필 사진 변경 x) 원래 저장된 사진 그대로 보여줌 
		if(memberForm.getMemberPic().getOriginalFilename().equals("")) {
			memberService.modifyNoPicMember(memberForm);
			return "redirect:/memberInfo"; // memberInfo.html 페이지 보여중  
		}	
		
		memberService.modifyMember(memberForm);	
		System.out.println(memberForm + "업데이트 멤버!!");
		
		return "redirect:/memberInfo";
		
	}
			
	
	//회원 탈퇴 폼보여주기 
	@GetMapping("/removeMember")
	public String removeMember(HttpSession session, LoginMember loginMember) {
		if(session.getAttribute("loginMember")== null){ //로그인 상태 아니면 인덱스로
			return "redirect:/";
		}				
	      return "removeMember";//input type="password"
	}
	
	//회원탈퇴 
	@PostMapping("/removeMember")
	public String removeMember(HttpSession session, @RequestParam("memberPw")String memberPw, Model model) {
		if(session.getAttribute("loginMember")== null){ //로그인 상태 아니면 인덱스로
			return "redirect:/";
		}
		
		LoginMember loginMember = (LoginMember)(session.getAttribute("loginMember"));// 넘겨받은 아이디 넣어줌 -
		loginMember.setMemberPw(memberPw);//넘겨받은 비번 넣어줌 
		
		System.out.println(loginMember +"삭제 멤버값 넘겨오나 ");
		System.out.println(loginMember.getMemberPw() + "삭제 비번값 넘겨오나");

		int row=memberService.removeMember(loginMember);
		
		if(row==1) {			
			session.invalidate(); //세션지움, 로그아웃			
			return "redirect:/index";
		}
		model.addAttribute("msg", "비밀번호가 틀렸습니다.");
		return "removeMember";
	}
	
	
	
	//나의 정보 
	@GetMapping("/memberInfo")
	public String memberInfo(HttpSession session, Model model) {
		if(session.getAttribute("loginMember")== null){ //로그인 상태 아니면 홈
			return "redirect:/";
		}
		Member member = memberService.getMemberOne((LoginMember)(session.getAttribute("loginMember")));// session 오브젝트 타입인걸 LoginMember타입으로 형변환해줌
		System.out.println(member);
		model.addAttribute("member",member);
		return "memberInfo"; //memberInfo.html 페이지 보여중  		
	}
	
	
	//회원 가입시 아이디 중복 체크 
	@PostMapping("/checkMemberId") 
	public String checkMemberId(Model model, HttpSession session, @RequestParam("memberIdCheck")String memberIdCheck) {
		//로그인 중일때 
		if(session.getAttribute("loginMember")!= null){
			return "redirect:/";
		}						
		String confirmMemberId = memberService.checkMemberId(memberIdCheck);
		//select member_id from member where member_id= memberIdcheck
		System.out.println(confirmMemberId);
		if(confirmMemberId==null) {//아이디 중복체크
			// 아이디 사용 가능 
			System.out.println("아이디를 사용할 수 있습니다");
			model.addAttribute("memberIdCheck",memberIdCheck); 
			
		}else {
			//아이디 사용 불가 
			System.out.println("아이디를 사용할 수 있습니다 ! ");
			model.addAttribute("msg","사용중인 아이디입니다"); 
		}
		return "addMember";  //addMember.html 페이지로감 
	}
	
	
	
	// 로그인 폼으로 이동(login form)
	@GetMapping("/login") 
	public String login(HttpSession session) {
		//로그인 중일때 
		if(session.getAttribute("loginMember")!= null){
			return "redirect:/";
		}		
		//로그인 되어있지 않은 경우에 login.html로 이동 
		return "login";
	}
	
	// 로그인 액션 (login Action) 
	@PostMapping("/login")
	public String login(Model model ,LoginMember loginMember, HttpSession session) {// 먀갸HttpSession session = request.getSession();	
		//로그인 중일때 
		if(session.getAttribute("loginMember")!= null){
			return "redirect:/";
		}		
		System.out.println("loginMember 로그인 확인" +loginMember); // 콘솔 LoginMember [memberId=user1, memberPw=1234, getMemberId()=user1, getMemberPw()=1234,  확인할수있음
		LoginMember returnLoginMember= memberService.login(loginMember);
		System.out.println("리턴 로그인 returnLoginMember:"+returnLoginMember); //리턴 로그인 returnLoginMember:LoginMember [memberId=user1, memberPw=1234]
		if(returnLoginMember ==null) { //빈값이면 로그인 못함(로그인 실패시)
			model.addAttribute("msg","아이디와 비밀번호를 확인하세요");
			return "login"; 
		}else{//로그인 성공시 (디비에 결과값 있으면 회원임)
			session.setAttribute("loginMember", returnLoginMember);
			return "redirect:/home"; // 홈으로감  
		}		
	}
	
	//로그아웃하기 
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		//로그인 실패시 로그인 폼으로 
		if(session.getAttribute("loginMember")== null){
			return "redirect:/"; // index로 넘겨줌 
		}
		session.invalidate(); // 로그아웃 
		return "redirect:/"; // 로그아웃 후 index 넘겨줌 
	}
	
		
	//회원가입 폼(get 링크) 
	@GetMapping("/addMember")
	public String addMember(HttpSession session) {
		//로그인 중일때 
		if(session.getAttribute("loginMember")!= null){
			return "redirect:/";
		}		
		return "addMember"; //addMember.html 페이지 보여줌 
	}
	
	//회원가입 액션(포스트 형식으로 넘겨 받음) 
	@PostMapping("/addMember") 		//모든 잇풋 타입을 멤버로 받음 	 
	public String addMember(MemberForm memberForm, HttpSession session) { //Command 객체, 도메인 객체 , 전부다 받아서 Member 타입으로 바꿔줌, (뷰)폼의  name이 vo안 이름과 같아야됨		
		//로그인 중일때 
		if(session.getAttribute("loginMember")!= null){
			return "redirect:/";
		}
		System.out.println(memberForm+"<---MemberForm");
		System.out.println(memberForm.getMemberPic() + "<---- memberForm.getMemberPic()");
		if(memberForm.getMemberPic()!=null) {
			//파일은  .png .jpg .gif만 업로드 가능 
			if(!memberForm.getMemberPic().getContentType().equals("image/png") 
				&& !memberForm.getMemberPic().getContentType().equals("image/jpeg") 
				&&	!memberForm.getMemberPic().getContentType().equals("image/gif") ) {
				return "redirect:/addMember";
			}
		}		
		memberService.addMember(memberForm);
		//service: memberForm -> member +폴더에 파일도 저장 
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
