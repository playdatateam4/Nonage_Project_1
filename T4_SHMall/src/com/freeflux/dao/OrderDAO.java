package com.freeflux.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.freeflux.dto.CartVO;
import com.freeflux.dto.OrderVO;
import com.freeflux.util.DBManager;

public class OrderDAO {

	private OrderDAO() {
	}

	private static OrderDAO instance = new OrderDAO();

	public static OrderDAO getInstance() {
		return instance;
	}

	// 사용자가 주문
	public int insertOrder(ArrayList<CartVO> cartList, String id) {
		int maxOseq = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs;

		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false); // 트랜잭션 시작 

			String selectMaxOseq = "select max(oseq) from orders";
			pstmt = conn.prepareStatement(selectMaxOseq);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				maxOseq = rs.getInt(1);
			}
			pstmt.close();

			String insertOrder = "insert into orders(oseq, id) values(" + "orders_seq.nextval, ?)";
			pstmt = conn.prepareStatement(insertOrder);
			pstmt.setString(1, id);
			pstmt.executeUpdate();

			for (CartVO cartVO : cartList) {
				insertOrderDetail(cartVO, maxOseq);
			}
			conn.commit(); // 트랜잭션 종료
		} catch (Exception e) {
			if (conn != null) {
	            try {
	                conn.rollback();  
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
			}
		} finally {
			DBManager.close(conn, pstmt);
		}
		return maxOseq;
	}

	public int insertOrderDetail(CartVO cartVO, int maxOseq) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBManager.getConnection();

			String insertOrderDetail = "insert into order_detail(odseq, oseq, "
					+ "pseq, quantity) values(order_detail_seq.nextval, ?, ?, ?)";
			pstmt = conn.prepareStatement(insertOrderDetail);
			pstmt.setInt(1, maxOseq);
			pstmt.setInt(2, cartVO.getPseq());
			pstmt.setInt(3, cartVO.getQuantity());
			pstmt.executeUpdate();
			pstmt.close();

			String updateCartResult = "update cart set result=2 where cseq=?";
			pstmt = conn.prepareStatement(updateCartResult);
			pstmt.setInt(1, cartVO.getCseq());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		return maxOseq;
	}

	// 사용자가 주문 내역 검색
	public ArrayList<OrderVO> listOrderById(String id, String result, int oseq) {
		ArrayList<OrderVO> orderList = new ArrayList<OrderVO>();
		String sql = "select * from order_view where id=? " + "and result like '%'||?||'%' and oseq=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, result);
			pstmt.setInt(3, oseq);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				OrderVO orderVO = new OrderVO();
				orderVO.setOdseq(rs.getInt("ODSEQ"));
				orderVO.setOseq(rs.getInt("OSEQ"));
				orderVO.setId(rs.getString("ID"));
				orderVO.setIndate(rs.getTimestamp("INDATE"));
				orderVO.setMname(rs.getString("MNAME"));
				orderVO.setZipNum(rs.getString("ZIP_NUM"));
				orderVO.setAddress(rs.getString("ADDRESS"));
				orderVO.setPhone(rs.getString("PHONE"));
				orderVO.setPseq(rs.getInt("PSEQ"));
				orderVO.setQuantity(rs.getInt("QUANTITY"));
				orderVO.setPname(rs.getString("PNAME"));
				orderVO.setPrice2(rs.getInt("PRICE2"));
				orderVO.setResult(rs.getString("RESULT"));
				orderList.add(orderVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return orderList;
	}
	
	//사용자가 결제 중인 상품 삭제
	public void deleteOrder(String oseq, String odseq) {
		String sql = "delete from order_detail where oseq=? and odseq=?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, oseq);
			pstmt.setString(2, odseq);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQLException in deleteOrder : "+e.getMessage());
		}finally {
			DBManager.close(con, pstmt);
		}
	}
	
	//order_detail 테이블을 체크해서 oseq=?를 가진 레코드가 하나라도 있으면(주문이 빈 것이 아니므로) 참 리턴한다.
	public boolean checkIsOrder_detail_is_Empty(String oseq) {
		String sql = "SELECT COUNT(*) FROM order_detail WHERE oseq = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Boolean answer = null;
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,oseq);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if(rs.getInt(1)>0) { //oseq=rs.getInt(1)를 가진 레코드를 하나 이상 가지고 있다는 의미
					answer = true;
					break;
				}else {
					//모든 레코드를 순회해도 oseq=rs.getInt(1)를 가진 레코드가 없다는 의미이다.
					answer = false;
				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException in checkIsOrder_detail_is_Empty : "+e.getMessage());
		}finally {
			DBManager.close(con, pstmt ,rs);
		}
				
		return answer;
	}
	

	// 현재 진행 중인 주문 내역만 조회
	public ArrayList<Integer> selectSeqOrderIng(String id) {
		ArrayList<Integer> oseqList = new ArrayList<Integer>();
		String sql = "select distinct oseq from order_view " + "where id=? and result='1' order by oseq desc";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				oseqList.add(rs.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(con, pstmt, rs);
		}
		return oseqList;
	}
	
	// 총 주문 내역 조회 ( 진행 완료인 주문 내역도 띄워 주기 )
	public ArrayList<Integer> selectTotalSeqOrderIng(String id) {
		ArrayList<Integer> oseqList = new ArrayList<Integer>();
		String sql = "select distinct oseq from order_view " + "where id=? order by oseq desc";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				oseqList.add(rs.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(con, pstmt, rs);
		}
		return oseqList;
	}

	/*
	 * * 관리자 모드에서 사용되는 메소드 * *
	 */
	public ArrayList<OrderVO> listOrder(String member_name) {
		ArrayList<OrderVO> orderList = new ArrayList<OrderVO>();
		String sql = "select * from order_view where mname like '%'||?||'%' " + "order by result, odseq desc";

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
				OrderVO orderVO = new OrderVO();
				orderVO.setOdseq(rs.getInt("ODSEQ"));
				orderVO.setOseq(rs.getInt("OSEQ"));
				orderVO.setId(rs.getString("ID"));
				orderVO.setPseq(rs.getInt("PSEQ"));
				orderVO.setMname(rs.getString("MNAME"));
				orderVO.setPname(rs.getString("PNAME"));
				orderVO.setQuantity(rs.getInt("QUANTITY"));
				orderVO.setZipNum(rs.getString("ZIP_NUM"));
				orderVO.setAddress(rs.getString("ADDRESS"));
				orderVO.setPhone(rs.getString("PHONE"));
				orderVO.setIndate(rs.getTimestamp("INDATE"));
				orderVO.setPrice2(rs.getInt("PRICE2"));
				orderVO.setResult(rs.getString("RESULT"));
				orderList.add(orderVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return orderList;
	}
	
	//관리자가 최종적으로 결제 처리를 함.
	//1. order_detail 테이블의 result를 2로 바꿈
	//2. product 테이블의 inventory에서 -(order_detail.quantity) 처리, 단 조건을 붙여서 inventory가 음수가 되지 않도록 해야함.
	public void updateOrderResult(String odseq) {
		String sql = "update order_detail set result='2' where odseq=?";
		String sql2 = "update product " + 
				"set inventory = case " + 
				"                when inventory>0 then inventory-(select quantity from order_detail where odseq=?) " + 
				"                else 0 " + 
				"                END " + 
				"where pseq = (select p.pseq " + 
				"                from product p " + 
				"                LEFT JOIN order_detail od " + 
				"                on p.pseq = od.pseq " + 
				"                where od.odseq = ?)";

		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, odseq);
			pstmt.executeUpdate();
			
			pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setString(1, odseq);
			pstmt2.setString(2, odseq);
			pstmt2.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
			DBManager.close(conn, pstmt2);
		}
	}
	
}
