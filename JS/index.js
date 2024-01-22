var html = new Vue({
	el:"#admin_index",
	data:{
		method:"POST",
		url:"./loginok.do",
		enc:"application/x-www-form-urlencoded",
		aid:"",
		apass:"",
	},
	methods:{
		
		
		new_join:function(){
		location.href="./admin_join.jsp"	
		},
	
	
		login_ok:function(){
			
			if(this.aid==""){
				alert("아이디를 입력하세요");
				f.aid.focus();
				console.log(this.aid);
			}
			else if(this.aid.length < 5){
				alert("아이디는 최소 5자 이상 입력하셔야 합니다");
				f.aid.focus();
			}
			else if(this.apass==""){
				alert("패스워드를 입력하세요");
				f.apass.focus();
			}
			else if(this.apass.length < 5){
				alert("비밀번호는 최소 5자 이상 입력하셔야 합니다");
				f.apass.focus();
			}
			else{
				f.method = this.method;
				f.action = this.url;
				f.ecntype = this.enc;
				f.submit();			
			}
		}
	},
	

	
	
});