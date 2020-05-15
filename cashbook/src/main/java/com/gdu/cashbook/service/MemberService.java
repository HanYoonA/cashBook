package com.gdu.cashbook.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.mapper.MemberidMapper;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.Memberid;

//@Service 
//1.Spring bean등록
//2.Transaction 
@Service 
@Transactional  // @Transactional CashbookService클레스안에(메소드)실행중에  하나라도 예외 발생하면 자동으로 롤백됨  , 메소드위에 위치할때는 해당 메소드 실행중에 오류가 있으면 취소 
public class MemberService {
	//주입  new생성자 연산자 대신해서 객체만듬 
	@Autowired private MemberMapper memberMapper;
	@Autowired private MemberidMapper memberidMapper;
	@Autowired private JavaMailSender javaMailSender;//@Conponent
	
	public int getMemberPw(Member member) { //id&email
		//pw추가 
		UUID uuid = UUID.randomUUID();
		String memberPw= uuid.toString().substring(0,8);//0번째부터 8번쨰까지 
		member.setMemberPw(memberPw);
		System.out.println(memberPw);
		System.out.println(member.getMemberPw());
		int row = memberMapper.updateMemberPw(member);
		if(row==1) {
			System.out.println(memberPw+"<--update memberPw");
			//javaMailSender.send(new SimpleMailMassage);
			//메일로  update성공한 랜덤 pw를 전송 
			//메일 객체  new JavaMailSender();		
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setTo(member.getMemberEmail()); // 누구에게 보내는지 
			simpleMailMessage.setFrom("hya7835@gmail.com"); // 누가 보내는지
			simpleMailMessage.setSubject("cashbook 비밀번호 찾기 메일");//내용
			simpleMailMessage.setText("변경된 비밀번호:"+ memberPw+"입니다");
			javaMailSender.send(simpleMailMessage);
		}
		return row;
	}
	
	//아이디 찾기
	public String getMemberIdByMember(Member member) {
		return memberMapper.selectMemberIdByMember(member);
	}
	
	//회원정보 수정(입력값 넣기)
	public int modifyMember(Member member) {
		 return memberMapper.updateMember(member);
	}	
	
	//@Transactional 위에 안쓰고 여기다 써도됨, 회원탈퇴 void리턴은 생략이 가능함 (return;) 
	public void removeMember(LoginMember loginMember) {	
		//1.아이디 입력
		Memberid memberid = new Memberid();
		memberid.setMemberId(loginMember.getMemberId());
		memberidMapper.insertMemberid(memberid);
		
		//2.아이디 지움  둘중 하나 실패하면 롤백된다
		memberMapper.deleteMember(loginMember);		
	}
	
	//회원정보 
	public Member getMemberOne(LoginMember loginMember) {
		return memberMapper.selectMemberOne(loginMember);
	}
	
	//회원가입 아이디 중복 체크 
	public String checkMemberId(String memberIdcheck) {
		return memberMapper.selectMemberId(memberIdcheck);
	}
	
	//로그인
	public LoginMember login(LoginMember loginMember) {
		return memberMapper.selectLoginMember(loginMember);
	}
	
	//회원가입
	public int addMember(Member member) {
		return memberMapper.insertMember(member);			
	}
}
