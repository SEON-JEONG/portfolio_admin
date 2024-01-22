<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
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
    <link rel="stylesheet" type="text/css" href="./css/product.css?v=5">
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
	    <p>항공편 및 번호 등록 페이지</p>
	<div class="subpage_view">
	    <span>등록된 항공사 ${ea}건</span>
	    <span>
	        <form id="f">
	        <select class="p_select1" name="search_ch" v-model="user_search_ch">
	            <option value="s_airname">항공사명</option>
	            <option value="s_aircode">항공코드</option>
	        </select>
	        <input type="text" name="search" v-model="user_search" class="p_input1" placeholder="검색어를 입력해 주세요">
	        <input type="submit" value="검색" title="항공검색" class="p_submit" @click="goSearch">
	        </form>
	    </span>
	</div>
	<div class="subpage_view2">
	    <ul>
	        <li><input type="checkbox" v-model="all" @change="alldata"></li>
	        <li>공항코드</li>
	        <li>항공사명</li>
	        <li>항공코드</li>
	        <li>항공편명</li>
	        <li>출발지</li>
	        <li>도착지</li>
	        <li>좌석형태</li>
	        <li>좌석수</li>
	        <li>비행사항</li>
	        <li>관리</li>
	    </ul>
	    <!-- 리스트 반복 구간 -->
	    <app:forEach var="alist" items="${ad}">
	    <ul>
	        <li><input type="checkbox" name="del_ck" v-model="a" @change="onedata()"  value="${alist.getPidx()}"></li>
	        <li>${alist.getAirplane_code()}</li>
	        <li>${alist.getAirname()}</li>
	        <li>${alist.getAircode()}</li>
	        <li>${alist.getAirflight()}</li>
	        <li>${alist.getStart_point()}</li>
	        <li>${alist.getEnd_point()}</li>
	        <li>${alist.getSeat_form()}</li>
	        <li>${alist.getSeat_count()}</li>
	        <li>${alist.getAir_run()}</li>
	        <li>관리</li>
	    </ul>
	    </app:forEach>
	    <app:if test="${fn:length(ad) == 0}">
	    <ul>
	        <li style="width: 100%;">등록된 항공편이 없습니다.</li>
	    </ul>
	    </app:if>
	</div>
	<div class="subpage_view3">
	    <ul class="pageing">
	        <li @click="lleft"><img src="./ico/double_left.svg"></li>
	        <li @click="left"><img src="./ico/left.svg"></li>
	        <%
            	int ea = (int)request.getAttribute("ea");
	        	int pg = (int)Math.ceil(ea / 3f);
	        	int ww = 1;
	        	while(ww <= pg){
	        		request.setAttribute("pgg", ww);
        	%>
	        <li onclick="location.href='./admin_product.do?page=<%= ww%>'" ><%=ww %></li>
	        <% ww++; } %>
	        <li @click="right(<%= pg %>)"><img src="./ico/right.svg"></li>
	        <li @click="rright(<%= pg %>)"><img src="./ico/double_right.svg"></li>
	   	
	    </ul>
	</div>
	<div class="subpage_view4">
	    <input type="button" value="선택항공편 삭제" title="선택상품 삭제" class="p_button" @click="delete_list">
	    <span style="float: right;">
	    <input type="button" value="신규항공편 등록" title="신규상품 등록" class="p_button p_button_color1" onclick="location.href='./admin_product_write.do'">
	    <input type="button" value="항공 코드 등록" title="카테고리 등록" class="p_button p_button_color2" onclick="location.href='./admin_category_write.jsp'">
	    </span>
	</div>
	</section>
	</main>
   <%@ include file="./admin_footer.jsp" %>
   <script>
   		var user_search = "${search}"
   		var user_search_ch = "${search_ch}"

   </script>
</body>
<script src="./js/admin_product.js?v=${today}"></script>
</html>