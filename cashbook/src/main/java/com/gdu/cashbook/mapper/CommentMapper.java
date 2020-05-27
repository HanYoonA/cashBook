package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Comment;

@Mapper
public interface CommentMapper {
	//댓글 전체 삭제 
	public  void deleteCommentAll(int bordNo);	
	//댓글삭제 
	public int deleteComment(int commentNo);	
	//댓글 추가하기 
	public int insertComment(Comment comment);
	//댓글 리스트 출력하기 
	public List<Comment> selectCommentList(int boardNo); 
}
