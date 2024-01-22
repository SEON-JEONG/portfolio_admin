

var html = new Vue({
	el : "#vue",
	data : {
		user_search : user_search,
		user_search_ch : user_search_ch,
		ch_del:[]
	},
	methods:{
		del_reserve:function(){
			if(confirm('해당 고객의 예매를 취소 시키겠습니까?')){
				location.href="./admin_reserve_delete.do?uidx="+this.ch_del;
			}
		},
		goSearch:function(){
			
			f.action="./admin_ticketing.do"
			f.method="post"
			f.enctype="application/x-www-form-urlencoded"
		}
	}
})