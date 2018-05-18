<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 서버 외부 업로드 -->
	<p>writer : ${writer }</p>
	<p>fileName : ${fileName }</p>
	<img src="displayFile?filename=${fileName }"/>
</body>
</html>