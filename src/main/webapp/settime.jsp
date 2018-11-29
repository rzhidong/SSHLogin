<%@page import="com.ssh.utils.SystemUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:set value="${pageContext.request.contextPath }" var="ctx"></c:set>
<script type="text/javascript" src="${ctx}/js/jquery-3.2.1.js"></script>
<title>Insert title here</title>
<script language="javascript">
	$(function(){
		setTimeout(function(){ 
			window.location.href='${ctx}/';
		}, 3000); 
		 
	})

</script>
</head>
<body>
	<span style="color: red">welcome</span>
	<br />
	<br />
	<div id="num"></div>
</body>
</html>