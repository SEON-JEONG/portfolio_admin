<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <title>항공사 및 번호 등록</title>
    <link rel="stylesheet" type="text/css" href="./css/basic.css">
    <link rel="stylesheet" type="text/css" href="./css/login.css?v=1">
    <link rel="stylesheet" type="text/css" href="./css/main.css">
    <link rel="stylesheet" type="text/css" href="./css/ticketing.css?v=1">
    <link rel="icon" href="./img/logo.png" sizes="128x128">
    <link rel="icon" href="./img/logo.png" sizes="64x64">
    <link rel="icon" href="./img/logo.png" sizes="32x32">
    <link rel="icon" href="./img/logo.png" sizes="16x16">
    <script src="./js/jquery.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/vue@2"></script>
</head>
<body>
<%@ include file="./admin_header.jsp" %>
<%@ include file="./admin_menu.jsp" %>
<main class="maincss" id="vue">
<section>
    <p>예매 리스트</p>
	<div class="subpage_view">
	    <span>
	        <form id="f">
	        <select class="p_select1" name="search_ch" v-model="user_search_ch" placeholder="선택">
	            <option>고객명</option>
	            <option>연락처</option>
	        </select>
	        <input type="text" name="search" v-model="user_search" class="p_input1" placeholder="검색할 단어를 입력하세요">
	        <input type="submit" value="검색" title="예매 정보 검색" class="p_submit"  @click="goSearch">
	        </form>
	    </span>
	</div>
	<div class="subtick_view2">
	    <ul>
	        <li></li>
	        <li>공항코드</li>
	        <li>항공사명</li>
	        <li>항공코드</li>
	        <li>출발지</li>
	        <li>도착지</li>
	        <li>좌석형태</li>
	        <li>고객명</li>
	        <li>연락처</li>
	        <li>예약일</li>
	        <li>인원수</li>
	        <li>결제금액</li>
	    </ul>
	    <!-- 반복 구간 -->
<app:forEach var="li" items="${list}">
<ul>
    <li><input type="radio" v-model="ch_del" value="${li.getRidx()}"></li>
    <li>${li.getAirplane_code()}</li>
    <li style="text-align: left; text-indent: 3px;">${li.getAirname()}</li>
    <li>${li.getAircode()}</li>
    <li>${li.getStart_point()}</li>
    <li>${li.getEnd_point()}</li>
    <li>${li.getSeat_form()}</li>
    <li>${li.getUname()}</li>
    <li>${li.getUtel()}</li>
    <li>${li.getUreserve_date()}</li>
    <li>${li.getUcount()} 인</li>
    <li><fmt:formatNumber value="${li.getRprice()*li.getUcount()}" pattern="#,###" />원</li>
</ul>
</app:forEach>
<app:if test="${list.size() == 0}">
<ul>
    <li style="width: 100%;">예매 하신 사용자 또는 검색하신 내용이 없습니다.</li>
</ul>
</app:if>
</div>
<div class="subpage_view4">
    <span style="text-align: right;">
    <input type="button" value="예매취소" title="예매취소" class="p_button p_button_color2" @click="del_reserve">
    </span>
</div>
</section>
</main>
<%@ include file="./admin_footer.jsp" %>
</body>
<script>
   		var user_search = "${search}"
   		var user_search_ch = "${search_ch}"
   		console.log(user_search_ch)
</script>
<script src="./js/admin_ticketing.js?v=${today}"></script>
</html>