package com.gdu.cashbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.BoardMapper;
import com.gdu.cashbook.mapper.CommentMapper;
import com.gdu.cashbook.vo.Board;

@Service
@Transactional
public class BoardService {
	@Autowired private BoardMapper boardMapper;
	@Autowired private CommentMapper commetMapper;
	//게시글 1개 삭제
	public void removeBoardAndCommentAll(int boardNo) {		
		commetMapper.deleteCommentAll(boardNo); //댓글 전체삭제 
		boardMapper.deleteBoard(boardNo);		//포스트 삭제
		return;
	}
	
	//게시글 추가하기 
	public int addBoard(Board board) {
		int row= boardMapper.insertBoard(board);
		return row;		
	}
	
	//게시판 리스트 중 1개 내용 가져오기  
	public Board selectBoardListOne(int boardNo) {
		return boardMapper.selectBoardListOne(boardNo); 
	}	
	
	//게시판 리스트 전체 목록 가져오기
	public List<Board> selectBoardList(){
		return boardMapper.selectBoardList();		
	}	
}
