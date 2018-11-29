<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人信息验证成功</title>
<c:set value="${pageContext.request.contextPath }" var="ctx"></c:set>
<script type="text/javascript" src="${ctx}/js/jquery-3.2.1.js"></script>
 
</head>
<body>
	<input type="hidden" id="serverUrl" value="${pageContext.request.contextPath}" />
	<input type="hidden" id="userId" value="${valiUser.id}" />
	
	<div align="center" style="padding-top: 150px;"><h3>${validateRes}  <br> 4s后会自动跳转到首页....</h3></div>
	<script type="text/javascript">
		var userId=$('#userId').val();
		var serverUrl=$('#serverUrl').val();
		
		console.log(userId+" -- "+serverUrl);
		setTimeout(function(){
				window.location.href=serverUrl+"/user/index/";
			}, 4000);

	</script>
	
</body>
</html>
