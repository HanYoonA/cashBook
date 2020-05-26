package com.gdu.cashbook.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.Category;
import com.gdu.cashbook.vo.DayAndPrice;

@Mapper
public interface CashMapper {
	//수정액션 
	public int updateCashhOne(Cash cash);	
	//수정폼 수정 클릭한 내용 가져오기(수정 폼에서 내용들 보여줌)
	public Cash selectCashOne(int cashNo);	
	// Cash 일자별 수입/지출 입력하기
	public int insertCash(Cash cash); 
	// addCash 폼에서  카테고리 내용들 보여질수 있도록 리스트에 담아서 보냄 
	public List<Category> selectCategoryList();
	
	public List<DayAndPrice> selectDayAndPriceList(Map<String, Object> map);
	//로그인 사용자의 오늘날짜 Cash 목록      
	public List<Cash> selectCashListByDate(Cash cash);	
	//총합계
	public int selectCashKindSum(Cash cash); 	
	//삭제
	public int delectCash(Cash cashNo); 
}
