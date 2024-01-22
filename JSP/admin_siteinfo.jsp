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
        <title>사이트 환경설정</title>
        <link rel="stylesheet" type="text/css" href="./css/basic.css?v=${today}">
        <link rel="stylesheet" type="text/css" href="./css/login.css?v=${today}">
        <link rel="stylesheet" type="text/css" href="./css/main.css?v=${today}">
        <link rel="stylesheet" type="text/css" href="./css/subpage.css?v=${today}">
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
	<form id="f">
	<main class="maincss" id="admin_siteinfo">
	<section>
	    <p>홈페이지 가입환경 설정</p>
	<div class="subpage_view">
	<ul class="info_form">
	    <li>홈페이지 제목</li>
	    <li>
	        <input type="text" name="s_title" value="${siteinfo.getS_title()}" class="in_form1"> 
	    </li>
	</ul>    
	<ul class="info_form">
	    <li>관리자 메일 주소</li>
	    <li>
	        <input type="text" name="s_email" value="${siteinfo.getS_email()}"  class="in_form2"> ※ 관리자가 보내고 받는 용도로 사용하는 메일 주소를 입력합니다.(회원가입,인증메일,회원메일발송 등에서 사용)
	    </li>
	</ul> 
	<ul class="info_form">
	    <li>포인트 사용 유/무</li>
	    <li class="checkcss">
	   		<app:if test="${siteinfo.s_point == 'Y'}">
	        <em><label><input type="radio" name="s_point" value="Y"  class="ckclass"checked>포인트 사용</label></em> 
	        <em><label><input type="radio" name="s_point" value="N"  class="ckclass" >포인트 미사용</label></em>
	        </app:if>
	
	        <app:if test="${siteinfo.s_point == 'N'}">
	        <em><label><input type="radio" name="s_point" value="Y"  class="ckclass">포인트 사용</label></em> 
	        <em><label><input type="radio" name="s_point" value="N"  class="ckclass" checked>포인트 미사용</label></em>
	        </app:if>
	
	        
	    </li>
	</ul>
	<ul class="info_form2" style="border-bottom:1px solid rgb(81, 61, 61);">
	    <li>회원가입시 적립금</li>
	    <li>
	        <input type="text" name="s_reserves" value="${siteinfo.getS_reserves()}"  class="in_form3" maxlength="5"> 점
	    </li>
	    <li>회원가입시 권한레벨</li>
	    <li>
	        <input type="text" name="s_level" value="${siteinfo.getS_level()}"  class="in_form3" maxlength="1" value="1"> 레벨
	    </li>
	</ul> 
	</div>
	<p>홈페이지 기본환경 설정</p>
	<div class="subpage_view">
	<ul class="info_form2">
	    <li>회사명</li>
	    <li>
	        <input type="text" name="s_corpnm" value="${siteinfo.getS_corpnm()}"  class="in_form0"> 
	    </li>
	    <li>사업자등록번호</li>
	    <li>
	        <input type="text" name="s_corpno" value="${siteinfo.getS_corpno()}"  class="in_form0"> 
	    </li>
	</ul> 
	<ul class="info_form2">
	    <li>대표자명</li>
	    <li>
	        <input type="text" name="s_ceo" value="${siteinfo.getS_ceo()}"  class="in_form0"> 
	    </li>
	    <li>대표전화번호</li>
	    <li>
	        <input type="text" name="s_tell" value="${siteinfo.getS_tell()}"  class="in_form0"> 
	    </li>
	</ul>
	<ul class="info_form2">
	    <li>통신판매업 신고번호</li>
	    <li>
	        <input type="text" name="s_number1" value="${siteinfo.getS_number1()}"  class="in_form0"> 
	    </li>
	    <li>부가통신 사업자번호</li>
	    <li>
	        <input type="text" name="s_number2" value="${siteinfo.getS_number2()}"  class="in_form0"> 
	    </li>
	</ul>
	<ul class="info_form2">
	    <li>사업장 우편번호</li>
	    <li>
	        <input type="text" name="s_port" value="${siteinfo.getS_port()}"  class="in_form0"> 
	    </li>
	    <li>사업장 주소</li>
	    <li>
	        <input type="text" name="s_add" value="${siteinfo.getS_add()}"  class="in_form2"> 
	    </li>
	</ul>
	<ul class="info_form2" style="border-bottom:1px solid rgb(81, 61, 61);">
	    <li>정보관리책임자명</li>
	    <li>
	        <input type="text" name="s_admin" value="${siteinfo.getS_admin()}"  class="in_form0"> 
	    </li>
	    <li>정보책임자 E-mail</li>
	    <li>
	        <input type="text" name="s_admail" value="${siteinfo.getS_admail()}"  class="in_form1"> 
	    </li>
	</ul>
	</div>
	<p>결제 기본환경 설정</p>
	<div class="subpage_view">  
	<ul class="info_form2">
	    <li>무통장 은행</li>
	    <li>
	        <input type="text" name="s_banknm" value="${siteinfo.getS_banknm()}"  class="in_form0"> 
	    </li>
	    <li>은행계좌번호</li>
	    <li>
	        <input type="text" name="s_bankno" value="${siteinfo.getS_bankno()}"  class="in_form1"> 
	    </li>
	</ul>
	<ul class="info_form">
	    <li>신용카드 결제 사용</li>
	    <li class="checkcss">
	   		<app:if test="${siteinfo.getPay_card() == 'Y'}">
	        <em><label><input type="radio" name="pay_card" value="Y"  class="ckclass"checked> 사용</label></em> 
	        <em><label><input type="radio" name="pay_card" value="N"  class="ckclass" > 미사용</label></em> ※ 해당 PG사가 있을 경우 사용으로 변경합니다.
	        </app:if>
	
	        <app:if test="${siteinfo.getPay_card() == 'N'}">
	        <em><label><input type="radio" name="pay_card" value="Y"  class="ckclass"> 사용</label></em> 
	        <em><label><input type="radio" name="pay_card" value="N"  class="ckclass" checked> 미사용</label></em> ※ 해당 PG사가 있을 경우 사용으로 변경합니다.
	        </app:if>
	
	    </li>
	</ul>
	<ul class="info_form">
	    <li>휴대폰 결제 사용</li>
	    <li class="checkcss">
	   		<app:if test="${siteinfo.getPay_hp() == 'Y'}">
	        <em><label><input type="radio" name="pay_hp" value="Y"  class="ckclass"checked> 사용</label></em> 
	        <em><label><input type="radio" name="pay_hp" value="N"  class="ckclass" > 미사용</label></em> ※ 주문시 휴대폰 결제를 가능하게 할 것인지를 설정합니다.
	        </app:if>
	
	        <app:if test="${siteinfo.getPay_hp() == 'N'}">
	        <em><label><input type="radio" name="pay_hp" value="Y"  class="ckclass"> 사용</label></em> 
	        <em><label><input type="radio" name="pay_hp" value="N"  class="ckclass" checked> 미사용</label></em> ※ 주문시 휴대폰 결제를 가능하게 할 것인지를 설정합니다.
	        </app:if>
	
	    </li>
	</ul>
	<ul class="info_form">
	    <li>도서상품권 결제사용</li>
	    <li class="checkcss">
	    	<app:if test="${siteinfo.getPay_book() == 'Y'}">
	        <em><label><input type="radio" name="pay_book" value="Y"  class="ckclass"checked> 사용</label></em> 
	        <em><label><input type="radio" name="pay_book" value="N"  class="ckclass" > 미사용</label></em> ※ 도서상품권 결제만 적용이 되며, 그 외에 상품권은 결제 되지 않습니다.
	    	</app:if>
	
	    	<app:if test="${siteinfo.getPay_book() == 'N'}">
	        <em><label><input type="radio" name="pay_book" value="Y"  class="ckclass"> 사용</label></em> 
	        <em><label><input type="radio" name="pay_book" value="N"  class="ckclass" checked> 미사용</label></em> ※ 도서상품권 결제만 적용이 되며, 그 외에 상품권은 결제 되지 않습니다.
	    	</app:if>
	
	    </li>
	</ul>
	<ul class="info_form2">
	    <li>결제 최소 포인트</li>
	    <li>
	        <input type="text" name="pay_min_point" value="${siteinfo.getPay_min_point()}"  class="in_form0" maxlength="5"> 점
	    </li>
	    <li>결제 최대 포인트</li>
	    <li>
	        <input type="text" name="pay_max_point" value="${siteinfo.getPay_max_point()}"  class="in_form0" maxlength="5"> 점
	    </li>
	</ul>
	<ul class="info_form" style="border-bottom:1px solid rgb(81, 61, 61);">
	    <li>현금 영수증 발급사용</li>
	    <li class="checkcss">
	    	<app:if test="${siteinfo.getPay_paper() == 'Y'}">
	        <em><label><input type="radio" name="pay_paper" value="Y"  class="ckclass"checked> 사용</label></em> 
	        <em><label><input type="radio" name="pay_paper" value="N"  class="ckclass" > 미사용</label></em> ※ 현금영수증 관련 사항은 PG사 가입이 되었을 경우 사용가능 합니다.
	    	</app:if>
	
	    	<app:if test="${siteinfo.getPay_paper() == 'N'}">
	        <em><label><input type="radio" name="pay_paper" value="Y"  class="ckclass"> 사용</label></em> 
	        <em><label><input type="radio" name="pay_paper" value="N"  class="ckclass" checked> 미사용</label></em> ※ 현금영수증 관련 사항은 PG사 가입이 되었을 경우 사용가능 합니다.
	    	</app:if>
	
	    </li>
	</ul>
	</div>
	<div class="btn_div">
	    <button type="button" class="sub_btn1"  title="설정 저장" @click="save_info">설정 저장</button>
	    <button type="button" class="sub_btn2"  title="저장 취소" onclick="window.location.reload()">저장 취소</button>
	</div>
	</section>
	</main>
	</form>
	<%@ include file="./admin_footer.jsp"%>
</body>
<script src="./js/admin_siteinfo.js?v=${today}"></script>
</html>