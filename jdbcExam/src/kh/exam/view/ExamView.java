package kh.exam.view;

import java.util.ArrayList;
import java.util.Scanner;

import kh.exam.model.vo.Board;
import kh.exam.model.vo.Member;

public class ExamView {
	Scanner sc;

	public ExamView() {
		super();
		sc = new Scanner(System.in);
	}

	public int showCommunity() {
		System.out.println("--------- KH커뮤니티 ----------");
		System.out.println("1. 로그인하기");
		System.out.println("2. 회원가입");
		System.out.println("3. 아이디 찾기");
		System.out.println("0. 프로그램 종료");
		System.out.print("선택 > ");
		return sc.nextInt();
	}

	public int showBoardMenu() {
		System.out.println("--------- KH커뮤니티 ----------");
		System.out.println("1. 게시물 목록 보기");
		System.out.println("2. 게시물 상세 보기");
		System.out.println("3. 게시물 등록");
		System.out.println("4. 게시물 수정");
		System.out.println("5. 게시물 삭제");
		System.out.println("6. 내 정보 보기");
		System.out.println("7. 내 정보 변경");
		System.out.println("8. 회원 탈퇴");
		System.out.println("0. 로그아웃");
		System.out.print("선택 > ");
		return sc.nextInt();
	}

	public void printMsg(String msg) {
		System.out.println(msg);
	}

	/* Exam_Member */

	public int deleteMemberMenu() {
		System.out.print("정말 탈퇴 하시겠습니까(1.YES / 2.NO) ? ");
		return sc.nextInt();
	}

	public void printMyInfo(Member member) {
		System.out.println("회원번호 : " + member.getMemberNo());
		System.out.println("아이디 : " + member.getMemberId());
		System.out.println("비밀번호 : " + member.getMemberPw());
		System.out.println("이름 : " + member.getMemberName());
		System.out.println("전화번호 : " + member.getPhone());
	}

	public Member SignUp() {
		Member member = new Member();
		member.setMemberId(getMemberId());
		member.setMemberPw(getMemberPw());
		member.setMemberName(getMemberName());
		System.out.print("전화번호 입력(ex.01011112222) : ");
		member.setPhone(sc.next());
		return member;
	}

	public Member updateMyInfo() {
		Member member = new Member();
		System.out.println("--------- 아이디 찾기 ----------");
		member.setMemberName(getMemberName());
		member.setPhone(getPhone());
		return member;
	}

	public Member findMemberId() {
		Member member = new Member();
		System.out.println("--------- 아이디 찾기 ----------");
		member.setMemberName(getMemberName());
		member.setPhone(getPhone());
		return member;
	}

	public String getMemberName() {
		System.out.print("이름 입력 : ");
		return sc.next();
	}

	public String getMemberId() {
		System.out.print("ID 입력 : ");
		return sc.next();
	}

	public String getMemberPw() {
		System.out.print("PW 입력 : ");
		return sc.next();
	}

	public String getPhone() {
		System.out.print("전화번호 입력 : ");
		return sc.next();
	}

	public String getPhoneEx() {
		System.out.print("전화번호 입력(ex.01011112222) : ");
		return sc.next();
	}

	/* Exam_Board */
	public void printAllBoard(ArrayList<Board> list) {
		for (Board board : list) {
			if (board.getBoardWriter() == null) {
				System.out.println(board.getBoardNo() + "\t" + board.getBoardTitle() + "\t" + "탈퇴회원" + "\t"
						+ board.getReadCount() + "\t" + board.getWriteDate());
			} else {
				System.out.println(board.getBoardNo() + "\t" + board.getBoardTitle() + "\t" + board.getMemberName()
						+ "\t" + board.getReadCount() + "\t" + board.getWriteDate());
			}
		}
	}

	public void printOneBoard(Board board) {
		System.out.println("게시물 번호 : " + board.getBoardNo());
		System.out.println("게시물 제목 : " + board.getBoardTitle());
		System.out.println("게시물 내용 : " + board.getBoardContent());
		System.out.println("게시물 작성자 : " + board.getMemberName());
		System.out.println("게시물 조회수 : " + board.getReadCount());
		System.out.println("게시물 작성일 : " + board.getWriteDate());
	}

	public String getBoardTitle() {
		System.out.print("제목 입력 : ");
		return sc.next();
	}

	public String getBoardContent() {
		System.out.print("내용 입력 : ");
		return sc.next();
	}

	public int getBoardNo() {
		System.out.print("게시물 번호 입력 : ");
		return sc.nextInt();
	}
}
