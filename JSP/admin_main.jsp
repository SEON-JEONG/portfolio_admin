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
    <title>관리자 등록 페이지</title>
    <script src="https://cdn.jsdelivr.net/npm/vue@2"></script>
    <link rel="stylesheet" type="text/css" href="./css/basic.css?v=${today}">
    <link rel="stylesheet" type="text/css" href="./css/login.css?v=${today}">
    <link rel="stylesheet" type="text/css" href="./css/main.css?v=${today}">
    <link rel="icon" href="./img/logo.png" sizes="128x128">
    <link rel="icon" href="./img/logo.png" sizes="64x64">
    <link rel="icon" href="./img/logo.png" sizes="32x32">
    <link rel="icon" href="./img/logo.png" sizes="16x16">
    <script src="./js/jquery.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/vue@2"></script>
</head>

<body>
	<%@ include file="./admin_header.jsp"%>
	<%@ include file="./admin_menu.jsp"%>
	<%@ include file="./admin_list.jsp"%>
	<%@ include file="./admin_footer.jsp"%>
</body>
<script src="./js/admin_main.js?v=${today}"></script>
</html>