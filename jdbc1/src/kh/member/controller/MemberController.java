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
		// dao를 통해서 DB에서 전체회원정보를 ArrayList로 받음
		ArrayList<Member> list = dao.selectAllMember();
		if (list.isEmpty()) {
			// 회원이 한명도 없는 경우
		} else {
			view.printAllMember(list);
		}
	}

	public void printMemberId() {
		Member m = dao.selectOneMember(view.getId());

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

	public void insertMember() {
		view.printMsg("----- 회원 가입 -----");
		String memberId = null;

		// id 중복체크
		while (true) {
			memberId = view.getId();

			if (dao.selectOneMember(memberId) == null) {
				break;
			} else {
				view.printMsg("중복된 아이디입니다. 다시 입력해주세요.");
			}
		}
		// 나머지 정보 입력
		Member m = view.getMember();
		m.setMemberId(memberId);

		if (dao.insertMember(m) > 0) { // 성공
			view.printMsg("가입 성공!");
		} else { // 실패
			view.printMsg("실패");
		}
	}

	public void updateMember() {
		view.printMsg("----- 회원 정보 수정 -----");
		String memberId = view.getId();

		if (dao.selectOneMember(memberId) == null) {
			view.printMsg("회원정보 찾을 수 없습니다.");
		} else {
			Member m = view.updateMember();
			m.setMemberId(memberId);

			if (dao.updateMember(m) > 0) {
				view.printMsg("업데이트 성공");
			} else {
				view.printMsg("업데이트 실패");
			}
		}
	}

	public void deleteMember() {
		view.printMsg("----- 회원 탈퇴 -----");

		if (dao.deleteMember(view.getId()) > 0) {
			view.printMsg("탈퇴 성공");
		} else {
			view.printMsg("해당 회원은 없습니다.");
		}
	}

}