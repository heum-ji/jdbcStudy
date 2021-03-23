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
				break;
			case 5:
				break;
			case 6:
				break;
			case 0:
				return;
			}
		}
	}

	public void printAllMember() {
		// dao를 통해서 DB에서 전체회원정보를 ArrayList로 받음
		ArrayList<Member> list = dao.selectAllMember();
		if (list.isEmpty()) {
			// 회원이 한명도 없는 경우
		} else {
			view.printAllMember(list);
		}
	}

	public void printMemberId() {
		Member m = dao.searchOneMember(view.getId());

		if (m == null) {
			// 해당 id 회원이 없는 경우
			view.printMsg("조회 실패!!");
		} else {
			view.printOneMember(m);
		}
	}

	public void printMemberName() {
		ArrayList<Member> list = dao.selectMemberName(view.getName());

		if (list.isEmpty()) {
			view.printMsg("조회 결과 없음!!");
		} else {
			view.printAllMember(list);
		}
	}
}