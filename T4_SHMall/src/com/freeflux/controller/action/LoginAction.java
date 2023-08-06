package com.freeflux.controller.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.freeflux.dao.MemberDAO;
import com.freeflux.dao.WorkerDAO;
import com.freeflux.dto.MemberVO;
import com.freeflux.dto.WorkerVO;

public class LoginAction implements Action {

	private static boolean isRecordFound = false;
	
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "member/login_fail.jsp";
        String login_result = "fail";

        HttpSession session = request.getSession();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<Callable<LoginTypeAction>> tasks = new ArrayList<>();
        tasks.add(() -> searchMember(request, response));
        tasks.add(() -> searchWorker(request, response));

        LoginTypeAction logintype = null;

        try {
            List<Future<LoginTypeAction>> results = executorService.invokeAll(tasks);

            for (Future<LoginTypeAction> future : results) {
                LoginTypeAction result = future.get();
                if (result != null) {
                    logintype = result;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

        if (logintype != null) {
            if (logintype.getRole().equals("member")) {
                MemberVO memberVO = logintype.getMemberVO();
                if (memberVO != null) {
                    if (memberVO.getPwd().equals(request.getParameter("pwd")) && memberVO.getUseyn().equals("y")) {
                        session.removeAttribute("id");
                        session.setAttribute("loginUser", memberVO);
                        url = "NonageServlet?command=index";
                    } else {
                        if (memberVO.getUseyn().equals("n")) {
                            login_result = "deleted";
                        }
                    }
                }
            } else if (logintype.getRole().equals("worker")) {
                WorkerVO workerVO = logintype.getWorkerVO();
                if (workerVO != null) {
                    if (workerVO.getPwd().equals(request.getParameter("pwd"))) {
                        session.removeAttribute("id");
                        session.setAttribute("loginUser", workerVO);
                        session.setAttribute("workerId", workerVO.getId());
                        url = "NonageServlet?command=index";
                    }
                }
            }
        }

        request.setAttribute("login_result", login_result);
        request.getRequestDispatcher(url).forward(request, response);
    }
	
	private LoginTypeAction searchMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String id = request.getParameter("id");
		
		MemberDAO memberDAO = MemberDAO.getInstance();
		MemberVO memberVO = memberDAO.getMember(id);

		LoginTypeAction logintype = null;
		
		if (memberVO != null) {
			logintype = new LoginTypeAction("member", memberVO);
			}
		
		return logintype;
	}
	
	private LoginTypeAction searchWorker(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		String id = request.getParameter("id");
		
		WorkerDAO workerDAO = WorkerDAO.getInstance();
		WorkerVO workerVO = workerDAO.getWorker(id);

		LoginTypeAction logintype = null;
		
		if (workerVO != null) {
			logintype = new LoginTypeAction("worker", workerVO);
			}
		return logintype;
	}
}
