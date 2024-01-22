window.onload = function(){
	var ck = CKEDITOR.replace("boardtext",{
		width:800,
		height:280,
	});
}

var html = new Vue({
	el:"#admin_modify",
	data:{
		method:"POST",
		enctype:"multipart/form-data",	//첨부파일은 application으로 보내면 안된다
		action:"./admin_notice_modifyok.do",
	},
	methods:{		
		

		
		list_page:function(){
			location.href="./admin_notice.do"
			},
		

		
		modify_page:function(){
			if(confirm('해당 내용을 수정 하시겠습니까?')){
				var ck = CKEDITOR.instances.boardtext.getData();
				f.method = this.method;
				f.action = this.action;
				f.enctype = this.enctype;
				f.submit();
				}
		},
		
	}
})


















/*
//fidx값 받기
var urls = document.location.search;
var fdata = urls.split("?aidx=");


window.onload = function(){
	var ck = CKEDITOR.replace("boardtext",{
		width:800,
		height:280,
	});
}

var html = new Vue({
	el:"#admin_modity",
	data:{
		method:"POST",
		enctype:"multipart/form-data",	//첨부파일은 application으로 보내면 안된다
		action:"./notice_modifyok.do",
		htmltag:filenm,
		data1:subject,	//제목
		data2:writer,
		fidx:fdata[1],
	},
	methods:{
		
		// 수정버튼 눌렀을때!
		modify_page:function(){
			location.href='./notice_modify.do'+this.aidx;	// this.니까 위에 데이터값
			
		},
		
		
		list_page:function(){
			location.href="./notice_list.do"
			},
		
		
		
			// 첨부파일 삭제 버튼
		file_delete:function(){
			if(confirm("첨부파일을 삭제 하시겠습니까?\n 단 삭제시 파일은 복구되지 않습니다.")){
				fetch("./file_delete.do?aidx="+this.aidx).then(function(response){
					return response.text();
				}).then(function(data){
					if(data == "ok"){
						this.htmltag = "";
						location.reload(true);
					}
				}).catch(function(error){
					console.log("통신오류!")
				});
				
				
				
				
			}
		},
		
		notice_write:function(){
			
			//CK에디터 값 가져오기
			var ck = CKEDITOR.instances.boardtext.getData();
			console.log(ck);	// 텍스트창에 내가쓴값
			
			
			if(this.data1 == ""){
				alert("수정할 제목을 입력하세요");
			}
			else if(ck == ""){
				alert("수정할 내용을 입력 하셔야 합니다");
			}
			else{
				f.method = this.method;
				f.action = this.action;
				f.enctype = this.enctype;
				f.submit();
			}
		}
	},
		
});
*/