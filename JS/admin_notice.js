

var html = new Vue({
	el : "#sec",
	data : {
		all:false,
		a:[],
		ck:document.getElementsByName("del_ck"),
		result:[],
		
		test:""
	},
	methods:{
		go_view:function(data,a){
			location.href='./admin_notice_view.do?aidx='+data+"&num="+a;
		},
		alldata:function(){
			if(this.all == true) {
				
				for (var i = 0; i < this.ck.length; i++) {
		            this.ck[i].checked = true;
					this.a.push(this.ck[i].value)
		        }
			}
			else if(this.all == false) {
				this.a = []
			}
			
		},
		onedata:function(){
			//console.log(list["target"]["checked"])
			if(this.all == true){
				this.all = false
			}
			else if(this.a.length == this.ck.length){
				this.all = true
			}
		},
		delete_list:function(){
			confirm("해당 게시물을 삭제하시겠습니까? \n(삭제시 해당 데이터는 복구되지 않습니다)")
			location.href="./admin_notice_deleteAll.do?aidx="+this.a;
		
		},
		go:function(){
			//★★★★★ 지금 이거 숫자로 안들어간다
			this.test =	this.test+1
			console.log(this.test)
			//location.href='./admin_notice.do?page='+this.test
		}
	},
	computed:{
		testd:function(){
			var bbb = window.location.search;
			this.test = bbb.replace("?page=","");
			console.log(this.test)
		}
		
	}
})


/*
var html = new Vue({
	el:"#admin_notice_list",
	data:{
		all:false,
		a:[],
		b:[]
	
	},
	methods:{
		
		selectall:function(aidx,e,no){
			console.log(no);
			if(aidx=="all"){
				if(this.all == true){
					this.a = true;
				}
				else if(this.all == false){
					this.a = false;
				}
			}
			else{
				this.a = false;	
				if(e.target.checked==true){	
					this.b.push(aidx);	
				}
				else {
					this.b.splice(no,1);	
					this.a = false;	
				}
				
			}
			/*
			if(this.all == true){
				this.a = true
			}
			else if(this.all == false){
				this.a=[];	// a 배열을 초기화
			
			}
			
		},
		
		
		
		
		admin_write:function(){
			location.href="./notice_write.do"
		},
	
		freeboard_view:function(aidx,a){
			location.href='./notice_view.do?aidx='+aidx+"&num="+a;
		},
		
		freeboard_del:function(aidx){
			//console.log(this.a);
			if(aidx == null ){
				alert("삭제할 내용을 체크해주세요.")
			}
			else if(confirm("해당 게시물을 삭제하시겠습니까?\n(삭제시 해당내용은 복구 되지 않습니다.)")){
				location.href='./notice_delete.do?aidx='+this.b;
			}
		},
	
	}
});

*/