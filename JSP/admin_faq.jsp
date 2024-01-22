<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" uri="http://java.sun.com/jsp/jstl/core" %> 
    
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
<title>고객관리 FAQ</title>
<script src="https://cdn.jsdelivr.net/npm/vue@2"></script>
</head>
<body>

<%@ include file="./admin_header.jsp"%>
<%@ include file="./admin_menu.jsp"%>
<!-- FAQ 리스트 시작 -->
<form id="f">
<main class="maincss" id="faq_list">
<section class="page_section">
<p>FAQ 관리페이지</p>
<div class="listbody">
    <div class="procho">
       <section class="search_part">
        <ol>
        <li>FAQ 모든 내용 검색</li>
        <li>
        <input type="text" name="search" class="search_input" v-model="search">
        <input type="submit" value="검색" @click="search_faq" class="datebtn">
        </li>        
        <li></li>
        <li></li> 
        </ol>
       </section> 
       <section class="data_listsview2">
       <ul>
        <li>QA</li>
        <li>질문/답변</li>
        <li>글쓴이</li>
        <li>등록일</li>
        <li>삭제</li>
       </ul>
          
<!-- FAQ 샘플 HTML 코드 시작 -->           
     <span>
     <app:forEach var="faq" items="${list}"><!-- java에서 만든 list를 faq라고 지정 -->
       <ul>
        <li>Q</li>
        <li style="text-align: left; justify-content: flex-start;" @click="modify_go('${faq.getFidx()}')">${faq.getFsubject()}</li>
        <li>${faq.getAname()}</li>
        <li>${faq.getFdate().substring(0,10)}</li>
        <li>
        <input type="button" value="삭제" @click="delete_faq('${faq.getFidx()}')" class="delbtn">
        </li>
       </ul>
       </app:forEach>
     </span>
<!-- FAQ 샘플 HTML 코드 끝 -->          
        
        
       <app:if test="${list.size() == 0}"> 
       <ul class="nodatas">
        <li>등록된 FAQ 내용 없습니다.</li>
       </ul>
       </app:if>
 
       <span class="notice_btns">
       <input type="button" value="FAQ 등록" onclick="location.href='./admin_faq_write.do'" class="meno_btn2"></span>
       <aside>
        <div class="subpage_view3">
            <ul class="pageing">
                <li><img src="./ico/double_left.svg"></li>
                <li><img src="./ico/left.svg"></li>
                <li>1</li>
                <li><img src="./ico/right.svg"></li>
                <li><img src="./ico/double_right.svg"></li>
            </ul>
        </div>
       </aside>
       </section>
    </div>
</div> 
</section>
</main>
</form>
<%@ include file="./admin_footer.jsp"%>
</body>
<script>var search = "${search}"</script>
<script src="./js/admin_faq.js?v=${today}"></script>
</html>