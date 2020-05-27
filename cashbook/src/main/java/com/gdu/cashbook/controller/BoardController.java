package com.gdu.cashbook.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.mapper.CommentMapper;
import com.gdu.cashbook.service.BoardService;
import com.gdu.cashbook.service.CommentService;
import com.gdu.cashbook.vo.Board;
import com.gdu.cashbook.vo.Comment;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class BoardController {
	@Autowired private BoardService boardService;
	@Autowired private CommentService commentService;
	
	//포스트 삭제하기 (댓글 전체 삭제후 포스트 삭제해야됨) 
	@GetMapping("/removeBoard")
	public String removeComment(HttpSession session,  @RequestParam("boardNo")int boardNo) {
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/";
		}
		System.out.println(boardNo+ "<---포스트 삭제 boardNo 받아오나");
		boardService.removeBoardAndCommentAll(boardNo);		
		return "redirect:/boardList";	
	}
	
	//댓글 삭제하기 
	@GetMapping("/removeComment")
	public String removeComment(HttpSession session,  @RequestParam("boardNo")int boardNo,@RequestParam("commentNo")int commentNo) {
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/";
		}
		//session로그인값 스트링으로 바꿔서 comment의 memberId에 넣기
		
		System.out.println(commentNo+"commentNo<--잘받아왓나");
		
		commentService.removeComment(commentNo);
		return "redirect:/detailBoardListOne?boardNo="+boardNo;	
	}
	
	
	//댓글 추가하기  
	@PostMapping("/addComment")
	public String addComment(HttpSession session, Comment comment ,@RequestParam("boardNo")int boardNo) {
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/";
		}
		//session로그인값 스트링으로 바꿔서 comment의 memberId에 넣기
		String memberId= ((LoginMember)(session.getAttribute("loginMember"))).getMemberId();
		comment.setMemberId(memberId);		
		comment.setBoardNo(boardNo);

		commentService.insertComment(comment);
		
		System.out.println(comment + "<--댓글 추가값 받니");
		   return "redirect:/detailBoardListOne?boardNo="+boardNo;	
	}
	
	
	//게시글 추가 폼 보여주기 
	@GetMapping("addBoard")
	public String addBoardList(HttpSession session) {
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/";
		}		
		return "addBoard";		
	}
	
	//게시글 추가 액션 
	@PostMapping("addBoard")
	public String addBoardList(HttpSession session, Board board) {
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/";
		}
		//session로그인값 스트링으로 바꿔서 memberId에 넣기
		String memberId= ((LoginMember)(session.getAttribute("loginMember"))).getMemberId();
		board.setMemberId(memberId);
		
		System.out.println(board+"<--게시글 입력 받아온값 확인");
		boardService.addBoard(board);
		
		return "redirect:/boardList";	
	}
	
	//게시글 수정하기 폼  
	@GetMapping("modifyBoard")
	public String modifyBoardOne(HttpSession session, Model model, @RequestParam(value="boardNo") int boardNo){
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/";
		}
		//선택한 게시글의 해당하는 내용  보여주기위해 값을 담음
		Board board =boardService.selectBoardListOne(boardNo);		
		model.addAttribute("board",board);
	
		return "modifyBoard";		
	}
	
	
	//게시판 전체리스트 폼(게시판 리스트 전체 목록내용 담아서 보내줌)
	@GetMapping("boardList")
	public String boardListAll(HttpSession session, Model model){
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/";
		}
		//게시판 전체 리스트 가져와서 모델에 담아주기 
		List<Board> boardList=boardService.selectBoardList();		
		model.addAttribute("boardList",boardList);	
		
		return "boardList";	
	}
	
	//게시글 (1개)상세보기  
	@GetMapping("detailBoardListOne")
	public String selectBoardListOne(HttpSession session,  Model model, @RequestParam(value="boardNo") int boardNo ) {
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/";
		}
		//게시글no에 해당하는 내용 1개 보여주기
		Board board =boardService.selectBoardListOne(boardNo);
		model.addAttribute("board",board); 
		
		//게시글no에 해당하는 댓글 리스트 보여주기 
		List<Comment> commentList= commentService.selectCommentList(boardNo);
		model.addAttribute("commentList", commentList);
		
		return "detailBoardListOne";		
	}

	
}
