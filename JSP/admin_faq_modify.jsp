<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Date today = new Date();
 	request.setAttribute("today", today);
%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="./css/basic.css?v=${today}">
<link rel="stylesheet" type="text/css" href="./css/login.css?v=${today}">
<link rel="stylesheet" type="text/css" href="./css/main.css?v=${today}">
<link rel="stylesheet" href="./css/faq.css?v=${today}">
<link rel="icon" href="./img/logo.png" sizes="128x128">
<link rel="icon" href="./img/logo.png" sizes="64x64">
<link rel="icon" href="./img/logo.png" sizes="32x32">
<link rel="icon" href="./img/logo.png" sizes="16x16">
<title>고객관리 FAQ - 내용 수정</title>
<script src="https://cdn.jsdelivr.net/npm/vue@2"></script>
<script src="./ckeditor/ckeditor.js"></script>
</head>
<body>
<%@ include file="./admin_header.jsp"%>
<%@ include file="./admin_menu.jsp"%>	
<form id ="f">
<input type="hidden" name="fidx" value="${faq.getFidx()}">
<main class="maincss" id="faq_modify">
<section class="page_section">
<p>FAQ 확인 및 수정</p>
<div class="listbody">
    <div class="procho">
       <section class="data_listsview">
       <ol>
       <li>질문 제목</li>
       <li><input type="text" name="fsubject" value="${faq.getFsubject()}" class="notice_in in1"></li>
       <li>글쓴이</li>
       <li><input type="text" name="aname" class="notice_in in2" value="${faq.getAname()}" readonly></li> <li style="height:520px;">질문 내용</li>
       <li style="height:520px; padding-top: 10px; padding-left:10px;">
       <textarea id="boardtext" name="ftext" class="notice_in in3">${faq.getFtext()}</textarea>
       </li>      
       </ol>
       <span class="notice_btns">
        <input type="button" value="FAQ 리스트" onclick="location.href='./admin_faq.do'" class="meno_btn1">
        <input type="button" value="FAQ 수정" @click="modifyok()" class="meno_btn2"></span>
       </section>
    </div>
</div> 
</section>
</main>
</form>
<%@ include file="./admin_footer.jsp"%>
</body>
<script src="./js/faq_modify.js?v=${today}"></script>
</html>