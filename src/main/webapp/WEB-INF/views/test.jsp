<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<style>
	.pagination{
		width:100%
	}
	.pagination li{
		list-style:none;
		float:left;
		padding :3px;
		border:1px solid blue;
		margin: 3px;
	}
	.pagination li a{
		margin:3px;
		text-decoration: none;
	}
</style>
</head>
<body>
	<h1>Ajax Test Page</h1>
	<div>
		<div>
			bno <input type="text" name="bno" id="bno">
		</div>
		<div>
			replyer <input type="text" name="replyer" id="replyer"> 
		</div>
		<div>
			replytext <input type="text" name="replytext" id="replytext">
		</div>
		<button id="addReplyBtn">add Reply</button>
		<button id="getListBtn">get List All</button>
		<button id="getListPageBtn">get List Page</button>
	</div>	
	<hr>	
	<ul id="replies">		
	</ul>
	<ul class="pagination">
		
	</ul>
	
	<!-- url:"pageContext.request.contextPath/replies", replies 도 가능 -->
	<script>
		var pageNumber = 1;
		
		$("#getListPageBtn").click(function(){
			var bnoVal = $("#bno").val();
			
			$.ajax({
				url:"replies/" + bnoVal + "/" + pageNumber,
				type:"get",
				dataType:"json",
				success:function(result){
					console.log(result);
					//list
					displayList(result);
					
					//pagination					
					displayPaging(result);
					
				}
				
				
			})
		})
		function displayList(result){
			$("#replies").empty();
			
			$(result.list).each(function(i, obj){
				var liObj = $("<li><input type='hidden' class='input' value='"+obj.rno+"'>")
				liObj.append(obj.rno + "," + obj.replyer + ", <span class='replytext'>" + obj.replytext+"</span>");
				liObj.append("<button class='modBtn'>"+"수정"+"</button>"+"<button class='delBtn'>"+"삭제"+"</button>")
				$("#replies").append(liObj);
			})   
			
			
		}
		
		function displayPaging(result){
			var str = "";
			
			if(result.pageMaker.prev){
				str += "<li><a href='#'> &lt;&lt; </a></li>"
			}
			
			for(var i = result.pageMaker.startPage; i<= result.pageMaker.endPage; i++){
				str += "<li><a href='#'>" + i + "</a></li>"
			}
			
			if(result.pageMaker.next){
				str += "<li><a href='#'> &gt;&gt; </a></li>"
			}
			
			
			$(".pagination").html(str);
		}
				
		
		$(document).on("click", ".pagination a", function(e){
			e.preventDefault();//링크(link)를 막음
			
			pageNumber = $(this).text();//해당 a태크의 값이 들어가면됨
			
			
			// 밑에 둘 중 하나 이용 밑에꺼 권장
			/*  $("#getListPageBtn").click(); */		
			$("#getListPageBtn").trigger("click"); 
			
			
			
		})
	
		$("#addReplyBtn").click(function(){
			var bnoVal = $("#bno").val();
			var rnoVal = $("#rno").val();
			var replyerVal = $("#replyer").val();
			var replytextVal = $("#replytext").val();
			
			var sendData = {bno:bnoVal, replyer: replyerVal, replytext:replytextVal };
			
			//@RequestBody, JSON.stringify, headers:Content-Type
			$.ajax({
				type:"post",
				url:"/ex02/replies",
				data: JSON.stringify(sendData),  // JSON.stringify() -> json string로 바꿔줌
				dataType:"text", //xml,text,json
				headers:{"Content-Type":"application/json"},
				success:function(result){
					console.log(result)
				}
			})                     
		})
		
		$("#getListBtn").click(function(){
			var bnoVal = $("#bno").val();
			
			$.ajax({
				type:"get",
				url:"/ex02/replies/all/" +bnoVal,
				dataType:"json",
				success:function(result){
					console.log(result);
					
					/* displayList(result) */  
					$("#replies").empty();
					
					$(result).each(function(i, obj){
						var liObj = $("<li><input type='hidden' class='input' value='"+obj.rno+"'>")
						liObj.append(obj.rno + "," + obj.replyer + ", <span class='replytext'>" + obj.replytext+"</span>");
						liObj.append("<button class='modBtn'>"+"수정"+"</button>"+"<button class='delBtn'>"+"삭제"+"</button>")
						$("#replies").append(liObj);
					}) 
					  
				}    
			})            
			     
		})
		
		$(document).on("click",'.delBtn',function(){			
			var rnoVal =  $(this).parent().find(".input").val();
			var obj = $(this).parent();
			
			console.log(rnoVal);
			
			$.ajax({
				type:"delete",
				url:"/ex02/replies/" + rnoVal,					
				dataType:"text",
				success:function(result){
					console.log(result);
					if(result == 'success'){
						alert("삭제 되었습니다.");
						obj.remove();
						
					}  
				}
			})
		})
		
		$(document).on("click",".modBtn",function(){
			var rnoVal =  $(this).parent().find(".input").val();
			var obj = $(this).parent();
			var replytext = $(this).parent().find(".replytext").text();
			console.log(replytext);
			
			/* <input type="text" name="bno" id="bno"> obj.replytext*/
			
			obj.append("<br>rno : <input type='text' class='modrno' name='rno' readonly value='"+rnoVal+"'> replytext : <input type='text' class='modreplytext' name='replytext'  value='"+replytext+"'>  ");
			obj.append("<button class='btnmod'>"+"수정 완료"+"</button>");
			
			
			
			
			
		})
		
		$(document).on("click",".btnmod",function(){
			var rnoVal =  $(this).parent().find(".modrno").val();
			var replytextVal =  $(this).parent().find(".modreplytext").val();
			
			
			
			$.ajax({
				type:"put",
				url:"/ex02/replies/"+rnoVal,
				dataType:"text",
				headers:{"Content-Type":"application/json"},
				data: JSON.stringify({replytext:replytextVal}),
				
				success:function(result){
					console.log(result);
					if(result == 'success'){
						alert("수정 되었습니다.");
						getPageList();
						 
					}
				}
			})			
		
		})
		function getPageList(){
				var bnoVal = $("#bno").val();
				
				$.ajax({
					type:"get",
					url:"/ex02/replies/all/" +bnoVal,
					dataType:"json",
					success:function(result){
						console.log(result);
						$("#replies").empty();
						
						$(result).each(function(i, obj){
							var liObj = $("<li><input type='hidden' class='input' value='"+obj.rno+"'>")
							liObj.append(obj.rno + "," + obj.replyer + ", <span class='replytext'>" + obj.replytext+"</span>");
							liObj.append("<button class='modBtn'>"+"수정"+"</button>"+"<button class='delBtn'>"+"삭제"+"</button>")
							$("#replies").append(liObj);
						})   
						  
					}   
				})            		
		}
	</script>
</body>
</html>
























