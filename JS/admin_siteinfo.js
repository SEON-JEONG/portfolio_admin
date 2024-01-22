var html = new Vue({
	el:"#admin_siteinfo",
	data:{
	},
	methods:{
	save_info:function(){
		let idck = /[a-zA-Z]/;
		let emailck= /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])+@[0-9a-zA-Z]+.[0-9a-zA-Z]/gi;
		let emailck2 = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])+@[0-9a-zA-Z]+.[0-9a-zA-Z]/gi;
		let banknck = /[0-9][-]/gi;
		if(f.s_title.value== ""){
			alert("홈페이지 제목을 입력해주세요.");
			f.s_title.focus();
			}
			else if(f.s_email.value == ""){
				alert("관리자 메일 주소를 입력해주세요");
				f.s_email.focus();
			}
			else if(emailck.test(f.s_email.value) == false){
				alert("올바른 이메일 주소를 입력하여 주세요.");
				f.s_email.focus();
			}
			else if(f.s_reserves.value == ""){
					alert("회원가입시 적립금을 입력하세요.");
			}
			else if(f.s_level.value == ""){
				alert("회원가입 권한레벨을 입력하세요.");
			}
			else if(f.s_corpnm.value == ""){
				alert("회사명 입력하세요.");
			}
			else if(f.s_corpno.value == ""){
				alert("사업자등록번호를 입력하세요.");
			}
			else if(f.s_ceo.value == ""){
				alert("대표자명을 입력하세요.");
			}
			else if(f.s_tell.value == ""){
				alert("대표자전화번호를 입력하세요.");
			}
			else if(f.s_number1.value == ""){
				alert("통신판매업 신고번호를 입력하세요.");
			}
			else if(f.s_port.value == ""){
				alert("사업장 우편번호를 입력하세요.");
			}
			else if(f.s_add.value == ""){
				alert("사업장 주소를 입력하세요.");
			}
			else if(f.s_add.value == ""){
				alert("정보관리 책임자명을 입력하세요.");
			}
			else if(f.s_admail.value == ""){
				alert("정보관리 책임자 이메일을 입력하세요.");
			}
			else if(f.s_banknm.value == ""){
				alert("무통장 은행을 입력하세요.");
			}
			else if(f.s_bankno.value == ""){
				alert("은행 계좌번호를 입력하세요.");
			}
			else if(f.pay_min_point.value == ""){
				alert("결제 최소포인트를 입력하세요.");
			}
			else if(f.pay_max_point.value == ""){
				alert("결제 최대포인트를 입력하세요.");
			}
			else if(isNaN(f.s_reserves.value) == true){
				alert("회원가입시 적립금은 숫자만 입력해 주세요.");
				f.s_reserves.focus();
			}
			else if(isNaN(f.s_tell.value) == false){
				alert("대표자 전화번호는 숫자만 입력해 주세요.");
				f.s_tell.focus();
			}
			else if(isNaN(f.s_port.value) == true){
				alert("우편번호는 숫자만 입력해 주세요.");
			}
			else if(isNaN(f.pay_min_point.value) == true){
				alert("결제 최소 포인트는 숫자만 입력하세요.");
				f.pay_min_point.focus();
			}
			else if(isNaN(f.pay_max_point.value) == true){
				alert("결제 최대 포인트는 숫자만 입력하세요.");
				f.pay_max_point.focus();
			}
		else{
			confirm("해당 내용을 저장 하시겠습니까?");
			f.action = "./admin_siteinfo_save.do";
			f.method = "post";
			f.enctype = "application/x-www-form-urlencoded";
			f.submit();
		}
		
	
		},		
	},
});