package com.gdu.cashbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.CashbookMapper;
import com.gdu.cashbook.vo.Member;

//@Service 
//1.Spring bean등록
//2.Transaction 

@Service
@Transactional  // @Transactional (메소드)실행중에  예외 발생하면 자동으로 롤백됨 
public class CashbookService {
	@Autowired //주입  new생성자 연산자 대신해서 객체만듬 
	private CashbookMapper cashbookMapper;
	
	public int addCashbook(Member member) {
		return cashbookMapper.insertCashbook(member);			
	}
}
