<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>

<% String login_result=(String)request.getAttribute("login_result"); %>

<script type="text/javascript">
	var login_result = '<%= login_result %>';
	if(login_result==="deleted"){
		alert("탈퇴한 회원입니다. 확인후 다시 로그인해주세요.");
		history.go(-1);
	}else {
		alert("로그인 실패하셨습니다. 확인후 다시 로그인해주세요.");
		history.go(-1);
	}
</script>



<%@ include file="../footer.jsp"%>