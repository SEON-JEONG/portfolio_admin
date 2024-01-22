<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	HttpSession hs = request.getSession();
	String aid = (String)hs.getAttribute("aid");
	String aname = (String)hs.getAttribute("aname");

	String script = "";
	if(aid == "null" || aid == "" || aid == null){ 
		script = "<script>alert('올바른 접근 방식이 아닙니다');location.href='./index.jsp'; </script>";
	}
	out.print(script);
%> 
   
	<header class="headercss">
	    <div class="header_div">
	        <p><img src="./img/logo.png" class="logo_sm"> ADMINISTRATOR ADD</p>
	  <p><%=aname %> 관리자 <a href="#">[개인정보 수정]</a> <a href="./logout.do">[로그아웃]</a></p>
	    </div>
	</header>
