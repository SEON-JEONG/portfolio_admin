var html = new Vue({
	el:"#faq_list",
	data:{
	search:search,
	},
	methods:{
		
		
		modify_go:function(a){
			location.href="./admin_faq_modify.do?fidx="+a;
		},
		
		
		//삭제 부분
		delete_faq:function(a){
			if(confirm('해당 FAQ를 삭제하시겠습니까?\n(삭제시 해당내용은 복구 되지 않습니다.)'))
			location.href="./faq_deleteok.do?fidx="+a;
		},
		
		search_faq:function(){
			f.action="./admin_faq.do"
			f.method="post"
			f.enctype="application/x-www-form-urlencoded"
		},
		
		
	},
});