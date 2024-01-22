
var htmll = new Vue({
	el : "#vue",
	data: {
		user_search : user_search,
		user_search_ch : user_search_ch,
		all:false,
		a:[],
		ck:document.getElementsByName("del_ck"),
		result:[]
	},
	methods:{
		gosearch:function(){

			f.method="post"
			f.enctype="application/x-www-form-urlencoded"
			f.action="./admin_category.do"
		
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
			if(this.all == true){
				this.all = false
			}
			else if(this.a.length == this.ck.length){
				this.all = true
			}
		},
		delete_list:function(){
			if(confirm('해당 카테고리들을 삭제 하시겠습니까?')){
				location.href="./admin_category_deleteAll.do?aidx="+this.a;
			}
		}
		
	}
	
})