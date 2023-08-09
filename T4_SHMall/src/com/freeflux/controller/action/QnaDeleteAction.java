package com.freeflux.controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.freeflux.dao.QnaDAO;

public class QnaDeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "NonageServlet?command=qna_list";
		
		String qseq = request.getParameter("qseq");
		QnaDAO qnaDAO = QnaDAO.getInstance();
		
		qnaDAO.deleteQna(qseq);
		response.sendRedirect(url);
	}

}
