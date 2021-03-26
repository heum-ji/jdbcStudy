package kh.exam.controller;

import java.sql.Connection;
import java.util.ArrayList;

import common.JDBCTemplate;
import kh.exam.model.dao.ExamDao;
import kh.exam.model.vo.Board;
import kh.exam.model.vo.Member;
import kh.exam.view.ExamView;

public class ExamController {
	ExamView view;
	Member loginMember;
	ExamDao dao;

	public ExamController() {
		super();
		view = new ExamView();
		loginMember = new Member();
		dao = new ExamDao();
	}

	public void main() {

		while (true) {
			switch (view.showCommunity()) {
			case 1:
				login();
				break;
			case 2:
				signUp();
				break;
			case 3:
				findMemberId();
				break;
			case 0:
				view.printMsg("프로그램이 종료됩니다.");
				return;
			}
		}
	}

	public void login() {
		System.out.println("--------- 로그인 ----------");
		String memberId = view.getMemberId();
		String memberPw = view.getMemberPw();

		Connection conn = JDBCTemplate.getConnection();
		loginMember = dao.login(conn, memberId, memberPw);
		JDBCTemplate.close(conn);

		if (loginMember != null) {
			view.printMsg("로그인 성공!!");
			showBoardMenu();
		} else {
			view.printMsg("아이디 또는 비밀번호를 확인하세요.");
		}
	}

	public void showBoardMenu() {

		while (true) {
			switch (view.showBoardMenu()) {
			case 1:
				printAllBoard();
				break;
			case 2:
				printDetailBoard();
				break;
			case 3:
				post();
				break;
			case 4:
				updatePost();
				break;
			case 5:
				deletePost();
				break;
			case 6:
				showMyInfo();
				break;
			case 7:
				updateMyInfo();
				break;
			case 8:
				int sel = deleteMember();

				if (sel == 1) { // 탈퇴
					Connection conn = JDBCTemplate.getConnection();
					
					int result = dao.deleteMember(conn, loginMember.getMemberId());

					if (result > 0) {
						JDBCTemplate.commit(conn);
						loginMember = null;
						view.printMsg("Bye~Bye");
						return;
					} else {
						JDBCTemplate.rollback(conn);
						view.printMsg("탈퇴 실패!!");
					}
				}
				break;
			case 0:
				loginMember = null;
				view.printMsg("Bye~Bye");
				return;
			}
		}
	}

	/* board */
	public void printAllBoard() {
		view.printMsg("----------게시물 목록 ----------");
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<Board> list = dao.selectAllBoard(conn);

		if (list.isEmpty()) {
			view.printMsg("게시물이 없습니다.");
		} else {
			view.printAllBoard(list);
		}
		JDBCTemplate.close(conn);
	}

	public void printDetailBoard() {
		int boardNo = view.getBoardNo();
		Connection conn = JDBCTemplate.getConnection();

		int result = dao.viewBoard(conn, boardNo);

		if (result > 0) {
			JDBCTemplate.commit(conn);
			view.printOneBoard(dao.selectOneBoard(conn, boardNo));
		} else {
			JDBCTemplate.rollback(conn);
			view.printMsg("게시물 번호를 확인해주세요.");
		}
		JDBCTemplate.close(conn);
	}

	public void post() {
		String boardTitle = view.getBoardTitle();
		String boardContent = view.getBoardContent();
		Connection conn = JDBCTemplate.getConnection();

		int result = dao.post(conn, boardTitle, boardContent, loginMember.getMemberId());

		if (result > 0) {
			JDBCTemplate.commit(conn);
			view.printMsg("게시물 등록 성공!!");
		} else {
			JDBCTemplate.rollback(conn);
			view.printMsg("게시물 등록 실패!!");
		}
		JDBCTemplate.close(conn);
	}

	public void updatePost() {
		int boardNo = view.getBoardNo();
		Connection conn = JDBCTemplate.getConnection();
		Board board = dao.selectOneBoard(conn, boardNo);

		if (loginMember.getMemberId().equals(board.getBoardWriter())) {
			String boardTitle = view.getBoardTitle();
			String boardContent = view.getBoardContent();

			int result = dao.updateBoard(conn, boardTitle, boardContent, boardNo);

			if (result > 0) {
				JDBCTemplate.commit(conn);
				view.printMsg("게시글 수정 성공!!");
			} else {
				JDBCTemplate.rollback(conn);
				view.printMsg("게시글 수정 실패!!");
			}
		} else {
			view.printMsg("작성자만 수정이 가능합니다.");
		}
		JDBCTemplate.close(conn);
	}

	public void deletePost() {
		int boardNo = view.getBoardNo();
		Connection conn = JDBCTemplate.getConnection();
		Board board = dao.selectOneBoard(conn, boardNo);

		if (loginMember.getMemberId().equals(board.getBoardWriter())) {

			int result = dao.deleteBoard(conn, boardNo);

			if (result > 0) {
				JDBCTemplate.commit(conn);
				view.printMsg("게시글 삭제 성공!!");
			} else {
				JDBCTemplate.rollback(conn);
				view.printMsg("게시글 삭제 실패!!");
			}
		} else {
			view.printMsg("작성자만 삭제가 가능합니다.");
		}
		JDBCTemplate.close(conn);
	}

	/* member */
	public void signUp() {
		System.out.println("--------- 회원가입 ----------");
		Member member = view.SignUp();
		Connection conn = JDBCTemplate.getConnection();

		int result = dao.insertMember(conn, member);

		if (result > 0) {
			JDBCTemplate.commit(conn);
			view.printMsg("회원가입 성공");
		} else {
			JDBCTemplate.rollback(conn);
			view.printMsg("회원가입 실패");
		}
		JDBCTemplate.close(conn);
	}

	public void findMemberId() {
		view.printMsg("--------- 아이디 찾기 ----------");
		String memberName = view.getMemberName();
		String phone = view.getPhone();
		Connection conn = JDBCTemplate.getConnection();

		String memberId = dao.selectMemberId(conn, memberName, phone);

		if (memberId != null) {
			view.printMsg("아이디는 [" + memberId + "] 입니다.");
		} else {
			view.printMsg("일치하는 정보가 없습니다.");
		}
		JDBCTemplate.close(conn);
	}

	public void showMyInfo() {
		view.printMyInfo(loginMember);
	}

	public void updateMyInfo() {
		String memberPw = view.getMemberPw();
		String phone = view.getPhoneEx();
		Connection conn = JDBCTemplate.getConnection();

		int result = dao.updateMember(conn, memberPw, phone, loginMember.getMemberId());

		if (result > 0) {
			JDBCTemplate.commit(conn);
			loginMember.setMemberPw(memberPw);
			loginMember.setPhone(phone);
			view.printMsg("정보 수정 성공");
		} else {
			JDBCTemplate.rollback(conn);
			view.printMsg("정보 수정 실패");
		}
	}

	public int deleteMember() {
		return view.deleteMemberMenu();
	}

}