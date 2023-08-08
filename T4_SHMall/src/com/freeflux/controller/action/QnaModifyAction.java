package com.freeflux.controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.freeflux.dao.QnaDAO;
import com.freeflux.dto.QnaVO;

public class QnaModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "NonageServlet?command=qna_list";

		HttpSession session = request.getSession();
		
		QnaVO qnaVO = new QnaVO();
			try {
				int qseq = Integer.parseInt(request.getParameter("qseq"));
				qnaVO.setQseq(qseq);
				session.setAttribute("qseq", qseq);
			}catch(NumberFormatException e) {
				e.printStackTrace();
			}
		qnaVO.setSubject(request.getParameter("subject"));
		qnaVO.setContent(request.getParameter("content"));
		
		QnaDAO qnaDAO = QnaDAO.getInstance();
		qnaDAO.modifyqna(qnaVO);
		
		response.sendRedirect(url);
	}

}
