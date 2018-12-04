<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:set value="${pageContext.request.contextPath }" var="ctx"></c:set>
<script type="text/javascript" src="${ctx}/js/jquery-3.2.1.js"></script>
<title>用户登录</title>
<script type="text/javascript">
	
	$(function(){
		var serverUrl=$('#serverUrl').val();
		
		$('#loginBtn').click(function(){
			var userName = $('#userName').val();
			var password = $('#password').val();
			if (userName == null || password == null || trim(userName) == "" || trim(password) == "") {
				$('#errorTip').html("");
				$('#errorTip').html("用户名或密码不能为空!");
				return false;
			}
			$('#errorTip').html("");
			
			$.post(serverUrl+'/user/login',{
				userName:userName,
				password:password
				
			},function(resData){
				if (resData.res=='yes') {
					window.location.href=serverUrl+'/user/index';
				} else if(resData.res == 'no') {
					$('#errorInfo').html("");
					$('#errorInfo').html('*用户名或者密码错误!');					
				} else if(resData.res == 'validate'){
					$('#errorInfo').html("");
					$('#errorInfo').html('*用户名未验证!');	
				}
				
			});
			
		});
	});
 
	function resetValue() {
		$('#userName').val("");
		$('#password').val("");
	}
 
	//去掉最后的空格
	function trim(str) {
		return str.replace(/(^\s+)|(\s+$)/g, "");
	}
 
</script>
</head>
<body>
<%
// 	 HttpSession currentSession = request.getSession();
// 	if (currentSession != null){
// 		if(currentSession.getAttribute("id") != null){
// 			response.sendRedirect(request.getContextPath()+"/user/index");
// 		}
// 	} 
%>

<input type="hidden" id="serverUrl" value="${pageContext.request.contextPath}" />
	<div align="center" style="padding-top: 150px;">
		<form>
			<table>
				<tr>
					<td>用户名：</td>
					<td><input type="text" id="userName" name="userName" /></td>
				</tr>
				<tr>
					<td>密码：</td>
					<td><input type="password" id="password" name="password" /></td>
 
				</tr>
				<tr>
					<td><input type="button" value="登录" id="loginBtn"/></td>
					<td><input type="button" value="重置" onclick="resetValue()"/></td>
				</tr>
				<tr>
					<td></td>
					<td id="errorInfo" style="color: #ff0000;"></td>
				</tr>
			</table>
			<div id="errorTip"></div>
		</form>
		<a href="${ctx}/user/forgetPassword">忘记密码</a>  <a href="${ctx}/user/toRegister">用户注册</a>
	</div>
</body>
</html>