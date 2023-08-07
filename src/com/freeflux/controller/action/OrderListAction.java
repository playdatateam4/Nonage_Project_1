package com.freeflux.controller.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.freeflux.dao.OrderDAO;
import com.freeflux.dto.MemberVO;
import com.freeflux.dto.OrderVO;

public class OrderListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "mypage/mypage.jsp";

		HttpSession session = request.getSession();
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

		if (loginUser == null) {
			url = "NonageServlet?command=login_form";
		} else {
			// oseq 파싱 해결
			String oseqStr = request.getParameter("oseq");
		    int oseq = 0;

		    if (oseqStr != null && !oseqStr.isEmpty()) {
		        try {
		            oseq = Integer.parseInt(oseqStr);
		        } catch (NumberFormatException e) {
		            e.printStackTrace();
		        }
		    }

		    OrderDAO orderDAO = OrderDAO.getInstance();
		    ArrayList<OrderVO> orderList = orderDAO.listOrderById(loginUser.getId(), "1", oseq);

		    int totalPrice = 0;
		    for (OrderVO orderVO : orderList) {
		        totalPrice += orderVO.getPrice2() * orderVO.getQuantity();
		    }

		    request.setAttribute("orderList", orderList);
		    request.setAttribute("totalPrice", totalPrice);
		}
		request.getRequestDispatcher(url).forward(request, response);
	}
}
