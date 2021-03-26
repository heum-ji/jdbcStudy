package kh.member.controller;

import java.sql.Connection;
import java.util.ArrayList;

import common.JdbcTemplate;
import kh.member.model.dao.MemberDao;
import kh.member.model.vo.Member;
import kh.member.view.MemberView;

public class MemberController {
	MemberView view;
	MemberDao dao;

	public MemberController() {
		super();
		view = new MemberView();
		dao = new MemberDao();
	}

	public void main() {

		while (true) {
			switch (view.showMenu()) {
			case 1:
				printAllMember();
				break;
			case 2:
				printMemberId();
				break;
			case 3:
				printMemberName();
				break;
			case 4:
				insertMember();
				break;
			case 5:
				updateMember();
				break;
			case 6:
				deleteMember();
				break;
			case 0:
				return;
			}
		}
	}

	public void printAllMember() {
		Connection conn = JdbcTemplate.getConnection();
		ArrayList<Member> list = dao.selectAllMember(conn);

		if (!list.isEmpty()) {
			view.printAllMember(list);
		} else {
			view.printMsg("회원이 없습니다.");
		}
		JdbcTemplate.close(conn);
	}

	public void printMemberId() {
		view.printMsg("----- 아이디로 회원 조회 -----");
		String memberId = view.getId();
		Connection conn = JdbcTemplate.getConnection();
		Member member = dao.selectOneMember(conn, memberId);

		if (member != null) {
			view.printOneMember(member);
		} else {
			view.printMsg("회원 정보를 조회할 수 없습니다.");
		}
		JdbcTemplate.close(conn);
	}

	public void printMemberName() {
		view.printMsg("----- 이름으로 회원 조회 -----");
		String memberName = view.getName();
		Connection conn = JdbcTemplate.getConnection();
		ArrayList<Member> list = dao.selectMemberName(conn, memberName);

		if (list.isEmpty()) {
			view.printMsg("회원 정보가 없습니다.");
		} else {
			view.printAllMember(list);
		}
		JdbcTemplate.close(conn);
	}

	public void insertMember() {
		view.printMsg("----- 회원 가입 -----");
		Connection conn = JdbcTemplate.getConnection();

		String memberId = null;

		while (true) {
			memberId = view.getId();

			if (dao.selectOneMember(conn, memberId) != null) {
				view.printMsg("이미 사용중인 아이디입니다.");
			} else {
				break;
			}
		}

		Member member = view.getMember();
		member.setMemberId(memberId);

		if (dao.insertMember(conn, member) > 0) {
			view.printMsg("회원 가입 성공");
			JdbcTemplate.commit(conn);
		} else {
			view.printMsg("회원 가입 실패");
			JdbcTemplate.rollback(conn);
		}
		JdbcTemplate.close(conn);
	}

	public void updateMember() {
		view.printMsg("----- 회원 정보 수정 -----");
		String memberId = view.getId();
		Connection conn = JdbcTemplate.getConnection();

		Member member = dao.selectOneMember(conn, memberId);

		if (member != null) {
			Member updateMember = view.updateMember();
			updateMember.setMemberId(memberId);
			int result = dao.updateMember(conn, updateMember);

			if (result > 0) {
				JdbcTemplate.commit(conn);
				view.printMsg("정보 변경 성공");
			} else {
				JdbcTemplate.rollback(conn);
				view.printMsg("정보 변경 실패");
			}
		} else {
			view.printMsg("회원 정보를 조회 할 수 없습니다.");
		}
		JdbcTemplate.close(conn);
	}

	public void deleteMember() {
		view.printMsg("----- 회원 탈퇴 -----");
		String memberId = view.getId();
		Connection conn = JdbcTemplate.getConnection();

		int result = dao.deleteMember(conn, memberId);

		if (result > 0) {
			JdbcTemplate.commit(conn);
			view.printMsg("삭제 성공");
		} else {
			JdbcTemplate.rollback(conn);
			view.printMsg("아이디를 확인해주세요");
		}
		JdbcTemplate.close(conn);
	}

}