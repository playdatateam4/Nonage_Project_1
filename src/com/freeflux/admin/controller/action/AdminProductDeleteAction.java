package com.freeflux.admin.controller.action;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.freeflux.controller.action.Action;
import com.freeflux.dao.ProductDAO;


public class AdminProductDeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "NonageServlet?command=admin_product_list";
		
		String pseq = request.getParameter("pseq");
		ProductDAO productDAO = ProductDAO.getInstance();
		
		productDAO.deleteProduct(pseq);
		response.sendRedirect(url);
	}

}
