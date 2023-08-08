function go_cart() {
  if (document.formm.quantity.value == "") {
    alert("수량을 입력하여 주세요.");
    document.formm.quantity.focus();
  } else {
	
    document.formm.action = "NonageServlet?command=cart_insert";
    document.formm.submit();
  }
}

function go_cart_delete() {
  var count = 0;

  if (document.formm.cseq.length == undefined) {
    if (document.formm.cseq.checked == true) {
      count++;
    }
  }

  for ( var i = 0; i < document.formm.cseq.length; i++) {
    alert("" + document.formm.cseq[i].checked);
    if (document.formm.cseq[i].checked == true) {
      count++;
      alert("" + count);
    }
  }
  if (count == 0) {
    alert("삭제할 항목을 선택해 주세요.");

  } else {
    document.formm.action = "NonageServlet?command=cart_delete";
    document.formm.submit();
  }
}

function go_order_insert() {
  document.formm.action = "NonageServlet?command=order_insert";
  document.formm.submit();
}

function go_order_delete() {
  var count = 0;

  if (document.formm.oseq.length == undefined) {
    if (document.formm.oseq.checked == true) {
      count++;
    }
  }

  for ( var i = 0; i < document.formm.oseq.length; i++) {
    if (document.formm.oseq[i].checked == true) {
      count++;
    }
  }
  if (count == 0) {
    alert("삭제할 항목을 선택해 주세요.");

  } else {
    document.formm.action = "NonageServlet?command=order_delete";
    document.formm.submit();
  }
}


function go_order_now() {
	  if (document.formm.quantity.value == "") {
    alert("수량을 입력하여 주세요.");
    document.formm.quantity.focus();
  } else {
    document.formm.action = "NonageServlet?command=cart_insert&go=orderInsert";
  	document.formm.submit();
  }
}

function go_save__modify() {
	  if (document.formm.pwd.value == "") {
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
	    document.formm.action = "NonageServlet?command=modifyUsr";
	    document.formm.submit();
	  }
}

function delete_Usr() {
	if (confirm("회원 탈퇴를 하시겠습니까?")) {
		alert("회원탈퇴 처리되었습니다. 이용해주셔서 감사합니다.");
		document.formm.action = "NonageServlet?command=deleteUsr";
	    document.formm.submit();
		} else {
		  return;
		}
	}