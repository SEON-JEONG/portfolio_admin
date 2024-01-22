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
    <title>관리자 페이지</title>
	<script src="https://cdn.jsdelivr.net/npm/vue@2"></script>
    <link rel="stylesheet" type="text/css" href="./css/basic.css?v=${today}">
    <link rel="stylesheet" type="text/css" href="./css/mainlogin.css?v=${today}">
    <link rel="stylesheet" type="text/css" href="./css/main.css?v=${today}">
    <link rel="icon" href="./img/logo.png" sizes="128x128">
    <link rel="icon" href="./img/logo.png" sizes="64x64">
    <link rel="icon" href="./img/logo.png" sizes="32x32">
    <link rel="icon" href="./img/logo.png" sizes="16x16">
</head>
<body class="bodycss" >
 	<header class="admin_title">
        <p><img src="./img/logo.png" class="logo_sm"> ADMINISTRATOR</p>
    </header>
	<%@ include file="./login.jsp"%>
	<%@ include file="./admin_footer.jsp"%>
</body>
<script src="./js/index.js?v=${today}"></script>
</html>