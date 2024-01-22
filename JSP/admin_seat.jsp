<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" uri="http://java.sun.com/jsp/jstl/core" %> 
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
    <link rel="stylesheet" type="text/css" href="./css/seat.css?v=5">
    <link rel="icon" href="./img/logo.png" sizes="128x128">
    <link rel="icon" href="./img/logo.png" sizes="64x64">
    <link rel="icon" href="./img/logo.png" sizes="32x32">
    <link rel="icon" href="./img/logo.png" sizes="16x16">
    <script src="./js/jquery.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/vue@2"></script>
</head>
<body>

 <app:if test="${dataList != null}">
 </app:if>
	<%@ include file="./admin_header.jsp" %>
    <%@ include file="./admin_menu.jsp" %>
	<main class="maincss" id="vue">
	<section>
	    <p>좌석 및 예약설정</p>
	<div class="subpage_view">
	    <span><b style="color:darkred;" @click="nonereserve">미설정 항공코드 ${nonelist.size()}건</b> | <span @click="ysereserve" style="display:inline;">설정 완료 항공코드 ${yeslist.size()}건</span></span>
	    <span>
	        <form id="f">
	      		<input type="hidden" name="air_resuerve_ck">
	        <select class="p_select1" name="seat_search_ch" v-model="ch">
	            <option value="출발지" selected>출발지</option>
	            <option value="도착지">도착지</option>
	        </select>
	        <input type="text" name="seat_search" class="p_input1" placeholder="검색할 단어를 입력하세요" v-model="sch">
	        <input type="submit" value="검색" title="항공검색" class="p_submit" @click="search">
	        </form>
	    </span>
	</div>
	<form id="f2">
	<input type="hidden" name="pidx" >
	<input type="hidden" name="rstart_date" >
	<input type="hidden" name="rend_date" >
	<input type="hidden" name="rprice" >
	<div class="subseat_view2">
	    <ul>
	        <li>공항코드</li>
	        <li>항공사명</li>
	        <li>항공코드</li>
	        <li>항공편명</li>
	        <li>출발지</li>
	        <li>도착지</li>
	        <li>좌석형태</li>
	        <li>좌석수</li>
	        <li>예약 시작일</li>
	        <li>예약 종료일</li>
	        <li>1인 금액</li>
	    </ul>
	   
	    <app:if test="${dataList != null}">
		    <app:forEach var="alist" items="${dataList}">
		    <input type="hidden" name="dx" value="${alist.getPidx()}">
		    
		    <ul>
		        <li>${alist.getAirplane_code()}</li>
		        <li style="text-align: left; text-indent: 3px;">${alist.getAirname()}</li>
		        <li style="text-align: left; text-indent: 3px;">${alist.getAircode()}</li>
		        <li style="text-align: left; text-indent: 3px;">${alist.getAirflight()}</li>
		        <li>${alist.getStart_point()}</li>
		        <li>${alist.getEnd_point()}</li>
		        <li>${alist.getSeat_form()}</li>
		        <li>${alist.getSeat_count()}</li>
		        <app:if test="${dataList.get(0).getRstart_date() == null}">
		        	<li><input type="date" name="sd" class="days" value="${alist.getRstart_date()}"></li>
		       		<li><input type="date" name="ed" class="days" value="${alist.getRend_date()}"></li>
		        </app:if>
		        <app:if test="${dataList.get(0).getRstart_date() != null}">
		        	<li><input type="date" name="rstart_date" class="days" value="${alist.getRstart_date()}" readonly></li>
		       		<li><input type="date" name="rend_date" class="days" value="${alist.getRend_date()}" readonly></li>
		        </app:if>
		        <app:if test="${dataList.get(0).getRprice() == null}">
		        	<li><input type="text" name="pri" class="moneys" maxlength="8" value="${alist.getRprice()}"></li>
		    	</app:if>
		    	<app:if test="${dataList.get(0).getRprice() != null}">
		       		<li><input type="text" name="rprice" class="moneys" maxlength="8" value="${alist.getRprice()}" readonly></li>
		    	</app:if>
		    </ul>
		    </app:forEach>
	    </app:if>
	   
	    <app:if test="${dataList == null}">
	    <ul>
	        <li style="width: 100%;">검색된 항공편이 없습니다.</li>
	    </ul>
	    </app:if>
	</div>
	</form>
	<div class="subpage_view4">
	    <app:if test="${dataList.get(0).getRprice() == null}">
	    <span style="text-align: right;">
	    <input type="button" value="등록완료" title="등록완료" class="p_button p_button_color2" @click="goinsert">
	    </span>
	    </app:if>
	</div>
	</section>
	</main>
	<%@ include file="./admin_footer.jsp" %>
</body>
<script>
	var seat_search = "${seat_search}"
	var seat_search_ch = "${seat_search_ch}"
</script>
<script src="./js/admin_seat.js?v=${today}"></script>
</html>