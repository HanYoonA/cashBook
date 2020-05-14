package com.gdu.cashbook.service;

import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired //주입  new생성자 연산자 대신해서 객체만듬 
	private MemberMapper memberMapper;
	private MemberidMapper memberidMapper;
	
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
