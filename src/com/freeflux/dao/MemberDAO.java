package com.freeflux.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.freeflux.dto.MemberVO;
import com.freeflux.util.DBManager;

public class MemberDAO {

	private MemberDAO() {
	}

	private static MemberDAO instance = new MemberDAO();

	public static MemberDAO getInstance() {
		return instance;
	}

	public int confirmID(String userid) {
		int result = -1;
		String sql = "select * from member where id=?";

		Connection connn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connn = DBManager.getConnection();
			pstmt = connn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = 1;
			} else {
				result = -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(connn, pstmt, rs);
		}
		return result;
	}

	public MemberVO getMember(String id) {
		MemberVO memberVO = null;
		String sql = "select * from member where id=?";

		Connection connn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connn = DBManager.getConnection();
			pstmt = connn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				memberVO = new MemberVO();
				memberVO.setId(rs.getString("id"));
				memberVO.setPwd(rs.getString("pwd"));
				memberVO.setName(rs.getString("name"));
				memberVO.setEmail(rs.getString("email"));
				memberVO.setZipNum(rs.getString("zip_num"));
				memberVO.setAddress(rs.getString("address"));
				memberVO.setPhone(rs.getString("phone"));
				memberVO.setUseyn(rs.getString("useyn"));
				memberVO.setIndate(rs.getTimestamp("indate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(connn, pstmt, rs);
		}
		return memberVO;
	}

	public int insertMember(MemberVO memberVO) {
		int result = 0;
		String sql = "insert into member(id, pwd, name, zip_num,";
		sql += " address, phone) values(?, ?, ?, ?, ?, ?)";

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberVO.getId());
			pstmt.setString(2, memberVO.getPwd());
			pstmt.setString(3, memberVO.getName());
			pstmt.setString(4, memberVO.getZipNum());
			pstmt.setString(5, memberVO.getAddress());
			pstmt.setString(6, memberVO.getPhone());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		return result;
	}
	// 이름과 이메일로 아이디 찾기
	public String find_id(String userName, String userEmail) {
		String sql = "select id from member where name=? and email=?";
		// db 연동
		Connection conn = null;
		// 쿼리문 수행
		PreparedStatement pstmt = null;
		// 결과 값 저장
		ResultSet rs = null;
		// 찾을 아이디
		String id = null;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userName);
			pstmt.setString(2, userEmail);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				id = rs.getString("id");
				}
			DBManager.close(conn, pstmt, rs);
		} catch (Exception e) {
		}
		return id;
	}
	// 아이디, 이름, 이메일로 비밀번호 찾기
	public String find_pwd(String userID, String userName, String userEmail) {
		String sql = "select pwd from member where id=? and name=? and email=?";
		// db 연동
		Connection conn = null;
		// 쿼리문 수행
		PreparedStatement pstmt = null;
		// 결과 값 저장
		ResultSet rs = null;
		// 찾을 비밀번호
		String pwd = null;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			pstmt.setString(2, userName);
			pstmt.setString(3, userEmail);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				pwd = rs.getString("pwd");
				}
			DBManager.close(conn, pstmt, rs);
		} catch (Exception e) {
		}
		return pwd;
	}
	// 비밀번호 재설정
	public boolean change_pwd(String userId, String newPwd) {
		String sql = "update member set pwd=? where id=?";
		// db 연동
		Connection conn = null;
		// 쿼리문 수행
		PreparedStatement pstmt = null;
		
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, newPwd);
			int rowsUpdated = pstmt.executeUpdate();
	        if (rowsUpdated > 0) {
	            // 비밀번호 변경이 성공했으므로 true 반환
	            return true;
	        }
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}finally {
			DBManager.close(conn, pstmt);
		}
		return false;
	}

	/*
	 * * 관리자 모드에서 사용되는 메소드 * *
	 */
	public ArrayList<MemberVO> listMember(String member_name) {
		ArrayList<MemberVO> memberList = new ArrayList<MemberVO>();
		String sql = "select * from member where name like '%'||?||'%' " + "order by indate desc";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			if (member_name == "") {
				pstmt.setString(1, "%");
			} else {
				pstmt.setString(1, member_name);
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				MemberVO memberVO = new MemberVO();
				memberVO.setId(rs.getString("id"));
				memberVO.setPwd(rs.getString("pwd"));
				memberVO.setName(rs.getString("name"));
				memberVO.setEmail(rs.getString("email"));
				memberVO.setZipNum(rs.getString("zip_num"));
				memberVO.setAddress(rs.getString("address"));
				memberVO.setPhone(rs.getString("phone"));
				memberVO.setUseyn(rs.getString("useyn"));
				memberVO.setIndate(rs.getTimestamp("indate"));
				memberList.add(memberVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return memberList;
	}
}
