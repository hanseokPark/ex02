<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<style>
	.wrap{
		position: relative;
		width:100px;
		height:100px;
		border:1px solid gray;
		text-align: center;
	}
	.wrap button{
		position: absolute;
		bottom:0px;
		right:0px;
	}
</style>
</head>
<body>
	<p>writer : ${writer }</p>
	<p> file : ${file } </p>
	<!-- 파일 외부 업로드 -->
	<div class="wrap">
		<img src="displayFile?filename=${file }"/>
		<button data-file="${file}">X</button>
	</div>
	<form action="deleteFile" method="get" id="f1">
		<input type="text" name="filename" id="delFile">
	</form>
	
	<script>
		$(".wrap button").click(function(){
			var path = $(this).attr("data-file");
			
			$("#delFile").val(path);
			
			$("#f1").submit();
			
			
		})
		
		
		
		
		
	</script>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
</body>
</html>