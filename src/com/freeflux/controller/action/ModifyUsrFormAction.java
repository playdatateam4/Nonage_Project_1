package com.freeflux.controller.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.freeflux.dao.MemberDAO;
import com.freeflux.dto.MemberVO;

public class ModifyUsrFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "/mypage/modify.jsp";
		
		HttpSession session = request.getSession();
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if (loginUser == null) {
			url = "NonageServlet?command=login_form";
		} else {
			MemberDAO memberDAO = MemberDAO.getInstance();
			loginUser = memberDAO.getMember(loginUser.getId());
			
			if (loginUser.getAddress() != null) {
				String[] address = loginUser.getAddress().split("///");
				request.setAttribute("add1", address[0]);
				request.setAttribute("add2", address[1]);
				request.setAttribute("loginUser", loginUser);
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}
}
