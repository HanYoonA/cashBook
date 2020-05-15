package com.gdu.cashbook.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Memberid;

@Mapper
public interface MemberidMapper {
	int insertMemberid(Memberid memberid);
	 
}
