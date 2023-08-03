package com.freeflux.controller.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.freeflux.dao.MemberDAO;
import com.freeflux.dto.MemberVO;

public class DeleteUsrAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "NonageServlet?command=logout";

		HttpSession session = request.getSession();
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.deleteMember(loginUser);

		response.sendRedirect(url);
	}
}
