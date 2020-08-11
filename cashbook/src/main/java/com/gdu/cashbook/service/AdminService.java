package com.gdu.cashbook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public Map<String, Object> selectMemberList(int currentPage){
		System.out.println(currentPage +"<--admin Service 받아온 현재 페이지 확인, 어드민 서비스");
		
		int rowPerPage = 5;
		int beginRow = (currentPage-1)*rowPerPage;
		System.out.println(beginRow+ "<--시작페이지 디버깅");
		
		//총 회원수
		int totalRow= memberMapper.getTotalMember();
		System.out.println(totalRow+"<--회원 총명수");
		
		//마지막 페이지 
		int lastPage = totalRow/rowPerPage;
				
		if(totalRow%rowPerPage != 0 ) {
			lastPage +=1;
		}
		System.out.println(lastPage + "<--마지막 페이지 디버깅");
		
		//두가지 담아서 보내기 
//		Map<String, Object> map1 = new HashMap<String, Object>();
//		map1.put("beginRow", beginRow);
//		map1.put("rowPerPage", rowPerPage);
		
		//리스트 가져오기 
		List<Member> list = memberMapper.selectMemberList(beginRow,rowPerPage);
		System.out.println(beginRow+"비긴로우,로우퍼페이지 확인"+rowPerPage);
		System.out.println(list+"<--adminService 리스트 가져온것"); 
		
		//맵 담아서 보내기 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lastPage", lastPage);
		map.put("list", list);
				
		return map;		
	}
	
	public Member selectMemberOne(String memberId) {
		System.out.println(memberId+ "<---어드민 서비스 멤버 아이디");
		return memberMapper.selectMember(memberId);
		
	}
	
}
