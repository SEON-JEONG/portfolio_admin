var html = new Vue({
	el:"#master_join",
	data:{
		aid:"",
		apass1:"",
		apass2:"",
		aname:"",
		aemail:"",
		atell1:"",
		atell2:"",
		atell3:"",
		ltell:"",
		adepart:"",
		aposition:"",
		count:0,
		method:"POST",
		action:"./joinok.do",
		enc:"application/x-www-form-urlencoded",
	},
	methods:{
		// 등록 취소 버튼
		cancel_join:function(){
			location.href="./index.jsp"
		},
		
		//중복확인 파트
		idcheck:function(){
			let idck1= /[\{\}\[\]\/?.,;:|\)*~`!^\-+<>@\#$%&\\\=\(\'\"]/g;
			let idck2= /\s/g;
			let idck3= /^[a-z0-9_]{4,20}$/;
			let ko= /[ㄱ-ㅎㅏ-ㅣ가-힣]/;
			if(this.aid == "admin"|| this.aid =="admin1"|| this.aid =="master"|| this.aid =="test" || this.aid =="test1"|| this.aid =="test2"){
				alert("사용불가능한 아이디입니다.");
				f.aid.value="";
			}
			else if(ko.test(this.aid) == true){
				alert("아이디는 영문으로 사용가능 합니다.")
				f.aid.focus();
			}
			else if(idck1.test(this.aid) == true){
				alert("특수문자는 사용할수 없습니다.")
				f.aid.focus();
			}
			else if(idck2.test(this.aid) == true){
				alert("사용불가능한 아이디입니다.")
				f.aid.focus();
			}
			else if(idck3.test(this.aid) == false){
				alert("사용불가능한 아이디입니다.")
				f.aid.focus();
			}	
			else{	
				fetch("./idcheck.do?aid="+this.aid).then(function(aa){
					return aa.text();
				}).then(function(bb){
					html.message(bb);	
				}).catch(function(error){
					console.log("통신오류 발생");
				});
			}
		},
		
		//중복확인 메세지 출력
		message:function(bb){
			if(bb=="1"){	
				alert("해당 아이디는 사용하실 수 없습니다.")
				this.count = 0;
			}
			else{
				alert("사용 가능한 아이디 입니다.")
				this.count++;
				}
			},
		
		

		// 등록완료 버튼
		join_submit:function(){
			let idck = /[a-zA-Z]/;
			let emailck= /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])+@[0-9a-zA-Z]+.[0-9a-zA-Z]/gi;
			let ko= /[ㄱ-ㅎㅏ-ㅣ가-힣]/;
			let passck= /^[a-z0-9_]{4,20}$/;
			let passck1= /[^?a-zA-Z0-9/]/;
			// 전화번호 인풋 3개 히든값에 합치기
			this.ltell = this.atell1 + this.atell2 + this.atell3;
			f.atell.value = this.ltell;
			
		    if(this.aid==""){
				alert("아이디를 입력해 주세요.");
				f.aid.focus();
			}
			else if(this.count == 0){
				alert("아이디 중복체크 해주세요")
				f.aid.focus();
			}
			else if(this.aid.length < 5){
				alert("아이디는 최소 5자 이상 입력하셔야 합니다.");
				f.aid.focus();
			}
			else if(this.apass1==""){
				alert("비밀번호를 입력해 주세요.");
				f.apass1.focus();
			}
			else if(this.apass1.length < 5){
				alert("비밀번호는 최소 5자 이상 입력하셔야 합니다");
				f.apass1.focus();
			}
			else if(this.apass2==""){
				alert("동일한 패스워드를 입력하세요");
				f.apass2.focus();
			}
			else if(this.apass2 != this.apass1){
				alert("비밀번호가 일치하지 않습니다.");
				f.apass2.focus();
			}
			else if(this.aname ==""){
				alert("담당자 이름을 입력해 주세요.");
				f.aname.focus();
			}
			else if(this.aname.length < 2){
				alert("올바른 이름을 입력해 주세요.");
				f.aname.focus();
			}
			else if(this.aemail ==""){
				alert("이메일을 입력해 주세요.");
				f.aemail.focus();
			}
			else if(emailck.test(this.aemail) == false){
				alert("올바른 이메일 형식이 아닙니다.")
				f.email.focus();
			}
			else if(this.atell1 ==""){
				alert("전화번호를 입력해 주세요.");
				f.atell1.focus();
			}
			else if(this.atell2 ==""){
				alert("전화번호를 입력해주세요");
				f.atell2.focus();
			}
			else if(this.atell2.length < 3){
				alert("정확한 전화번호를 입력해주세요");
				f.atell2.focus();
			}
			else if(this.atell3 ==""){
				alert("전화번호를 입력해주세요");
				f.atell3.focus();
			}
			else if(this.atell3.length < 4){
				alert("정확한 전화번호를 입력해주세요");
				f.atell3.focus();
			}
			else if(idck.test(this.ltell) == true || this.ltell.length < 11){
				alert("전화번호를 다시 확인해 주세요.")
			}
			else if(this.adepart.value ==""){
				alert("담당 부서를 선택해주세요");
				f.adepart.focus();
			}
			else if(this.aposition ==""){
				alert("담당 직책을 선택해주세요");
				f.aposition.focus();
			}
			else{
				f.method = this.method;
				f.action = this.action;
				f.ecntype = this.enc;
				f.submit();			
			}
		}
	},
	
	
	
	
});