package com.gdu.cashbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.vo.Member;

@Transactional
@Service
public class AdminService {
	//주입  new생성자 연산자 대신해서 객체만듬 
	@Autowired private MemberMapper memberMapper;
	
	//회원 전체 리스트 
	public List<Member> selectMemberList(){
		return memberMapper.selectMemberList();		
	}
}
