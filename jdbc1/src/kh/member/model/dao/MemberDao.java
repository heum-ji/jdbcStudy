package kh.member.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import kh.member.model.vo.Member;

/* Data Access Object - Dao */
public class MemberDao {

	public ArrayList<Member> selectAllMember() {
		Connection conn = null; // DBMS 연결용 객체
		Statement stmt = null; // SQL 구문을 실행하고 결과를 받아오는 객체
		ResultSet rset = null; // SELECT실행 결과를 저장하는 객체
		ArrayList<Member> list = new ArrayList<Member>(); // 전체 회원 정보를 저장할 객체
		String query = "select * from member"; // 쿼리문 작성 시 ; 포함 X

		try {
			// 1. 사용할 DB에 대한 드라이버 등록(사용할 클래스 등록)
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. 등록된 클래스를 이용해서 DB 연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "jdbc", "1234");
			// 3. 쿼리문을 실행할 statement 객체 생성
			stmt = conn.createStatement();
			// 4. 쿼리문 전송
			// SELECT를 수행하기때문에 executeQuery()메소드를 호출
			// 5. 쿼리문 수행 결과 저장
			// SELECT 수행 결과는 ResultSet 객체로 리턴
			rset = stmt.executeQuery(query);

			while (rset.next()) {
				Member m = new Member();
				m.setMemberNo(rset.getInt("member_no"));
				m.setMemberId(rset.getString("member_id"));
				m.setMemberPw(rset.getString("member_pw"));
				m.setMemberName(rset.getString("member_name"));
				m.setAddr(rset.getString("addr"));
				m.setAge(rset.getInt("age"));
				m.setPhone(rset.getString("phone"));
				m.setEnDate(rset.getDate("en_date"));
				list.add(m);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 6. 자원반환
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public Member searchOneMember(String memberId) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		Member m = null;
		String query = "select * from member where member_id = '" + memberId + "'";

		try {
			// 1. 사용할 드라이버 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. 등록된 클래스로 DB 연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "jdbc", "1234");
			// 3. 쿼리문을 실행할 Statement 객체 생성
			stmt = conn.createStatement();
			// 4. 쿼리문을 실행하고 결과를 받아옴, 5. 받은 결과를 저장
			rset = stmt.executeQuery(query);

			if (rset.next()) {
				m = new Member();
				m.setMemberNo(rset.getInt("member_no"));
				m.setMemberId(rset.getString("member_id"));
				m.setMemberPw(rset.getString("member_pw"));
				m.setMemberName(rset.getString("member_name"));
				m.setAddr(rset.getString("addr"));
				m.setAge(rset.getInt("age"));
				m.setPhone(rset.getString("phone"));
				m.setEnDate(rset.getDate("en_date"));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return m;
	}

	public ArrayList<Member> selectMemberName(String memberName) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		ArrayList<Member> list = new ArrayList<Member>();
		String query = "select * from member where member_name like '%" + memberName + "%'";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "jdbc", "1234");
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);

			while (rset.next()) {
				Member m = new Member();
				m.setMemberNo(rset.getInt(1));
				m.setMemberId(rset.getString(2));
				m.setMemberPw(rset.getString(3));
				m.setMemberName(rset.getString(4));
				m.setAddr(rset.getString(5));
				m.setAge(rset.getInt(6));
				m.setPhone(rset.getString(7));
				m.setEnDate(rset.getDate(8));
				list.add(m);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

}