package com.freeflux.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.freeflux.admin.controller.action.AdminIndexAction;
import com.freeflux.admin.controller.action.AdminLoginAction;
import com.freeflux.admin.controller.action.AdminLogoutAction;
import com.freeflux.admin.controller.action.AdminMemberListAction;
import com.freeflux.admin.controller.action.AdminOrderListAction;
import com.freeflux.admin.controller.action.AdminOrderSaveAction;
import com.freeflux.admin.controller.action.AdminProductDetailAction;
import com.freeflux.admin.controller.action.AdminProductListAction;
import com.freeflux.admin.controller.action.AdminProductUpdateAction;
import com.freeflux.admin.controller.action.AdminProductUpdateFormAction;
import com.freeflux.admin.controller.action.AdminProductWriteAction;
import com.freeflux.admin.controller.action.AdminProductWriteFormAction;
import com.freeflux.admin.controller.action.AdminQnaDetailAction;
import com.freeflux.admin.controller.action.AdminQnaListAction;
import com.freeflux.admin.controller.action.AdminQnaResaveAction;
import com.freeflux.controller.action.*;
import com.freeflux.dao.OrderDAO;
import com.freeflux.dto.MemberVO;
import com.freeflux.dto.OrderVO;
import com.freeflux.dto.ProductVO;

@WebServlet("/main/*")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ProductVO productVO;
	OrderVO orderVO;

	public void init(ServletConfig config) throws ServletException {
		productVO = new ProductVO();
		orderVO = new OrderVO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nextPage = "";

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		HttpSession session;
		String path = request.getPathInfo();
		System.out.println("action:" + path);
		
		Action action=null;
	
		try {
			if (path == null) {
				action= new IndexAction();
			}else if(path.equals("/index")) {
				action= new IndexAction();
			}else if(path.equals("/main/catagory*")) {
				action=new ProductKindAction();
			}
			
			if (action != null) {
				action.execute(request, response);
			}
		} catch (Exception e) {
			System.out.println("컨트롤러 에러" + e.getMessage());
		}
	}
}
