<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p>writer : ${writer }</p>
	<p> file : ${file } </p>
	<!-- 파일 외부 업로드 -->
	<img src="displayFile?filename=${file }"/>
</body>
</html>