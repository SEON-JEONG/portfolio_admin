function logout(){
 location.href='./logout.do';
}

var html = new Vue({
	el:"#main",
	data:{
		ori:[],
		arr:[],
		yes:"Y",
		no:"N",
		count:0,
		method:"POST",
		action:"./mainlist.do",
		enc:"application/x-www-form-urlencoded"
	},
	methods:{
		datas:function(data){
			if(data == null){
				this.count = 0;
			}
			else{
				this.ori = data;
				this.arr = this.ori;
				this.count++;
			}			
		},
		
		agree:function(a){
			location.href="./agree.do?aidx="+a;
		},
		
		disagree:function(a){
			location.href="./disagree.do?aidx="+a;
		}
		
	},
	computed:{
		json:function(){
			fetch("./api_json.do").then(function(res){
				return res.json();
			}).then(function(data){
				html.datas(data)
			}).catch(function(error){
				console.log("api통신 오류!")
			})
		}
	},
		
});