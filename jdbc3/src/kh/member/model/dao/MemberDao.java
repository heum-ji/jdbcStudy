package kh.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.JdbcTemplate;
import kh.member.model.vo.Member;

public class MemberDao {

	public ArrayList<Member> selectAllMember() {
		Connection conn = JdbcTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "select * from member";
		ArrayList<Member> list = new ArrayList<Member>();

		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				list.add(memberSet(rset));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(rset);
			JdbcTemplate.close(pstmt);
			JdbcTemplate.close(conn);
		}
		return list;
	}

	public Member selectOneMember(String memberId) {
		Connection conn = JdbcTemplate.getConnection(); // 1. 드라이버 등록, 2. Connection 객체 생성
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "select * from member where member_id = ?";
		Member m = null;
		try {
			// 3. PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);

			// 4-5. 쿼리문 수행 후 결과 값 받아서 저장
			rset = pstmt.executeQuery();

			if (rset.next()) {
				m = memberSet(rset);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(rset);
			JdbcTemplate.close(pstmt);
			JdbcTemplate.close(conn);
		}
		return m;
	}

	public ArrayList<Member> selectMemberName(String memberName) {
		Connection conn = JdbcTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "select * from member where member_name like ?";
		ArrayList<Member> list = new ArrayList<Member>();

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%" + memberName + "%");
			rset = pstmt.executeQuery();

			while (rset.next()) {
				list.add(memberSet(rset));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(rset);
			JdbcTemplate.close(pstmt);
			JdbcTemplate.close(conn);
		}
		return list;
	}

	public int insertMember(Member m) {
		Connection conn = JdbcTemplate.getConnection();
		PreparedStatement pstmt = null;
		String query = "insert into member values(mem_seq.nextval,?,?,?,?,?,?,sysdate)";
		int result = 0;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, m.getMemberId());
			pstmt.setString(2, m.getMemberPw());
			pstmt.setString(3, m.getMemberName());
			pstmt.setString(4, m.getAddr());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getPhone());

			result = pstmt.executeUpdate();

			if (result > 0) {
				JdbcTemplate.commit(conn);
			} else {
				JdbcTemplate.rollback(conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(pstmt);
			JdbcTemplate.close(conn);
		}
		return result;
	}

	public int updateMember(Member m) {
		Connection conn = JdbcTemplate.getConnection();
		PreparedStatement pstmt = null;
		String query = "update member set member_pw = ?,addr = ?,phone=? where member_id = ?";
		int result = 0;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, m.getMemberPw());
			pstmt.setString(2, m.getAddr());
			pstmt.setString(3, m.getPhone());
			pstmt.setString(4, m.getMemberId());

			result = pstmt.executeUpdate();

			if (result > 0) {
				JdbcTemplate.commit(conn);
			} else {
				JdbcTemplate.rollback(conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(pstmt);
			JdbcTemplate.close(conn);
		}
		return result;
	}

	public int deleteMember(String memberId) {
		Connection conn = JdbcTemplate.getConnection();
		PreparedStatement pstmt = null;
		String query = "delete from member where member_id = ?";
		int result = 0;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);

			result = pstmt.executeUpdate();

			if (result > 0) {
				JdbcTemplate.commit(conn);
			} else {
				JdbcTemplate.rollback(conn);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(pstmt);
			JdbcTemplate.close(conn);
		}
		return result;
	}

	public Member memberSet(ResultSet rset) {
		Member m = new Member();

		try {
			m.setAddr(rset.getString("addr"));
			m.setAge(rset.getInt("age"));
			m.setEnDate(rset.getDate("en_date"));
			m.setMemberId(rset.getString("member_id"));
			m.setMemberName(rset.getString("member_name"));
			m.setMemberNo(rset.getInt("member_no"));
			m.setMemberPw(rset.getString("member_pw"));
			m.setPhone(rset.getString("phone"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}

}