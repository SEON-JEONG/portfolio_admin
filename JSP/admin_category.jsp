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
    <link rel="stylesheet" type="text/css" href="./css/login.css?v=2">
    <link rel="stylesheet" type="text/css" href="./css/main.css">
    <link rel="stylesheet" type="text/css" href="./css/category.css?v=3">
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
<section style="height: auto;">
    <p>항공 코드 관리 페이지</p>
    <div class="subpage_view">
        <span>등록된 항공 코드 ${ea.size()}건</span>
        <span>
            <form id="f" >
            <select class="p_select1" name="search_ch" v-model="user_search_ch">
                <option selected>항공사명</option>
                <option>항공코드</option>
            </select>
            <input type="text" name="search" v-model="user_search" class="p_input1" placeholder="검색어를 입력해 주세요">
            <input type="submit" value="검색" title="검색" class="p_submit" @click="gosearch">
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
            <li>사용 유/무</li>
            <li>관리</li>
        </ul>
        <app:forEach  var="alist" items= "${datalist}" >
	        <ul>
	            <li><input type="checkbox" name="del_ck" v-model="a" @change="onedata()" value="${alist.getAidx()}"></li>
	            <li style="text-align: left; text-indent: 5px;">${alist.getAirplane_code()}</li>
	            <li style="text-align: left; text-indent: 5px;">${alist.getAirname()}</li>
	            <li style="text-align: left; text-indent: 5px;">${alist.getAircode()}</li>
	            <li style="text-align: left; text-indent: 5px;">${alist.getAirflight()}</li>
	            <li>${alist.getAiruse()}</li>
	            <li onclick="location.href='./admin_category_modify.do?aidx=${alist.getAidx()}'" style="cursor:pointer;">[수정]</li>
	        </ul>
        </app:forEach>
        <app:if test="${datalist.size() == 0}">
	        <ul>
	            <li style="width: 100%;">등록된 항공코드가 없습니다.</li>
	        </ul>
        </app:if>
    </div>
    <div class="subpage_view3">
        <ul class="pageing">
            <li><img src="./ico/double_left.svg"></li>
            <li><img src="./ico/left.svg"></li>
            <li>1</li>
            <li><img src="./ico/right.svg"></li>
            <li><img src="./ico/double_right.svg"></li>
        </ul>
    </div>
    <div class="subpage_view4">
        <input type="button" value="공항코드 삭제" title="공항코드 삭제" class="p_button" @click="delete_list">
        <span style="float: right;">
        <input type="button" value="항공편 리스트" title="항공편 리스트" class="p_button p_button_color1" onclick="location.href='./admin_product.do'">
        <input type="button" value="항공코드 등록" title="항공코드 등록" class="p_button p_button_color2" onclick="location.href='./admin_category_write.jsp'">
        </span>
    </div>
</section>
</main>
   <%@ include file="./admin_footer.jsp" %>
</body>
<script>
		var user_search = "${search}"
		var user_search_ch = "${search_ch}"
</script>
<script src="./js/admin_category.js?v=${today}"></script>
</html>