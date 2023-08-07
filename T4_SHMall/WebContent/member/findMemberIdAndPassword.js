function findMemberId() { //이름,이메일로 '찾기' 버튼
	var form = document.findId;
	  if (form.name.value == "") {
	    alert("이름을 입력해주세요");
	    form.name.focus();
	    return;
	  }
	  if (form.email.value == "") {
	    alert("이메일을 입력해주세요");
	    form.email.focus();
	    return;
	  }
	  var name = form.name.value;
	  var email = form.email.value;
	   
	    $.ajax({
	        type: "POST",
	        url: "NonageServlet",
	        data: {
	            command: "find_id",
	            name: name,
	            email: email
	        },
	        success: function(response) {
	            window.open("",
						"아이디 찾기",
						"width=400, height=500, history=no, resizable=no, status=no, scrollbars=yes, menubar=no").document.write("아이디는 " + response + "입니다.");
	        }
	    });
	}
function findPassword() {//아이디, 이름, 이메일을 통해 비밀번호 찾기
	
	var form = document.findPW;
	
	if (form.memberId.value == "") {
	    alert("아이디를 입력하여 주세요.");
	    form.memberId.focus();
	    return;
	  }
	if (form.name.value == "") {
	    alert("이름을 입력해 주세요.");
	    form.name.focus();
	    return;
	  }
	if (form.email.value == "") {
	    alert("이메일을 입력해 주세요.");
	    form.email.focus();
	    return;
	}
	var memberId= form.memberId.value;
	var name = form.name.value;
	var email = form.email.value;
	
	$.ajax({
        type: "POST",
        url: "NonageServlet",
        data: {
            command: "find_pwd",
            memberId : memberId,
            name: name,
            email: email
        },
        success: function(response) {
            window.open("member/changePwd.jsp?userId=" + memberId,
					"비밀번호 변경",
					"width=600, height=500, history=no, resizable=no, status=no, scrollbars=yes, menubar=no");
        }
    });
}
function modPwd(){
	var form = document.cpwd;
	
	if (form.newPwd.value == "") {
	    alert("비밀번호를 입력해주세요.");
	    form.newPwd.focus();
	    return;
	  }
	document.form.action = "T4_SHMall1/NonageServlet?command=change_pwd"
	document.form.submit();
}