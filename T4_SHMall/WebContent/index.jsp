<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<!--메인 이미지 들어가는 곳 시작 --->
<div class="clear"></div>
<div id="main_img">
	<img src="${contextPath}/images/main_img.jpg">
</div>
<!--메인 이미지 들어가는 곳 끝--->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  request.setCharacterEncoding("UTF-8");
%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />

<div id="front">
	<h2>New Item</h2>
	<div id="bestProduct">
	<!-- foreach 에서 사용하는 item = collection 객체 var = 변수명  -->
		<c:forEach items="${newProductList}" var="productVO">
			<div id="item">
        
       <!-- contextPath + mall/productDeatailAction.do 라는 하이퍼 링크를 생성하면서 
			?pseq= 에 productVO의 pseq값을 인자로 전달한다 -->
				<a
					href="${contextPath}/prdt/detail?pseq=${productVO.pseq}">
					<img src="${contextPath}/product_images/${productVO.image}" />

					<h3>${productVO.name}</h3>
					<p>${productVO.price2}</p>
				</a>
			</div>
		</c:forEach>
	</div>
	<div class="clear"></div>

	<h2>Best Item</h2>
	<div id="bestProduct">
		<c:forEach items="${bestProductList}" var="productVO">
			<div id="item">
				<a
					href="${contextPath}/prdt/detail?pseq=${productVO.pseq}">
					
					<img src="product_images/${productVO.image}" />
					<h3>${productVO.name}</h3>
					<p>${productVO.price2}</p>
				</a>
			</div>
		</c:forEach>
	</div>
	<div class="clear"></div>
</div>

<%@ include file="../footer.jsp"%>