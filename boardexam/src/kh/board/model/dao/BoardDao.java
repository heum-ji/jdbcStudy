package kh.board.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.JdbcTemplateBoard;
import kh.board.model.vo.Board;

public class BoardDao {

	public ArrayList<Board> selectAllBoard() {
		Connection conn = JdbcTemplateBoard.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Board> list = new ArrayList<Board>();
		String query = "select * from board";
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				list.add(getBoard(rset));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplateBoard.close(rset);
			JdbcTemplateBoard.close(pstmt);
			JdbcTemplateBoard.close(conn);
		}
		return list;
	}

	public Board selectOneBoard(int boardNo) {
		Connection conn = JdbcTemplateBoard.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "select * from board where board_no = ?";
		Board b = null;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				b = getBoard(rset);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplateBoard.close(rset);
			JdbcTemplateBoard.close(pstmt);
			JdbcTemplateBoard.close(conn);
		}
		return b;
	}

	public Board getBoard(ResultSet rset) {
		Board b = new Board();

		try {
			b.setBoardContent(rset.getString("board_content"));
			b.setBoardNo(rset.getInt("board_no"));
			b.setBoardTitle(rset.getString("board_title"));
			b.setEnDate(rset.getDate("en_date"));
			b.setWriter(rset.getString("writer"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	public int insertBoard(Board b) {
		Connection conn = JdbcTemplateBoard.getConnection();
		PreparedStatement pstmt = null;
		String query = "insert into board values(board_seq.nextval,?,?,?,sysdate)";
		int result = 0;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, b.getBoardTitle());
			pstmt.setString(2, b.getBoardContent());
			pstmt.setString(3, b.getWriter());

			result = pstmt.executeUpdate();

			if (result > 0) {
				JdbcTemplateBoard.commit(conn);
			} else {
				JdbcTemplateBoard.rollback(conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplateBoard.close(pstmt);
			JdbcTemplateBoard.close(conn);
		}
		return result;
	}

	public int updateBoard(Board b) {
		Connection conn = JdbcTemplateBoard.getConnection();
		PreparedStatement pstmt = null;
		String query = "update board set board_title = ?,board_content = ? where board_no = ?";
		int result = 0;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, b.getBoardTitle());
			pstmt.setString(2, b.getBoardContent());
			pstmt.setInt(3, b.getBoardNo());

			result = pstmt.executeUpdate();

			if (result > 0) {
				JdbcTemplateBoard.commit(conn);
			} else {
				JdbcTemplateBoard.rollback(conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplateBoard.close(pstmt);
			JdbcTemplateBoard.close(conn);
		}
		return result;
	}

	public int deleteBoard(int boardNo) {
		Connection conn = JdbcTemplateBoard.getConnection();
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "delete from board where board_no = ?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);

			result = pstmt.executeUpdate();

			if (result > 0) {
				JdbcTemplateBoard.commit(conn);
			} else {
				JdbcTemplateBoard.rollback(conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplateBoard.close(pstmt);
			JdbcTemplateBoard.close(conn);
		}
		return result;
	}
}
