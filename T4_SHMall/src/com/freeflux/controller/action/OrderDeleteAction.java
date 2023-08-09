package com.freeflux.controller.action;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.freeflux.dao.CartDAO;
import com.freeflux.dao.OrderDAO;

public class OrderDeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//결제중인 상품을 삭제한다
		String url = "NonageServlet?command=order_detail";

		String oseq = request.getParameter("oseq");
		String odseq = request.getParameter("odseq");
		
	    System.out.println("oseq: " + oseq + ", odseq: " + odseq);
	    OrderDAO orderDAO = OrderDAO.getInstance();
	    orderDAO.deleteOrder(oseq,odseq);
	      
	    // 1. 다시 order_detail로 포워딩 전 order_detail을 체크
	    // oseq가 없으면(주문이 더 이상 없으면) 메인으로 포워딩한다.
	    if(!(orderDAO.checkIsOrder_detail_is_Empty(oseq))) { //1을 별도의 함수로 체크한다. 
	        url = "NonageServlet?command=index";
	        request.getRequestDispatcher(url).forward(request, response);
	    }
	        
	    request.getRequestDispatcher(url).forward(request, response);
		
	}

}
