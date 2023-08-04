<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<%@ include file="../header.jsp" %>   
<%@ include file="sub_img.html"%> 
<%@ include file="sub_menu.jsp" %>   
  <article>
    <h2>Modify User Information</h2>
    <form id="join" action="NonageServlet?command=join" method="post" name="formm">
      <fieldset>
        <legend>Basic Info</legend>
        <label>User ID</label>
        <input type="text" value="${loginUser.getId()}" size="12" disabled >
        <input type="hidden" name=id value="${loginUser.getId()}"><br>
        <label>Password</label> 
        <input type="password" name="pwd"><br> 
        <label>Retype Password</label> 
        <input type="password" name="pwdCheck"><br> 
        <label>Name</label>
        <input type="text" name="name" value="${loginUser.getName()}"><br> 
        <label>E-Mail</label>
        <input type="text" name="email" value="${loginUser.getEmail()}"><br>
        
      </fieldset>
      <fieldset>
        <legend>Optional</legend>
        <label>Zip Code</label> 
        <input type="text"       name="zipNum"  value="${loginUser.getZipNum()}" size="10" >      
        <input type="button"     value="주소 찾기" class="dup" onclick="post_zip()"><br>
        <label>Address</label> 
        <input type="text"        name="addr1"	value="${requestScope.add1}"   size="35">
        <input type="text"        name="addr2"  value="${requestScope.add2}"	size="30"> <br>
        <label>Phone Number</label> 
        <input  type="text"       name="phone"	value="${loginUser.getPhone()}"><br>
      </fieldset>
      <div class="clear"></div>
      <div id="buttons">
        <input type="button"    value="정보 수정"   class="submit" onclick="go_save__modify()"> 
        <input type="reset"      value="취소"     class="cancel">
      </div>
    </form>
  </article>
<%@ include file="../footer.jsp" %>
  