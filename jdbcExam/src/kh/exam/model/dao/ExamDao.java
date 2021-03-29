package kh.exam.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.JDBCTemplate;
import kh.exam.model.vo.Board;
import kh.exam.model.vo.Member;

public class ExamDao {

	/* Member */

	public int updateMember(Connection conn, String memberPw, String phone, String memberId) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "update exam_member set member_pw = ?,phone = ? where member_id = ?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberPw);
			pstmt.setString(2, phone);
			pstmt.setString(3, memberId);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public Member login(Connection conn, String memberId, String memberPw) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "select * from exam_member where member_id = ? and member_pw = ?";
		Member member = null;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPw);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				member = new Member();

				member.setMemberId(memberId);
				member.setMemberName(rset.getString("member_name"));
				member.setMemberNo(rset.getInt("member_no"));
				member.setMemberPw(memberPw);
				member.setPhone(rset.getString("phone"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return member;
	}

	public int insertMember(Connection conn, Member member) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "insert into exam_member values(exam_member_seq.nextval,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getPhone());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int deleteMember(Connection conn, String memberId) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "delete from exam_member where member_id = ?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public String selectMemberId(Connection conn, String memberName, String phone) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "select member_id from exam_member where member_name = ? and phone = ?";
		String memberId = null;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberName);
			pstmt.setString(2, phone);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				memberId = rset.getString("member_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return memberId;
	}

	/* Board */
	public ArrayList<Board> selectAllBoard(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "select * from exam_board left join exam_member on (member_id = board_writer)";
		ArrayList<Board> list = new ArrayList<Board>();

		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				Board board = new Board();
				board.setBoardContent(rset.getString("board_content"));
				board.setBoardNo(rset.getInt("board_no"));
				board.setBoardTitle(rset.getString("board_title"));
				board.setBoardWriter(rset.getString("board_writer"));
				board.setReadCount(rset.getInt("read_count"));
				board.setWriteDate(rset.getDate("write_date"));
				board.setMemberName(rset.getString("member_name"));
				list.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}

	public int viewBoard(Connection conn, int boardNo) {
		PreparedStatement pstmt = null;
		String query = "update exam_board set read_count = read_count + 1 where board_no = ?";
		int result = 0;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int updateBoard(Connection conn, String boardTitle, String boardContent, int boardNo) {
		PreparedStatement pstmt = null;
		String query = "update exam_board set board_title = ?,board_content = ? where board_no = ?";
		int result = 0;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardTitle);
			pstmt.setString(2, boardContent);
			pstmt.setInt(3, boardNo);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int deleteBoard(Connection conn, int boardNo) {
		PreparedStatement pstmt = null;
		String query = "delete from exam_board where board_no = ?";
		int result = 0;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public Board selectOneBoard(Connection conn, int boardNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "select * from exam_board join exam_member on (member_id = board_writer) where board_no = ?";
		Board board = null;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				board = new Board();
				board.setBoardContent(rset.getString("board_content"));
				board.setBoardNo(rset.getInt("board_no"));
				board.setBoardTitle(rset.getString("board_title"));
				board.setBoardWriter(rset.getString("board_writer"));
				board.setReadCount(rset.getInt("read_count"));
				board.setWriteDate(rset.getDate("write_date"));
				board.setMemberName(rset.getString("member_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return board;
	}

	public int post(Connection conn, String boardTitle, String boardContent, String memberId) {
		PreparedStatement pstmt = null;
		String query = "insert into exam_board values(exam_board_seq.nextval,?,?,?,default,default)";
		int result = 0;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardTitle);
			pstmt.setString(2, boardContent);
			pstmt.setString(3, memberId);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

}