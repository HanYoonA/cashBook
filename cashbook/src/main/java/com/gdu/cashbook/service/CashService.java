package com.gdu.cashbook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.CashMapper;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.Category;
import com.gdu.cashbook.vo.DayAndPrice;

@Service
@Transactional
public class CashService {
	@Autowired private CashMapper cashMapper;
	
	//수정액션
	public int modifyCashOne(Cash cash) {
		int row = cashMapper.updateCashhOne(cash);
		return row;		
	}
	
	//수정폼 
	public Cash selectCashOne(int cashNo) {		
		return cashMapper.selectCashOne(cashNo);				
	}
	
	//입력하기 
	public int addCash(Cash Cash) {
		int row = cashMapper.insertCash(Cash); 
		return row;
	}
	
	//카테고리 리스트 
	public List<Category> selectCateogyList(){		
		return cashMapper.selectCategoryList();	
	}
	
	public List<DayAndPrice> getCashAndPriceList(String memberId, int year,  int month){
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("year", year);
		map.put("month", month);	
		return cashMapper.selectDayAndPriceList(map);		
	
	
	}
	//Cash 삭제 
	public int removeCash(Cash cashNo) {
		int row= cashMapper.delectCash(cashNo);		
		return row;
	}
	
	
	public Map<String, Object> getCashListByDate(Cash cash){
		List<Cash> cashList= cashMapper.selectCashListByDate(cash);
		int  cashKindSum = cashMapper.selectCashKindSum(cash);
		Map<String, Object> map = new HashMap<>();
		map.put("cashList", cashList);
		map.put("cashKindSum", cashKindSum);	
		return map;
		
	}
}
