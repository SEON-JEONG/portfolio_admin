    window.onload = function(){
    	var ck = CKEDITOR.replace("boardtext",{
    		width:920,
    		height:390,
    	});
    }

var html = new Vue({
	el:"#faq_write",
	data:{
	},
	methods:{
	write_ok:function(){
		var ck = CKEDITOR.instances.boardtext.getData();
			if(f.fsubject.value == "" || ck == ""){
				alert("질문 제목을 입력해 주세요.")
		}
		else{
				f.action= "./faq_writeok.do"
				f.method="POST"
				f.enctype="application/x-www-form-urlencoded"
				f.submit();
		}
	},
		
	},
});