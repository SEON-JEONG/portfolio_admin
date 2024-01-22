var html = new Vue({
	el : "#vue",
	data : {
		name_arr : [],
		aircode_arr : []
	},
	methods:{
		planecode_ch:function(){
			/*
			  headers: {
	    Accept: "application / json",
	  }, method: "GET",dataType : "json",
			 */
			
			
			if(f.airplane_code.value != "x"){
				fetch("./admin_product_ch.do?airplane_code="+f.airplane_code.value+"&ck="+"name").then(function(data){
					return data.text();
				}).then(function(data){
					//console.log(data)
					//var test = JSON.parse(data)
					html.result(data,"name")
				}).catch(function(error){
					console.log("ajax 통신 오류1",error)
				})
			}
		},
		name_ch:function(){
			if(f.airname.value != "x"){
				fetch("./admin_product_ch.do?airplane_code="+f.airplane_code.value+"&airname="+f.airname.value+"&ck="+"code").then(function(data){
					return data.text()
				}).then(function(data){
					html.result(data,"code")
				}).catch(function(error){
					console.log("ajax 통신 오류2",error)
				})
			}
		},
		code_ch:function(){

			if(f.aircode.value != "x"){
				fetch("./admin_product_ch.do?aircode="+f.aircode.value+"&ck="+"flight").then(function(data){
					return data.text()
				}).then(function(data){
					f.airflight.value = data
				}).catch(function(error){
					console.log("ajax통신 오류3",error)
				})
			}
		},
		result:function(data, ck){
			
			data = data.replace('[','')
			data = data.replace(']','')
			data = data.split(', ')
			if(ck == "name"){
				this.name_arr = data
			}
			else if(ck == "code"){
				this.aircode_arr = data
			}
		},
		writeok:function(){
			var numck= /[^0-9]/g
			// 출발지, 도착지 동일 여부 체크 (저장시엔 공백은 포함)
			var startck = f.start_point.value.replaceAll(" ","")
			var endck = f.end_point.value.replaceAll(" ","")
			
			if(f.airplane_code.value == "x" || f.airname.value == "x" || f.aircode.value == "x" || f.start_point.value == "" || f.end_point.value == "" || f.seat_form.value == ""){
				alert('빈칸을 모두 채워주세요')
			}else if(numck.test(f.seat_count.value)){
				alert('좌석 수는 숫자만 입력하세요')
				f.seat_count.value = 0
				f.seat_count.focus();
			}else if(startck == endck){
				alert('출발지와 도착지는 같을 수 없습니다')
				f.end_point.value = ""
				f.end_point.focus();
				
			}else{
				if(f.seat_count.value == 0) {
					if(confirm('좌석 수가 0입니다. \n 그래도 등록 하시겠습니까?')){
						f.aidx = 
						f.action="./admin_product_writeok.do"
						f.method="post"
						f.enctype="application/x-www-form-urlencoded"
						f.submit();
					}
				}
				else {
					if(confirm('항공편 등록을 하시겠습니까?')){
						f.action="./admin_product_writeok.do"
						f.method="post"
						f.enctype="application/x-www-form-urlencoded"
						f.submit();
					}
				}
				
			}
		}
	}
})