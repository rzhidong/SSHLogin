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
		
		$('#ensureBtn').click(function(){
			var oldPassword = $('#oldPassword').val();
			var newPassword = $('#newPassword').val();
			if (oldPassword == null || newPassword == null || oldPassword == "" || newPassword == "") {
				$('#errorTip').html("新输入的密码不能为空!");
				return false;
			}
			if(oldPassword!=newPassword){
				$('#errorTip').html("两次输入的密码不一致!");
				return false;
			}
			$('#errorTip').html("");
			
			var id=$('#userId').val();
			$.post(serverUrl+'/user/updateUserPassword/'+parseInt(id),{
				password:newPassword
			},function(requestData){//service 传过来是map类型
				if (requestData.updateRes=='ok') {
					alert('密码修改成功!返回登录');
					backIndex();
				} else {
					alert('密码修改失败,请联系系统管理员!');					
				}
			});
		});
		
		function backIndex(){
			setTimeout(function(){
				window.location.href='${ctx}/';
			}, 200);
		}
	});
	
	
</script>
</head>
<body>
<div  align="center" style="padding-top: 50px;">
	<input type="hidden" id="serverUrl" value="${pageContext.request.contextPath}" />
	<input type="hidden" id="userId" value="${valiUser.id}"/>
	<h2>验证信息 ${validateRes}</h2>
	<br>
	<c:choose>
		<c:when test="${validateRes=='验证成功'}">
			<form>
				<table>
				<tr>
					<td>用户名：</td>
					<td><input type="text" value="${valiUser.userName}" readonly="readonly"/></td>
				</tr>
				<tr>
					<td>新密码：</td>
					<td><input type="password" id="oldPassword" /></td>
				</tr>
				<tr>
					<td>确认密码：</td>
					<td><input type="password" id="newPassword" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="button" value="确认" id="ensureBtn"/></td>
				</tr>
				</table>
				<div id="errorTip"></div>		
			</form>
		</c:when>
		<c:otherwise>
			<font size="7" color="#ff000">*${validateRes}2秒后返回首页</font><br>
			<script type="text/javascript">
				$(function(){
					setTimeout(function(){
						window.location.href='${pageContext.request.contextPath }/';
					}, 2000);
				});
			</script>
		</c:otherwise>
	</c:choose>
</div>
</body>
</html>
