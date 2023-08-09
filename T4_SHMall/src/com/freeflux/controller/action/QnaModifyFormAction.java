package com.freeflux.controller.action;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.freeflux.dao.QnaDAO;
import com.freeflux.dto.QnaVO;

public class QnaModifyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "qna/qnaModForm.jsp";
		
									// null 방지
		int qseq = Integer.parseInt(Optional.ofNullable(request.getParameter("qseq")).orElse("0"));
		
        QnaDAO qnaDAO = QnaDAO.getInstance();
        QnaVO qnaVO = qnaDAO.getQna(qseq);
        
        request.setAttribute("qnaVO", qnaVO);
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
