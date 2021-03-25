package kh.member.controller;

import java.util.ArrayList;

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
		ArrayList<Member> list = dao.selectAllMember();

		if (!list.isEmpty()) {
			view.printAllMember(list);
		} else {
			view.printMsg("회원이 없습니다.");
		}
	}

	public void printMemberId() {
		view.printMsg("----- 아이디로 회원 조회 -----");
		String memberId = view.getId();
		Member m = dao.selectOneMember(memberId);

		if (m != null) {
			view.printOneMember(m);
		} else {
			view.printMsg("회원 정보를 조회할 수 없습니다.");
		}
	}

	public void printMemberName() {
		view.printMsg("----- 이름으로 회원 조회 -----");
		String memberName = view.getName();

		ArrayList<Member> list = dao.selectMemberName(memberName);

		if (!list.isEmpty()) {
			view.printAllMember(list);
		} else {
			view.printMsg("회원이 없습니다.");
		}
	}

	public void insertMember() {
		view.printMsg("----- 회원 가입 -----");
		String memberId = null;

		while (true) {
			memberId = view.getId();

			if (dao.selectOneMember(memberId) != null) {
				view.printMsg("이미 사용중인 아이디입니다.");
			} else {
				break;
			}
		}

		Member m = view.getMember();
		m.setMemberId(memberId);

		if (dao.insertMember(m) > 0) {
			view.printMsg("회원 가입 성공");
		} else {
			view.printMsg("회원 가입 실패");
		}
	}

	public void updateMember() {
		view.printMsg("----- 회원 정보 수정 -----");
		String memberId = view.getId();

		if (dao.selectOneMember(memberId) == null) {
			view.printMsg("없는 회원입니다.");
		} else {
			Member m = view.updateMember();
			m.setMemberId(memberId);

			if (dao.updateMember(m) > 0) {
				view.printMsg("회원 수정 성공");
			} else {
				view.printMsg("회원 수정 실패");
			}
		}
	}

	public void deleteMember() {
		view.printMsg("----- 회원 탈퇴 -----");

		if (dao.deleteMember(view.getId()) > 0) {
			view.printMsg("회원 탈퇴 성공");
		} else {
			view.printMsg("회원 탈퇴 실패");
		}
	}
}