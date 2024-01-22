<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	Date today = new Date();
	request.setAttribute("today",today);
	//List<String> dl = (List<String>)request.getAttribute("dl");
	//out.print(dl);
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
<div id="vue">
<form id="f">
<main class="maincss">
<section>
    <p>항공편 및 번호 등록 페이지</p>
    <div class="product_insert">
        <ul>
            <li>공항코드</li>
            <li>
                <select class="product_input1_1" name="airplane_code" @change="planecode_ch">
                    <option value="x">공항코드를 선택하세요</option>
	            	<app:forEach items="${dataList}" varStatus="i">
          		<app:if test="${dataList.get(i.index).get('airuse') == 'Y'}">
				<option value="${dataList.get(i.index).get('airplane_code')}">
					${dataList.get(i.index).get("airplane_code")}
				</option>
              	</app:if>
              </app:forEach>
             </select>
         </li>
         
     </ul>
     <ul>
         <li>항공사명</li>
         <li>
             <select class="product_input1_1" name="airname" @change="name_ch">
                 <option value="x">항공사명를 선택하세요</option>
                 <option v-for="n in name_arr" :value="n">{{n}}</option>
             </select>
             
         </li>
     </ul>
     <ul>
         <li>항공코드</li>
         <li>
              <select class="product_input1_1" name="aircode" @change="code_ch">
                 <option value="x">항공코드를 선택하세요</option>
                 <option v-for="n in aircode_arr" :value="n">{{n}}</option>
             </select>
             <input type="button" value="항공 코드 등록" title="항공코드 등록" class="product_btn" onclick="location.href='./admin_category_write.jsp'"> <span class="help_text">※ 항공코드는 절대 중복되지 않도록 합니다.</span>
         </li>
     </ul>
     <ul>
         <li>항공편명</li>
         <li>
             <input type="text" name="airflight" class="product_input2" maxlength="100" readonly>
         </li>
     </ul>
     <ul>
         <li>출발지</li>
         <li>
             <input type="text" name="start_point" class="product_input3"><span class="help_text">※ 출발지와 도착지가 같을 수 없습니다.</span>
         </li>
     </ul>
     <ul>
         <li>도착지</li>
         <li>
             <input type="text" name="end_point" class="product_input3"><span class="help_text">※ 출발지와 도착지가 같을 수 없습니다.</span>
         </li>
     </ul>
     <ul>
         <li>좌석형태</li>
         <li>
             <input type="text" name="seat_form" class="product_input3"> <span class="help_text">※ 예시 일반석, 프레스티지석, 일등석, 비지니스석으로 나누어집니다.</span>
         </li>
     </ul>
     <ul>
         <li>좌석수</li>
         <li>
             <input type="text" name="seat_count" class="product_input3" maxlength="4" value="0">EA <span class="help_text">※ 숫자만 입력하세요. 재고가 0일 경우 soldout이 됩니다.</span>
         </li>
     </ul>
     <ul>
         <li>비행사항</li>
         <li>
             <label class="product_label">
             <input type="radio" name="air_run" value="Y" style="vertical-align:-1px;" checked> 운행가능
             </label>
             <label class="product_label">
             <input type="radio" name="air_run" value="N" style="vertical-align:-1px;"> 운행종료
             </label> <span class="help_text">※ 운행종료로 선택할 경우 고객은 해당 항공을 이용하지 못합니다.</span>
         </li>
     </ul>
 </div>
 <div class="subpage_view4" style="text-align:center;">
     <input type="button" value="항공편 리스트" title="항공편 리스트" class="p_button p_button_color1" style="margin-right: 5px;" onclick="location.href='./admin_product.do'">
        <input type="button" value="신규항공편 등록" title="신규항공편 등록" class="p_button p_button_color2" @click="writeok">
        </span>
    </div>
</section>
</main>
</form>
</div>
   <%@ include file="./admin_footer.jsp" %>
<script src="./js/admin_product_write.js?v=${today}"></script>
</body>
</html>