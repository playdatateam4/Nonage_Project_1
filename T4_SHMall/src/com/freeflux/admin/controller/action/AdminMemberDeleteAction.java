package com.freeflux.admin.controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.freeflux.controller.action.Action;
import com.freeflux.dao.ProductDAO;

public class AdminMemberDeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = "NonageServlet?command=admin_member_list";
		
		String id = request.getParameter("id");
		ProductDAO productDAO = ProductDAO.getInstance();
		
		productDAO.deleteMember(id);
		response.sendRedirect(url);
		
	}

}
