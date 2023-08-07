package com.freeflux.controller.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.freeflux.dao.MemberDAO;

public class FindPasswordAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userID = request.getParameter("memberId");
		String userName = request.getParameter("name");
		String userEmail = request.getParameter("email");
		
		// 유효성 검사
		if(userID == null || userID.trim().isEmpty() || userName == null || userName.trim().isEmpty() || userEmail == null || userEmail.trim().isEmpty()) {
			request.setAttribute("error", "아이디를 입력해 주세요.");
		} else if(!userEmail.matches("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)*(\\.[a-zA-Z]{2,})$")) {
			request.setAttribute("error", "올바른 이메일을 입력해주세요.");
		} else {
			MemberDAO memberDAO = MemberDAO.getInstance();
			String pwd = memberDAO.find_pwd(userID, userName, userEmail);
		
			if(pwd == null) {
                request.setAttribute("error", "일치하는 비밀번호가 없습니다.");
            } else {
            	HttpSession session = request.getSession();
                session.setAttribute("pwd", pwd);
                session.setAttribute("memberId", userID);
            }
			RequestDispatcher dispatcher = request.getRequestDispatcher("/member/changePwd.jsp");
			dispatcher.forward(request, response);
		}
	}
}
