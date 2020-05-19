package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Cash;

@Mapper
public interface CashMapper {
	//로그인 사용자의 오늘날짜 Cash 목록      
	public List<Cash> selectCashListByDate(Cash cash);
	
}