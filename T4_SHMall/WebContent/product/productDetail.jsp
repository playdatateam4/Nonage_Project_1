<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>  
<%@ include file="../header.jsp" %>  
<%@ include file="sub_img.html"%> 
<%@ include file="sub_menu.html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>  
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    $('.cancel').click(function() {
        window.history.back();
    });
});
</script>    
  <article>
    <h1> Item </h1>
    <div id="itemdetail" >
      <form  method="post" name="formm">    
        <fieldset>
          <legend> Item detail Info</legend>  
          <a href="${contextPath}/prdt/detail&pseq=${productVO.pseq}">         
            <span style="float: left;">
              <img  src="${contextPath}/product_images/${productVO.image}"  />
            </span>              
            <h2> ${productVO.name} </h2>  
          </a>    
          <label> 가 격 :  </label>  
          <p> ${productVO.price2} 원</p>  
          <label> 수 량 : </label>
          <input  type="text"      name="quantity"  size="2"      value="1"><br>
          <input  type="hidden"    name="pseq"       value="${productVO.pseq}"><br>
           <label> 사용 : </label>
           <c:choose>
				<c:when test="${productVO.useyn=='y'}">
					<p> 중고 </p>
				</c:when>
				<c:otherwise>
					<p> 신상품 </p>
				</c:otherwise>
			</c:choose>
            <label> 재고 : </label>
            <c:choose>
				<c:when test="${productVO.inventory>0}">
					<p> ${productVO.inventory}개 </p>
				</c:when>
				<c:otherwise>
					<p> 재고없음 </p>
				</c:otherwise>
			</c:choose>
        </fieldset>
        <div class="clear"></div>
        <div id="buttons">
          <input type="button" value="장바구니에 담기"   class="submit"    onclick="go_cart()"> 
          <input type="button" value="즉시 구매"       class="submit"    onclick="go_order_now()"> 
          <input type="reset"  value="취소"           class="cancel">
        </div>
      </form>  
    </div>
  </article>
<%@ include file="../footer.jsp" %>    