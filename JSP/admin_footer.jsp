<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<footer class="main_copyright" style="height:85px;">
        <div id="footer1" style="height:85px;"></div>
	</footer>
	
	<script>
	window.onload = function(){
		var html = new XMLHttpRequest();
		html.onreadystatechange = function(){
			if(html.readyState == XMLHttpRequest.DONE){
				if(html.status==200){
					var footer = document.getElementById("footer1");
					footer.innerHTML = this.response;
				}
			}
		}	
		html.open("GET","./footer.do");
		html.send();
	}
	</script>