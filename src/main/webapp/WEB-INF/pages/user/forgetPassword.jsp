<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>找回密码</title>
<c:set value="${pageContext.request.contextPath }" var="ctx"></c:set>
<script type="text/javascript" src="${ctx}/js/jquery-3.2.1.js"></script>
<script type="text/javascript">
	
	$(function(){
		var serverUrl=$('#serverUrl').val();
		
		$("#findPasword").click(function(){
			
			var userName = $('#userName').val();
			var email = $('#email').val();
			if (userName == null || email == null || trim(userName) == "" || trim(email) == "") {
				alert("请填写用户名与邮箱,以用于找回密码!");
				return false;
			}
			
			//邮箱验证
			if(trim(email) != ""){
				var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
				isok = reg.test(email);
				if (!isok) {
	                alert("邮箱格式不正确，请重新输入！");
	                return false;
	            }
			}
			
			console.log(userName+" -- "+email);
			
			var url=this.href;
			$.post(url,{
				userName:userName,
				email:email
				
			},function(requestData){
				console.log(requestData.res);
				if(requestData.res=='yes'){
					alert('用于找回密码的验证信息已发送到您的邮箱,请您前往邮箱进行验证!');
					
				}else if(requestData.res=='no'){
					alert('您填写的用户名与邮箱不匹配,请填写准确以用于找回密码!');
				}
				
			})
			return false;
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
	<input type="hidden" id="serverUrl" value="${pageContext.request.contextPath}" />
<div align="center" style="padding-top: 50px;">	
	<form>
		<table>
			<tr>
				<td>用户名：</td>
				<td><input type="text" id="userName" name="userName" /></td>
			</tr>
			<tr>
				<td>邮箱：</td>
				<td><input type="text" id="email" name="email" /></td>
			</tr>
		</table>
	</form>
	<a href="${ctx}/user/findPassword" id="findPasword">找回密码</a>
	<a href="${ctx }/">返回登录</a>
	<br><br>
</div>
</body>
</html>
