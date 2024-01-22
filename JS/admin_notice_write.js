var html = new Vue({
	el:"#page_main",
	data:{
		data1:"",	//제목
	
		method:"POST",
		enctype:"multipart/form-data",	//첨부파일은 application으로 보내면 안된다
		action:"./admin_notice_writeok.do",
	},
	methods:{

		admin_notice:function(){
		location.href="./admin_notice.do"
		},
		
	
		notice_write:function(){
			
			//CK에디터 값 가져오기
			var ck = CKEDITOR.instances.boardtext.getData();
			console.log(ck);	// 텍스트창에 내가쓴값
			
			
			if(this.data1 == ""){
				alert("공지사항 제목을 입력하세요");
			}
			
			
			else if(ck == ""){
				alert("내용을 입력 하셔야 합니다");
			}
			else{
				f.method = this.method;
				f.action = this.action;
				f.enctype = this.enctype;
				f.submit();
			}
		}
	}
});