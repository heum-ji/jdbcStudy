package kh.board.view;

import java.util.ArrayList;
import java.util.Scanner;

import kh.board.model.vo.Board;

public class BoardView {
	Scanner sc;

	public BoardView() {
		super();
		sc = new Scanner(System.in);
	}

	public int showMenu() {
		System.out.println("\n------- 게시판 -------");
		System.out.println("1. 게시물 목록 보기");
		System.out.println("2. 게시물 등록");
		System.out.println("0. 프로그램 종료");
		System.out.print("선택 >>> ");
		return sc.nextInt();
	}

	public int showSubMenu() {
		System.out.println("\n------- 게시글 메뉴 -------");
		System.out.println("1. 게시물 상세보기");
		System.out.println("2. 게시물 수정");
		System.out.println("3. 게시물 삭제");
		System.out.println("0. 메인으로 돌아가기");
		System.out.print("선택 >>> ");
		return sc.nextInt();
	}

	public void showDetail(Board b) {
		System.out.println("\n------- 게시글 상세보기 -------");
		System.out.println("글번호 : " + b.getBoardNo());
		System.out.println("제목 : " + b.getBoardTitle());
		System.out.println("내용 : " + b.getBoardContent());
		System.out.println("작성자 : " + b.getWriter());
		System.out.println("작성일 : " + b.getEnDate());
	}

	public void printMsg(String msg) {
		System.out.println(msg);
	}

	public void printAllBoard(ArrayList<Board> list) {
		System.out.println("\n--------------- 게시물 목록 ---------------");
		System.out.println("글번호\t제목\t작성자\t작성일");

		for (Board board : list) {
			System.out.println(board.getBoardNo() + "\t" + board.getBoardTitle() + "\t" + board.getWriter() + "\t"
					+ board.getEnDate());
		}
		System.out.println("--------------------------------------");
	}

	public Board getBoard() {
		Board b = new Board();

		System.out.print("제목 입력 : ");
		b.setBoardTitle(sc.next());
		System.out.print("내용 입력 : ");
		b.setBoardContent(sc.next());
		System.out.print("작성자 입력 : ");
		b.setWriter(sc.next());

		return b;
	}

	public Board getBoardUpdate() {
		Board b = new Board();
		System.out.print("수정 할 제목 입력 : ");
		b.setBoardTitle(sc.next());
		System.out.print("수정할 내용 입력 : ");
		b.setBoardContent(sc.next());

		return b;
	}

	public int getBoardNo(String msg) {
		System.out.print(msg + "할 게시물 번호 입력 : ");
		return sc.nextInt();
	}
	
	public int deleteBoard() {
		System.out.println("정말 삭제하시겠습니까[1.yes/2.no]?");
		return sc.nextInt();
	}
}