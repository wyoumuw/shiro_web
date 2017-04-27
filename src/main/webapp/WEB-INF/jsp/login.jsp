<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<% String context=request.getContextPath(); %>
</head>
<body>

<form action=<%=context+"/system/login"%> method="post">
<input type="text" id="username" name="username">
<input type="password" id="password" name="password">
<input type="submit">
</form>
</body>
</html>