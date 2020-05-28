package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Board;

@Mapper
public interface BoardMapper {
	//게시글 수정 
	public int updateBoard(Board board);
	
	//게시글 삭제 
	public void deleteBoard(int boardNo);
	//게시글  추가하기
	public int insertBoard(Board board);
	
	//게시판 리스트 중 1개 내용 보기 
	public Board selectBoardListOne(int boardNo);
	
	//게시판 전체목록 리스트 가져오기 
	public List<Board> selectBoardList();
}
