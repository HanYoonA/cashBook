package com.gdu.cashbook.service;

import java.io.File;
import java.util.UUID;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.mapper.MemberidMapper;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.MemberForm;
import com.gdu.cashbook.vo.Memberid;

//@Service 
//1.Spring bean등록
//2.Transaction 

@Transactional  // @Transactional CashbookService클레스안에(메소드)실행중에  하나라도 예외 발생하면 자동으로 롤백됨  , 메소드위에 위치할때는 해당 메소드 실행중에 오류가 있으면 취소 
@Service 
public class MemberService {
	//주입  new생성자 연산자 대신해서 객체만듬 
	@Autowired private MemberMapper memberMapper;
	@Autowired private MemberidMapper memberidMapper;
	@Autowired private JavaMailSender javaMailSender;//@Conponent
	@Value("C:\\Users\\gd\\Documents\\workspace-spring-tool-suite-4-4.6.1.RELEASE\\maven.1590372851892\\cashbook\\src\\main\\resources\\static\\upload\\")
	
	private String path;
	
	public int getMemberPw(Member member) { //id&email
		//pw추가 
		UUID uuid = UUID.randomUUID();
		String memberPw= uuid.toString().substring(0,8);//0번째부터 8번쨰까지 
		member.setMemberPw(memberPw);
		System.out.println(memberPw);
		System.out.println(member.getMemberPw());
		int row = memberMapper.updateMemberPw(member);
		if(row==1) {
			System.out.println(memberPw+"<--update memberPw");
			//javaMailSender.send(new SimpleMailMassage);
			//메일로  update성공한 랜덤 pw를 전송 
			//메일 객체  new JavaMailSender();		
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setTo(member.getMemberEmail()); // 누구에게 보내는지 
			simpleMailMessage.setFrom("hya7835@gmail.com"); // 누가 보내는지
			simpleMailMessage.setSubject("cashbook 비밀번호 찾기 메일");//내용
			simpleMailMessage.setText("변경된 비밀번호:"+ memberPw+"입니다");
			javaMailSender.send(simpleMailMessage);
		}
		return row;
	}
	
	//아이디 찾기
	public String getMemberIdByMember(Member member) {
		return memberMapper.selectMemberIdByMember(member);
	}
	
	//회원정보 수정(프로필 사진 변경 안함,pic가 빈값일경우 ) 
	public int modifyNoPicMember(MemberForm memberForm) {
		Member member = new Member();
		member.setMemberId(memberForm.getMemberId());
		member.setMemberPw(memberForm.getMemberPw());
		member.setMemberAddr(memberForm.getMemberAddr());
		member.setMemberEmail(memberForm.getMemberEmail());
		member.setMemberName(memberForm.getMemberName());
		member.setMemberPhone(memberForm.getMemberPhone());
		int row =memberMapper.updateNoPicMember(member);
		System.out.println(member+ "<---들어간 값 확인");

		return row;
	}

	//회원정보 수정(입력값 넣기, 프로필 사진 변경함)
	public int modifyMember(MemberForm memberForm) {
		MultipartFile mf = memberForm.getMemberPic();
		//확장자 필요		
		String originName= mf.getOriginalFilename();	
		 
		 System.out.println(originName+ "<--originName" ); //꾸꾸까까.jpg<--originName
			int lastDot= originName.lastIndexOf("."); // 마지막글자의 . (좌석표.png)
			String extension =originName.substring(lastDot); 
			
			//새로운 이름을 생성: UUID
			String memberPic= memberForm.getMemberId()+extension; 
			
			//1.디비에서 저장 
			Member member = new Member(); 
			member.setMemberId(memberForm.getMemberId());
			member.setMemberPw(memberForm.getMemberPw());
			member.setMemberAddr(memberForm.getMemberAddr());
			member.setMemberEmail(memberForm.getMemberEmail());
			member.setMemberName(memberForm.getMemberName());
			member.setMemberPhone(memberForm.getMemberPhone());
			member.setMemberPic(memberPic);
			System.out.println(member+"<-----MemberService.addMemeber:member 출력해볼게");
			int row = memberMapper.updateMember(member);
			
			//2.파일저장
			
			File file = new File(this.path+memberPic);
			 
			try {
				mf.transferTo(file);
			} catch (Exception e) {			
				e.printStackTrace();
			}		
		         // Exception 
		         //1.예외처리를 해야만 문법적으로 이상없는 예외 
		         //2.예외처리를 토드에서 구현하지 않아도 아무문제 없는 예외 RuntimeException
		 
		      return row;
	}	
	
	//@Transactional 위에 안쓰고 여기다 써도됨, 회원탈퇴 void리턴은 생략이 가능함 (return;) 
	public int removeMember(LoginMember loginMember) {	
		//1.멤버이미지 파일 삭제
		//1_1 파일이름 select member_pic from member 
		String memberPic = memberMapper.selectMemberPic(loginMember.getMemberId());
		System.out.println(memberPic+"<---memberPic");
		//1_2 파일삭제
		File file = new File(this.path+memberPic);
		 /*if(file.exists()) {
				file.delete();
			}*/
		
		//2.아이디 입력
		Memberid memberid = new Memberid();
		memberid.setMemberId(loginMember.getMemberId());
		System.out.println(memberid+"<--멤버아이디");
		//3.
		int row = memberMapper.deleteMember(loginMember);   // 맴퍼 성공하면 1, 실패 0값이 남음  
		int row1 =0;
		if(row ==1) {
		 row1 =memberidMapper.insertMemberid(memberid); 
		 if(file.exists()) {
				file.delete();
			}
		 
		}	
		return row1;
	}
	
	//회원정보 
	public Member getMemberOne(LoginMember loginMember) {
		return memberMapper.selectMemberOne(loginMember);
	}
	
	//회원가입 아이디 중복 체크 
	public String checkMemberId(String memberIdcheck) {
		return memberMapper.selectMemberId(memberIdcheck);
	}
	
	//로그인
	public LoginMember login(LoginMember loginMember) {
		return memberMapper.selectLoginMember(loginMember);
	}
	
	//회원가입
	public int addMember(MemberForm memberForm) {
		MultipartFile mf = memberForm.getMemberPic();
		//확장자 필요 : 				
		String originName= mf.getOriginalFilename();	
		
		/*
		if(mf.getContentType().equals("imge/jpg") || mf.getContentType().equals("imge/png") ){//이미지 파일이  jpg거나 png경우 백업 
			//업로드	
		}else {
			//업로드 실패
		}
		*/		
		System.out.println(originName+ "<--originName" ); //꾸꾸까까.jpg<--originName
		int lastDot= originName.lastIndexOf("."); // 마지막글자의 . (좌석표.png)
		String extension =originName.substring(lastDot); 
		
		//새로운 이름을 생성: UUID
		String memberPic= memberForm.getMemberId()+extension; 
		
		
		//1.디비에서 저장 
		// memberForm -> member
		// 파일 -> 디스크에 물리적으로 저장
		Member member = new Member(); 
		member.setMemberId(memberForm.getMemberId());
		member.setMemberPw(memberForm.getMemberPw());
		member.setMemberAddr(memberForm.getMemberAddr());
		member.setMemberEmail(memberForm.getMemberEmail());
		member.setMemberName(memberForm.getMemberName());
		member.setMemberPhone(memberForm.getMemberPhone());
		member.setMemberPic(memberPic);
		System.out.println(member+"<-----MemberService.addMemeber:member 출력해볼게");
		int row = memberMapper.insertMember(member);
		
		//2.파일저장
		// / linux
		// \ window
		//String path="C:/git-cashbook/cashbook/src/main/resources\\static\\upload"; // 여기다가 파일을 저장 할거임	
		File file = new File(this.path+memberPic);
		try {
			mf.transferTo(file);
		} catch (Exception e) {			
			e.printStackTrace();
			throw new RuntimeException(); 
		}		
	         // Exception 
	         //1.예외처리를 해야만 문법적으로 이상없는 예외 
	         //2.예외처리를 토드에서 구현하지 않아도 아무문제 없는 예외 RuntimeException
	 
	      return row;//return memberMapper.insertMember(member); // 파일이름과 확장자가 따로저장됨	
		
	}
}






