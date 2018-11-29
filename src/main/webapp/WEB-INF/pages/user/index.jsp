<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:set value="${pageContext.request.contextPath }" var="ctx"></c:set>
<title>Insert title here</title>
</head>
<body>
<div align="center"><h2>Hello,当前用户${loginUser.userName }</h2>&nbsp<a href="${ctx }/user/logout">注销</a></div>
</body>
</html>