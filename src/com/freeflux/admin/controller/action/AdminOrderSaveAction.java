package com.freeflux.admin.controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.freeflux.controller.action.Action;
import com.freeflux.dao.OrderDAO;

public class AdminOrderSaveAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "NonageServlet?command=admin_order_list";

		String[] resultArr = request.getParameterValues("result");
		
		System.out.println(resultArr);

		for (String oseq : resultArr) {
			System.out.println(oseq);
			OrderDAO orderDAO = OrderDAO.getInstance();
			orderDAO.updateOrderResult(oseq);
		}
		request.getRequestDispatcher(url).forward(request, response);
	}
}
