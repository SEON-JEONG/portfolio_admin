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
    <link rel="stylesheet" type="text/css" href="./css/basic.css">
    <link rel="stylesheet" type="text/css" href="./css/login.css?v=1">
    <link rel="icon" href="./img/logo.png" sizes="128x128">
    <link rel="icon" href="./img/logo.png" sizes="64x64">
    <link rel="icon" href="./img/logo.png" sizes="32x32">
    <link rel="icon" href="./img/logo.png" sizes="16x16">
    <script src="https://cdn.jsdelivr.net/npm/vue@2"></script>
</head>
<body>

    <header class="admin_title_add">
        <p><img src="./img/logo.png" class="logo_sm"> ADMINISTRATOR ADD</p>
    </header>
    
   <%@ include file="./join.jsp"%>    
   <%@ include file="./admin_footer.jsp" %>
     
</body>
<script src="./js/add_master.js?v=${today}"></script>
</html>