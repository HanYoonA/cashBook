package com.gdu.cashbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.CommentMapper;
import com.gdu.cashbook.vo.Comment;

@Service
@Transactional
public class CommentService {
	@Autowired private CommentMapper commentMapper;
	
	//댓글 삭제  
	public int removeComment(int commentNo) {
		int row= commentMapper.deleteComment(commentNo);
		return row;		
	}
	
	//댓글 추가
	public int insertComment(Comment comment) {
		int row= commentMapper.insertComment(comment);
		return row;
	}
	
	//댓글 리스트 
	 public List<Comment> selectCommentList(int boardNo){
		return commentMapper.selectCommentList(boardNo); 
	 }
}
