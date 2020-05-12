package com.gdu.cashbook.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Member;

@Mapper
public interface CashbookMapper {
	public int insertCashbook(Member member); // insertCashbookf를 CashbookMapper.xml insert id로 사용할거임 
	 
}
