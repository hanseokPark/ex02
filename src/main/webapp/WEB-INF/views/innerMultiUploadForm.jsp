<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="innerMultiUpload" method="post" enctype="multipart/form-data">
		<input type="text" name="writer" placeholder="작성자이름">
		<input type="file" name="files" multiple="multiple"><!-- 여러개 파일 선택 업로드 -->
		<input type="submit">
	</form>
</body>
</html>