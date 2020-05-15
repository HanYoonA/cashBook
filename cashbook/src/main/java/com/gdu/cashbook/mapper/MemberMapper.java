package com.gdu.cashbook.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Mapper //@Mapper의 기능 + @Component(객체)의 기능 둘다함
public interface MemberMapper {
	//비밀번호 찾기
	public int updateMemberPw(Member member);
	//아이디 찾기 
	public String selectMemberIdByMember(Member member); 
	//수정하기 
	public int updateMember(Member member); 
	//회원탈퇴(삭제) 
	public int deleteMember(LoginMember loginMember);	
	//회원(로그인한 나의 정보)보기 
	public Member selectMemberOne(LoginMember loginMember);
	// 아이디 중복 확인, 결과값이 있으면 사용못함, SELECT member_id from member WHERE member_id 
	public String selectMemberId(String memberIdCheck); 	
	//로그인 
	public LoginMember selectLoginMember(LoginMember loginMember);
	//회원가입 입력
	public int insertMember(Member member); // insertCashbookf를 CashbookMapper.xml insert id로 사용할거임 
	
	
}
