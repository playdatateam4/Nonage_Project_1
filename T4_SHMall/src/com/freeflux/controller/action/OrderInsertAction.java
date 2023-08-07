package com.freeflux.controller.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.freeflux.dao.CartDAO;
import com.freeflux.dao.OrderDAO;
import com.freeflux.dto.CartVO;
import com.freeflux.dto.MemberVO;

public class OrderInsertAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "NonageServlet?command=order_list";
		HttpSession session = request.getSession();
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		
		if (loginUser == null) {
			url = "NonageServlet?command=login_form";
		} else {
			// 수정 부분 null 검사(oseq 파싱 검사)
			String oseqStr = request.getParameter("oseq");
		    int oseq = 0;
			if (oseqStr != null && !oseqStr.isEmpty()) {
				try {
					oseq = Integer.parseInt(oseqStr);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
			
			boolean buyNow = "true".equals(request.getParameter("buyNow"));
	        if (buyNow) {
	            // 즉시 구매 로직
	        	 OrderDAO orderDAO = OrderDAO.getInstance();
	        	 int pseq = Integer.parseInt(request.getParameter("pseq")); // 상품 ID 가져오기
	        	 int quantity = Integer.parseInt(request.getParameter("quantity")); // 선택한 상품의 수량 가져오기
	        	 CartVO cartVO = new CartVO();
	        	 cartVO.setPseq(pseq);
	        	 cartVO.setQuantity(quantity);
	        	 cartVO.setId(loginUser.getId());
	        	// 새로운 주문 추가
	        	int maxOseq = orderDAO.insertOrder(new ArrayList<CartVO>(Arrays.asList(cartVO)), loginUser.getId());
	        	// 주문 상세 정보를 데이터베이스에 추가
	        	orderDAO.insertOrderDetail(cartVO, maxOseq);
	        	url = "NonageServlet?command=mypage";
	        } else {
			CartDAO cartDAO = CartDAO.getInstance();
			ArrayList<CartVO> cartList = cartDAO.listCart(loginUser.getId());
			OrderDAO orderDAO = OrderDAO.getInstance();
			int maxOseq = orderDAO.insertOrder(cartList, loginUser.getId());
			url = "NonageServlet?command=mypage";
		}
		}
		response.sendRedirect(url);
	}
}
