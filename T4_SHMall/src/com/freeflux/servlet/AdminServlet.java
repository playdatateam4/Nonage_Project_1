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
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ProductVO productVO;
	OrderVO orderVO;
	String charset = null;
	HashMap<String, Action> list = null;

	public void init(ServletConfig config) throws ServletException {

		productVO = new ProductVO();
		orderVO = new OrderVO();
		charset = config.getInitParameter("charset");
		list = new HashMap<String, Action>();
		list.put("/index.do", new IndexAction());
		list.put("/product_detail.do", new ProductDetailAction());
		list.put("/catagory.do", new ProductKindAction());
		list.put("/contract.do", new ContractAction());
		list.put("/join_form.do", new JoinFormAction());
		list.put("/id_check_form.do", new IdCheckFormAction());
		list.put("/find_zip_num.do", new FindZipNumAction());
		list.put("/join.do", new JoinAction());
		list.put("/login_form.do", new LoginFormAction());
		list.put("/iogin.do", new LoginAction());
		list.put("/logout.do", new LogoutAction());
		list.put("/cart_insert.do", new CartInsertAction());
		list.put("/cart_list.do", new CartListAction());
		list.put("/cart_delete.do", new CartDeleteAction());
		list.put("/order_list.do", new OrderListAction());
		list.put("/order_detail.do", new OrderDetailAction());
		list.put("/order_all.do", new OrderAllAction());
		list.put("/mypage.do", new MyPageAction());
		list.put("/qna_list.do", new QnaListAction());
		list.put("/qna_write_form.do", new QnaWriteFormAction());
		list.put("/qna_write.do", new QnaWriteAction());
		list.put("/qna_view.do", new QnaViewAction());

		list.put("/admin_login_form.do", new AdminIndexAction());
		list.put("/admin_login.do", new AdminLoginAction());
		list.put("/admin_logout.do", new AdminLogoutAction());
		list.put("/admin_product_list.do", new AdminProductListAction());
		list.put("/admin_product_write_form.do", new AdminProductWriteFormAction());
		list.put("/admin_product_write.do", new AdminProductWriteAction());
		list.put("/admin_product_detail.do", new AdminProductDetailAction());
		list.put("/admin_product_update_form.do", new AdminProductUpdateFormAction());
		list.put("/admin_product_update.do", new AdminProductUpdateAction());
		list.put("/admin_order_list.do", new AdminOrderListAction());
		list.put("/admin_order_save.do", new AdminOrderSaveAction());
		list.put("/admin_member_List.do", new AdminMemberListAction());
		list.put("/admin_qna_list.do", new AdminQnaListAction());
		list.put("/admin_qna_detail.do", new AdminQnaDetailAction());
		list.put("/admin_qna_repsave.do", new AdminQnaResaveAction());
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

		String action = request.getPathInfo();
	//	String action2 = request.getRequestURI();
		System.out.println("action:" + action);
	//	System.out.println("action2:" + action2);
	
		HttpSession session = request.getSession();
	
		try {

			if (action == null) {

				nextPage = "/index.jsp";
				RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
				dispatch.forward(request, response);
				
			}else {
				Action subAction = list.get(action);
				
				System.out.println(subAction);
				System.out.println();
				System.out.println();
				
				if(subAction == null) {
					RequestDispatcher dispatch = request.getRequestDispatcher(action);
					System.out.println("action에서 값이 넘어오나요" +action);
					dispatch.forward(request, response);
				}else {
					subAction.execute(request,response);
				}
				
			}
//			} else if (action.equals("/OrderAllAction.do")) {
//				MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
//				
//				if (loginUser == null) {
//					nextPage = "/index.jsp";
//				
//				} else {				
//					ArrayList<Integer> oseqList =orderPageService.SelectSeq(loginUser.getId());
//					ArrayList<OrderVO> orderList = new ArrayList<OrderVO>();
//						
//				for(int oseq : oseqList) {
//					ArrayList<OrderVO> orderListIng = orderPageService.OrderAllAction(loginUser.getId(), "%", oseq);
//				
//					OrderVO orderVO = orderListIng.get(0);
//					orderVO.setPname(orderVO.getPname() + " 외 " + orderListIng.size() + "건");
//					
//					// 확인용 코드
//					System.out.println(orderVO.getIndate());
//
//					int totalPrice = 0;
//					for (OrderVO ovo : orderListIng) {
//						totalPrice += ovo.getPrice2() * ovo.getQuantity();
//				}
//					orderVO.setPrice2(totalPrice);
//					orderList.add(orderVO);
//				}
//				request.setAttribute("title", "총 주문 내역");
//				request.setAttribute("orderList", orderList);
//				
//				nextPage = "/mypage/mypage.jsp";
//				}
//							
//			} else if (action.equals("/OrderDetailAction.do")) {
//				ArrayList<OrderVO> orderList =  orderPageService.OrderDetailAction();
//				
//				request.setAttribute("orderList", orderList);
//				nextPage = "/mypage/mypage.jsp";
//
//			} else if (action.equals("/OrderInsertAction.do")) {
//				MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
//
//				return;
//			} else if (action.equals("/OrderListAction.do")) {
//
//				OrderDAO orderDAO = OrderDAO.getInstance();
//				MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
//				ArrayList<Integer> oseqList = orderDAO.selectSeqOrderIng(loginUser.getId());
//				ArrayList<OrderVO> orderList = new ArrayList<OrderVO>();
//				for (int oseq : oseqList) {
//					ArrayList<OrderVO> orderListIng = orderDAO.listOrderById(loginUser.getId(), "%", oseq);
//
//					OrderVO orderVO = orderListIng.get(0);
//					orderVO.setPname(orderVO.getPname() + " 외 " + orderListIng.size() + "건");
//					System.out.println(orderVO.getIndate());
//
//					int totalPrice = 0;
//					for (OrderVO ovo : orderListIng) {
//						totalPrice += ovo.getPrice2() * ovo.getQuantity();
//					}
//
//					orderVO.setPrice2(totalPrice);
//					orderList.add(orderVO);
//				}
//				orderList = orderPageService.OrderListAction();
//				
//				request.setAttribute("orderList", orderList);
//				nextPage = "/mypage/orderList.jsp";
//
//			} else if (action.equals("/ProductDetailAction.do")) {
//				String pseq = request.getParameter("pseq").trim();
//				ProductVO productVO = productPageService.ProductDetailAction(pseq);
//				
//				request.setAttribute("productVO", productVO);
//				nextPage = "/product/productDetail.jsp";
//
//			} else if (action.equals("/ProductKindAction.do")) {
//				String kind = request.getParameter("kind").trim();
//				ArrayList<ProductVO> productVO = productPageService.productKindAction(kind);
//		
//				request.setAttribute("productKindList", productVO);
//				nextPage = "/product/productKind.jsp";
//			}else if (action.equals("/LoginFormAction.do")) {
//				nextPage = "/member/login.jsp";
//				
//				String id = request.getParameter("id").trim();
//				request.setAttribute("id", id);
//				
//			
//			
//			}
//			RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
//			dispatch.forward(request, response);

		} catch (Exception e) {
			System.out.println("컨트롤러 에러" + e.getMessage());
		}
	}
}
