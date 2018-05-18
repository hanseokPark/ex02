<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 서버내부 업로드 -->
	<p>writer : ${writer }</p>
	<c:forEach var = "item" items="${listPath }">
		<p>file : ${item }</p>
		<img src="resources/upload/${item }"/>
	</c:forEach>
	
	
</body>
</html>