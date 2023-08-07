package com.freeflux.controller.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.freeflux.dao.MemberDAO;

public class ChangePasswordAction implements Action {
       
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String userId = request.getParameter("userId");
		    String newPwd = request.getParameter("newPwd");
		    
		    MemberDAO memberDAO = MemberDAO.getInstance();
		    try {
		    	boolean result = memberDAO.change_pwd(newPwd, userId);
		    
		    if(result) {
		    	 response.setContentType("text/html;charset=utf-8");
		    	 PrintWriter out = response.getWriter();
		    	 out.println("<html>");
		    	 out.println("<script>");
		    	 out.println("alert('비밀번호가 성공적으로 변경되었습니다.');");
		    	 out.println("window.close();");
		    	 out.println("window.opener.location.href='NonageServlet?command=login_form';");
		    	 out.println("</script>");
		    	 out.println("</html>");
		    }else {
		    	response.setContentType("text/html;charset=utf-8");
		        PrintWriter out = response.getWriter();
		        out.println("<html>");
		        out.println("<script>");
		        out.println("alert('비밀번호 변경 실패!');");
		        out.println("location.href='member/changePwd.jsp';");
		        out.println("</script>");
		        out.println("</html>");
		    }
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}

