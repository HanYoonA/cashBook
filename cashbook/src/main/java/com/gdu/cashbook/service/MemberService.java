package com.gdu.cashbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

//@Service 
//1.Spring bean등록
//2.Transaction 

@Service 
@Transactional  // @Transactional CashbookService클레스안에(메소드)실행중에  하나라도 예외 발생하면 자동으로 롤백됨  , 메소드위에 위치할때는 해당 메소드 실행중에 오류가 있으면 취소 
public class MemberService {
	@Autowired //주입  new생성자 연산자 대신해서 객체만듬 
	private MemberMapper memberMapper;
	
	
	public LoginMember login(LoginMember loginMember) {
		return memberMapper.selectLoginMember(loginMember);
	}
	
	public int addMember(Member member) {
		return memberMapper.insertMember(member);			
	}
}
