package kh.board.controller;

import java.util.ArrayList;

import kh.board.model.dao.BoardDao;
import kh.board.model.vo.Board;
import kh.board.view.BoardView;

public class BoardController {
	BoardView view;
	BoardDao dao;
	final int YES = 1;

	public BoardController() {
		super();
		view = new BoardView();
		dao = new BoardDao();
	}

	public void main() {

		while (true) {
			switch (view.showMenu()) {
			case 1:
				printAllBoard();
				break;
			case 2:
				insertBoard();
				break;
			case 0:
				view.printMsg("Bye~");
				return;
			default:
				view.printMsg("잘못입력하셨습니다.");
				break;
			}
		}
	}

	public void printAllBoard() {
		ArrayList<Board> list = dao.selectAllBoard();

		if (!list.isEmpty()) {

			while (true) {
				list = dao.selectAllBoard();

				if (!list.isEmpty()) {
					view.printAllBoard(list);
					// 게시글 메뉴 실행
					subMenu(view.showSubMenu());
				} else {
					break;
				}
			}
		} else {
			view.printMsg("게시물이 없습니다.");
		}
	}

	public void insertBoard() {
		view.printMsg("\n----- 게시물 등록 -----");

		if (dao.insertBoard(view.getBoard()) > 0) {
			view.printMsg("등록 성공!!");
		} else {
			view.printMsg("등록 실패!!");
		}
	}

	public void subMenu(int sel) {
		int boardNo = 0;

		switch (sel) {
		case 1: // 게시물 상세보기
			Board selectBoard = dao.selectOneBoard(view.getBoardNo("조회"));
			// 게시물 번호 확인
			if (selectBoard != null) {
				view.showDetail(selectBoard);
			} else {
				view.printMsg("글번호를 확인해주세요");
			}
			break;
		case 2:
			boardNo = view.getBoardNo("수정");

			if (dao.selectOneBoard(boardNo) != null) {
				view.printMsg("------- 게시글 수정 -------");
				Board b = view.getBoardUpdate();
				b.setBoardNo(boardNo);

				if (dao.updateBoard(b) > 0) {
					view.printMsg("게시물 수정 성공");
				} else {
					view.printMsg("게시물 수정 실패");
				}

			} else {
				view.printMsg("글번호를 확인해주세요");
			}
			break;
		case 3:
			boardNo = view.getBoardNo("삭제");

			if (dao.selectOneBoard(boardNo) != null) {
				if (view.deleteBoard() == YES) {
					if (dao.deleteBoard(boardNo) > 0) {
						view.printMsg("삭제 성공");
					} else {
						view.printMsg("삭제 실패");
					}
				}
			} else {
				view.printMsg("글번호를 확인해주세요");
			}
			break;
		case 0:
			return;
		}
	}

}