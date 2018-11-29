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
<title>检测Email地址格式是否正确</title>
</head>
<body>
<%
	System.out.println(SystemUtils.getProjectRootPath(request));
	System.out.println(SystemUtils.getProjectURLPath(request));
%>
	<input type="text" id="emailname">
	<input type="submit" value="检测Email地址格式是否正确" class="is-email">

	<script type="text/javascript">
		$(".is-email")
				.click(
						function() {
							var email = $("#emailname").val();
							if (email == '') {
								alert("请输入您的邮箱");
								return;
							} else if (email != "") {
								var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
								isok = reg.test(email);
								if (!isok) {
									alert("邮箱格式不正确，请重新输入！");
									return false;
								}
							}
							alert("ok 输入正确");
				});
	</script>
</body>
</html>