<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<%
  request.setCharacterEncoding("UTF-8");
%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 변경</title>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<style>
body {
font-family: 'Arial', sans-serif; 
background-color: #8a2be2; 
display: flex;
justify-content: center;
align-items: center;
height: 100vh;
}
.container {
padding: 20px;
background-color: #fff;
border-radius: 5px;
box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
width: 80%;
max-width: 300px;
}
button {
display: block;
margin-top: 10px;
padding: 10px 15px;
background-color: #007BFF;
color: #ffffff;
border: none;
border-radius: 5px;
cursor: pointer;
transition: background-color 0.2s;
}
button:hover {
background-color: #0056b3;
}
#lblError {
color: red;
}
</style>
<script type="text/javascript">
	$(document).ready(function(){
		
		//1) lblError 레이어 클리어
		$('#txtPassword').keyup(function(){ //keyup :해당 위치에서 키보드입력이 up(키가 떼지면)
			// 눌럿을때 하려면 down	
		
			$('#lblError').text(''); // 제거가 아니라 클리어
			
		}); 
		
		//2) 암호 확인 기능 구현
		$('#txtPasswordConfirm').keyup(function(){
			if($('#txtPassword').val() != $('#txtPasswordConfirm').val()){
				$('#lblError').text(''); //클리어
				$('#lblError').html("<b>암호가 틀립니다.</b>"); // 레이어에 html 출력
			}
			else{
				$('#lblError').text(''); //클리어
				$('#lblError').text("암호가 맞습니다."); // 레이어에 html 출력
			}
		});
	});
	function modPwd(){
		if (document.formm.newPwd.value == "") {
		    alert("비밀번호를 입력해주세요.");
		    document.formm.newPwd.focus();
		    return;
		  }
		document.formm.action = "${contextPath}/find/change_pwd";
	    document.formm.submit();
	}
</script>
</head>
<body>
<div class="container">
<form id="cpwd" method="post" name="formm" >
	<table>
	<tr>
		<td><input type="hidden" name="userId" value="<%= session.getAttribute("memberId") %>"></td>
	</tr>
	<tr>
		<td>새로운 암호 : </td>
		<td><input type="password" id="txtPassword" name="newPwd" size="20" /></td>	
	</tr>
	<tr>
		<td>암호확인 : </td>
		<td><input type="password" id="txtPasswordConfirm" name="confirmPwd" size="20" /></td>	
	</tr>
	<tr>
        <td colspan="2">
        <input type="submit" value="비밀번호 변경" onclick="modPwd()"></td>
    </tr>
	</table>
	
	<div id="lblError">암호를 입력하시오.</div>
</div>
</form>
</body>
</html>