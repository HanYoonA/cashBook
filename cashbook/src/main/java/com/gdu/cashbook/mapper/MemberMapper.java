package com.gdu.cashbook.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Mapper
public interface MemberMapper {
	// 아이디 중복 확인, 결과값이 있으면 사용못함, SELECT member_id from member WHERE member_id 
	public String selectMemberId(String memberIdCheck); 	
		
	//로그인 
	public LoginMember selectLoginMember(LoginMember loginMember);
	
	//회원가입 입력
	public int insertMember(Member member); // insertCashbookf를 CashbookMapper.xml insert id로 사용할거임 
	
	
}
