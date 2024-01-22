<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	Date today = new Date();
 	request.setAttribute("today", today);
%>     
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>공지사항 등록 페이지</title>
    <link rel="stylesheet" type="text/css" href="./css/basic.css?v=${today}">
    <link rel="stylesheet" type="text/css" href="./css/login.css?v=${today}">
    <link rel="stylesheet" type="text/css" href="./css/main.css?v=${today}">
    <link rel="stylesheet" type="text/css" href="./css/notice.css?v=${today}">
    <link rel="icon" href="./img/logo.png" sizes="128x128">
    <link rel="icon" href="./img/logo.png" sizes="64x64">
    <link rel="icon" href="./img/logo.png" sizes="32x32">
    <link rel="icon" href="./img/logo.png" sizes="16x16">
    <script src="./js/jquery.js"></script>
</head>

<script src="./ckeditor/ckeditor.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue@2"></script>

<body>
    <header class="headercss">
        <div class="header_div">
            <p><img src="./img/logo.png" class="logo_sm"> ADMINISTRATOR</p>
            <p>홍길동 관리자 <a href="#">[개인정보 수정]</a> <a href="#">[로그아웃]</a></p>
        </div>
    </header>
    <nav class="navcss">
        <div class="nav_div">
            <ol>
                <li title="사이트 기본설정">사이트 기본설정</li>
                <li title="회원관리">회원관리</li>
                <li title="공지사항">공지사항</li>
                <li title="항공공사 및 번호 등록">항공사 및 번호 등록</li>
                <li title="좌석 및 예매등록">좌석 및 예매등록</li>
                <li title="예매 리스트">예매 리스트</li>
                <li title="고객관리 FAQ">고객관리 FAQ</li>
            </ol>
        </div>
    
    </nav>
    
    <!-- 공지사항 등록 페이지 -->
    <form id="f">
    <main class="maincss" id="page_main">
    <section>
        <p>공지사항 등록페이지</p>
        <div class="write_view">
        <ul>
            <li>공지사항 여부</li>
            <li>
                <label class="label_notice"><em class="cbox"><input type="checkbox" name="ack" ></em> 공지 출력</label> ※ 공지출력을 체크할 시 해당 글 내용은 최상단에 노출 되어 집니다.
            </li>
        </ul>
        <ul>
            <li>공지사항 제목</li>
            <li>
                <input type="text" name="asubject" v-model="data1" class="notice_input1"> ※ 최대 150자까지 입력이 가능
            </li>
        </ul>
        <ul>
            <li>글쓴이</li>
            <li>
                <input type="text" name="aname" v-model="data2" class="notice_input2"> ※ 관리자 이름이 노출 됩니다.       
            </li>
        </ul>
        <ul>
            <li>첨부파일</li>
            <li>
                <input type="file" name="userfile"> ※ 첨부파일 최대 용량은 2MB 입니다.       
            </li>
        </ul>
        <ul class="ul_height">
            <li>공지내용</li>
            <li >
                <textarea class="notice_input3" id="boardtext" name="atext" placeholder="공지내용을 입력하세요!"></textarea>
            </li>
        </ul>
        </div>
        <div class="board_btn">
            <button type="button" class="border_del" @click="admin_notice()">공지목록</button>
            <button class="border_add" @click="notice_write()">공지등록</button>
        </div>
    </section>
    <section></section>
    <section></section>
    </main>
    </form>
    <!-- 공지사항 등록 페이지 -->
	<%@ include file="./admin_footer.jsp" %>
    </body>
    <script>
    window.onload = function(){
    	var ck = CKEDITOR.replace("boardtext",{
    		width:800,
    		height:300,
    	});
    }
</script>
    <script src="./js/admin_notice_write.js?v=${today}"></script>
    </html>