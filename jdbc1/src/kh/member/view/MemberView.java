package kh.member.view;

import java.util.ArrayList;
import java.util.Scanner;

import kh.member.model.vo.Member;

public class MemberView {
	Scanner sc;

	public MemberView() {
		super();
		sc = new Scanner(System.in);
	}

	public int showMenu() {
		System.out.println("----- 회원 관리 프로그램v1 -----");
		System.out.println("1. 회원 전체 조회"); // SELECT
		System.out.println("2. 아이디로 회원 조회"); // SELECT
		System.out.println("3. 이름으로 회원 조회"); // SELECT
		System.out.println("4. 회원가입"); // INSERT
		System.out.println("5. 회원 정보 변경"); // UPDATE
		System.out.println("6. 회원 탈퇴"); // DELETE
		System.out.println("0. 프로그램 종료");
		System.out.print("선택 > ");
		return sc.nextInt();
	}

	public void printAllMember(ArrayList<Member> list) {
		System.out.println("----- 전체 회원 정보 -----");
		System.out.println("아이디\t이름\t나이\t가입일");

		for (Member member : list) {
			System.out.println(member.getMemberId() + "\t" + member.getMemberName() + "\t" + member.getAge() + "\t"
					+ member.getEnDate());
		}
	}

	public void printOneMember(Member m) {
		System.out.println("----- 회원 1명 정보 -----");
		System.out.println("회원번호 : " + m.getMemberNo());
		System.out.println("아이디: " + m.getMemberId());
		System.out.println("비밀번호 : " + m.getMemberPw());
		System.out.println("이름 : " + m.getMemberName());
		System.out.println("주소 : " + m.getAddr());
		System.out.println("나이 : " + m.getAge());
		System.out.println("전화번호 : " + m.getPhone());
		System.out.println("가입일 : " + m.getEnDate());
	}

	public String getId() {
		System.out.print("아이디 입력 : ");
		return sc.next();
	}

	public String getName() {
		System.out.print("이름 입력 : ");
		return sc.next();
	}

	public void printMsg(String msg) {
		System.out.println(msg);
	}

}