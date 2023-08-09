function go_save__join() {
  if (document.formm.id.value == "") {
    alert("아이디를 입력하여 주세요.");
    document.formm.id.focus();
  } else if (document.formm.id.value != document.formm.reid.value) {
    alert("중복확인을 클릭하여 주세요.");
    document.formm.id.focus();
  } else if (document.formm.pwd.value == "") {
    alert("비밀번호를 입력해 주세요.");
    document.formm.pwd.focus();
  } else if ((document.formm.pwd.value != document.formm.pwdCheck.value)) {
    alert("비밀번호가 일치하지 않습니다.");
    document.formm.pwd.focus();
  } else if (document.formm.name.value == "") {
    alert("이름을 입력해 주세요.");
    document.formm.name.focus();
  } else if (document.formm.email.value == "") {
    alert("이메일을 입력해 주세요.");
    document.formm.email.focus();
  } else {
    document.formm.action = "NonageServlet?command=join";
    document.formm.submit();
  }
}

function idcheck() {
  if (document.formm.id.value == "") {
    alert('아이디를 입력하여 주십시오.');
    document.formm.id.focus();
    return;
  }
  var url = "NonageServlet?command=id_check_form&id=" 
+ document.formm.id.value;
  window.open( url, "_blank_1",
"toolbar=no, menubar=no, scrollbars=yes, resizable=no, width=330, height=200");
}

function post_zip() {
  var url = "NonageServlet?command=find_zip_num";
  window.open( url, "_blank_1",
"toolbar=no, menubar=no, scrollbars=yes, resizable=no, width=550, height=300, top=300, left=300, ");
}

function go_next() {
  if (document.formm.okon1[0].checked == true) {
    document.formm.action = "NonageServlet?command=join_form&contract=agree";
    document.formm.submit();
  } else if (document.formm.okon1[1].checked == true) {
    alert('약관에 동의하셔야만 합니다.');
  }
}

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
						"width=400, height=500, history=no, resizable=no, status=no, scrollbars=yes, menubar=no").document.write(response);
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
