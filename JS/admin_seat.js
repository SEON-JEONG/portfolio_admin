var html = new Vue({
	el : "#vue",
	data : {
		ch:seat_search_ch,
		sch:seat_search,
		start : [],
		end : [],
		result:[],
		idx:[],
		pr:[],
		aaa:[],
		null:[]
	},
	methods:{
		nonereserve:function(){
			f.air_resuerve_ck.value="none"
			f.seat_search.value = ""
			
			f.action="./admin_seat.do"
			f.method="post"
			f.enctype="application/x-www-form-urlencoded"
			f.submit();
		},
		ysereserve:function(){
			f.air_resuerve_ck.value="yes"
			f.seat_search.value = ""
			
			f.action="./admin_seat.do"
			f.method="post"
			f.enctype="application/x-www-form-urlencoded"
			f.submit();
		},
		search:function(){
			
			f.action="./admin_seat.do"
			f.method="post"
			f.enctype="application/x-www-form-urlencoded"
		},
		goinsert:function(){
			this.start = [];
			this.end = [];
			this.result = [];
			this.null = [];
			
			var i = 0;
			var ea = f2.sd.length
			while(i < ea){
				if(f2.sd[i]['value'] != "" && f2.ed[i]['value'] != ""){
					this.start.push(f2.sd[i]['value'])
					this.end.push(f2.ed[i]['value'])
					this.idx.push(f2.dx[i]['value'])
					this.pr.push(f2.pri[i]['value'])
					console.log(i)
				}
				i++;
			}
			i = 0;
			
			if(this.start != []){
				while(i < this.start.length){
					var s = new Date(this.start[i])
					var e = new Date(this.end[i])
					if(s > e) {
						this.result.push(false)
					}else {
						this.result.push(true)
					}
					i++;
				}
			}
			i = 0;
			while(i < ea){
				if(f2.sd[i]['value'] != "" && f2.ed[i]['value'] != "" && f2.pri[i]['value'] != ""){
					this.null.push(true)
				}else if(f2.sd[i]['value'] == "" && f2.ed[i]['value'] == "" && f2.pri[i]['value'] == "") {
					this.null.push(true)
				}else {
					this.null.push(false)
				}
				i++;
			}
			
			f2.pidx.value = this.idx
			f2.rstart_date.value = this.start
			f2.rend_date.value = this.end
			f2.rprice.value = this.pr
			
			console.log(this.null)
			
			if(this.result.indexOf(false) > -1){
				alert("예약 종료일 보다 예약 시작일이 클 수 없습니다")
			}
			else if(this.null.indexOf(false) > -1){
				alert("등록할 항공코드의 예약정보가 있다면 빈칸을 채워주세요");
			}
			else {
				f2.action="./admin_seat_ok.do"
				f2.method="post"
				f2.enctype="application/x-www-form-urlencoded"
				f2.submit()
			}
		}
	}
})