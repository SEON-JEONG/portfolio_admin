<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	
	<main class="maincss">
	<section id="main">
	{{json}}
	    <p>신규등록 관리자</p>
	    <ol class="new_admin_title">
	        <li>NO</li>
	        <li>관리자명</li>
	        <li>아이디</li>
	        <li>전화번호</li>
	        <li>이메일</li>
	        <li>담당부서</li>
	        <li>담당직책</li>
	        <li>가입일자</li>
	        <li>승인여부</li>
	    </ol>
	    <form id="f">
	    <input type="hidden" name="aidx">
	    <input type="hidden" name="aid">
	    <input type="hidden" name="approve">
	    <ol class="new_admin_none" v-if="count == 0">
	        <li>신규 등록된 관리자가 없습니다.</li>
	    </ol>
	    <ol class="new_admin_lists" v-else v-for="aa in arr['admin']">
	        <li>{{aa.aidx}}</li>
	        <li>{{aa.aname}}</li>
	        <li>{{aa.aid}}</li>
	        <li>{{aa.atell}}</li>
	        <li>{{aa.aemail}}</li>
	        <li>{{aa.adepart}}</li>
	        <li>{{aa.aposition}}</li>
	        <li>{{aa.adate.substr(0,10)}}</li>
	        <li>
	            <input type="button" value="승인" class="new_addbtn1" title="승인" @click="agree(aa.aidx)">           
	            <input type="button" value="미승인" class="new_addbtn2" title="미승인" @click="disagree(aa.aidx)">
	        </li>
	    </ol>
	    </form>
	</section>
	<section></section>
	<section></section>
	</main>