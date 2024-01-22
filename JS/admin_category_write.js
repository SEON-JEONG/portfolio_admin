var html = new Vue({
	el : "#vue",
	data:{
		aircode:"",
		aircode_overlap: false
		
	},
	methods:{
		aircode_ck:function(){
			
			fetch("./aircode_ck.do?aircode="+this.aircode).then(function(data){
				return data.text();
			}).then(function(data){
				html.ck(data)
			}).catch(function(error){
				console.log("ajax 통신 오류!"+error)
			})
			
		},
		new_aircode:function(){
			var enck = /[^A-Z]+/g
			var scck = /[^a-z0-9가-힣\s]/ig;
			
			
			if(f.airplane_code.value == "" ||f.airname.value == "" ||this.aircode == "" ||f.airflight.value == ""){
				alert("빈칸을 모두 채워주세요")
			}
			else if(enck.test(f.airplane_code.value)){
				alert("공항코드는 영문 대문자만 입력이 가능합니다")
				f.airplane_code.focus();
			}
			else if(scck.test(f.airname.value)){
				alert("항공사 명은 특수문자가 입력되서는 안됩니다")
				f.airname.focus();
			}
			else if(this.aircode_overlap != true){
				alert("항공코드 중복체크를 하지 않았습니다")					
			}
			else {
				f.action="./admin_category_writeok.do";
				f.method="post";
				f.enctype="application/x-www-form-urlencoded";
				f.submit();				
			}
			
		},
		ck:function(data){
			if(data > 0){
				alert("해당 항공코드는 이미 등록 되어 있습니다")					
			}
			else {
				alert("해당 항공코드는 사용이 가능합니다")
				f.aircode.readOnly = "true"
				this.aircode_overlap = true
			}
		}
	}
})