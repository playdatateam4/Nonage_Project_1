package com.freeflux.controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.freeflux.dao.MemberDAO;

public class FindIdAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String name = request.getParameter("name");
	    String email = request.getParameter("email");
	    
	    MemberDAO memberDAO = MemberDAO.getInstance();
	    String id = memberDAO.find_id(name, email);
	    
	    response.setContentType("text/html;charset=utf-8");
	    response.setCharacterEncoding("UTF-8");
	    if (id != null) {
	        response.getWriter().write(id);
	    } else {
	        response.getWriter().write("일치하는 아이디가 없습니다");
	    }
	}


}
