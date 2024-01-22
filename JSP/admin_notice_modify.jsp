<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="app" uri="http://java.sun.com/jsp/jstl/core"%>  
	<!-- JSTL 엔진 :  배열 갯수를 확인하는 라이브러리 -->
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>     
<%
	Date today = new Date();
 	request.setAttribute("today", today);
 	
 	int num = (int)request.getAttribute("num");
 	request.setAttribute("num", num);
 
%> 
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>공지사항</title>
    <link rel="stylesheet" type="text/css" href="./css/basic.css?v=${today}">
    <link rel="stylesheet" type="text/css" href="./css/login.css?v=${today}">
    <link rel="stylesheet" type="text/css" href="./css/main.css?v=${today}">
    <link rel="stylesheet" type="text/css" href="./css/notice.css?v=${today}">
    <link rel="icon" href="./img/logo.png" sizes="128x128">
    <link rel="icon" href="./img/logo.png" sizes="64x64">
    <link rel="icon" href="./img/logo.png" sizes="32x32">
    <link rel="icon" href="./img/logo.png" sizes="16x16">
    <script src="./js/jquery.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/vue@2"></script>
    <script src="./ckeditor/ckeditor.js"></script>
</head>
<body>
	<%@ include file="./admin_header.jsp"%>
	<%@ include file="./admin_menu.jsp"%>
    <form id="f">
    <input type="hidden" name="aidx" value="${ndOne.getAidx()}">
    <main class="maincss" id="admin_modify">
    <section>
        <p>공지사항 수정 페이지</p>
        <div class="write_view">
        <ul>
            <li>공지번호</li>
            <li>${num}번째 공지글</li>
        </ul>
        <ul>
            <li>공지사항 여부</li>
           
               <app:if test="${ndOne.getAck() == 'Y'}">
	            <li>
	            <label class="label_notice"><em class="cbox"><input type="checkbox" name="ack" value="Y" checked></em> 공지 출력</label> ※ 공지출력을 체크할 시 해당 글 내용은 최상단에 노출 되어 집니다.
	            </li>
            	</app:if>
            	
           		 <app:if test="${ndOne.getAck() == 'N'}">
	            <li>
	            <label class="label_notice"><em class="cbox"><input type="checkbox" name="ack" value="Y"></em> 공지 출력</label> ※ 공지출력을 체크할 시 해당 글 내용은 최상단에 노출 되어 집니다.
	            </li>
            	</app:if>
        </ul>
        <ul>
            <li>공지사항 제목</li>
            <li>
                <input type="text" class="notice_input1" name="asubject" value="${ndOne.getAsubject()}"> &nbsp; ※ 최대 150자까지 입력이 가능
            </li>
        </ul>
        <ul>
            <li>글쓴이</li>
            <li>
                <input type="text" class="notice_input2" name="aname" value="${ndOne.getAname()}" readonly>&nbsp; ※ 관리자 이름이 노출 됩니다.       
            </li>
        </ul>
        <ul>
            <li>첨부파일</li>
               <li>
	            	<app:if test="${ndOne.getAfile() == ''}">
		                <input type="file" name="userfile"> 
	            	</app:if>
	            	<app:if test="${ndOne.getAfile() != ''}">
		                <input type="file" name="userfile"> ※ 새로운 첨부파일 적용시 기본 첨부파일은 삭제 됩니다.
		                <em class="fileview">기존 첨부 파일명 : ${ndOne.getAfile().substring(15)} </em>
		                <input type="hidden" name="oldfile" value="${ndOne.getAfile()}">
	            	</app:if>
	            </li>
        </ul>
        <ul class="ul_height">
            <li>공지내용</li>
            <li>
                <textarea class="notice_input3" name="atext" id="boardtext" >${ndOne.getAtext()}</textarea>
            </li>
        </ul>
        </div>
        <div class="board_btn">
            <button class="border_list" type="button" @click="list_page()">공지목록</button>
            <button class="border_modify" type="button" @click="modify_page()" >공지수정 완료</button>
        </div>
    </section>
    <section></section>
    <section></section>
    </main>
    </form>
	<%@ include file="./admin_footer.jsp"%>
</body>
<script src="./js/admin_notice_modify.js?v=${today}"></script>
</html>