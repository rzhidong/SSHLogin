package com.ssh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * session是服务器端,为每个客户端创建的独立的数据容器。
 * 通过 request.getSession()获取 session.
 * getSession()方法内部会去 request 请求里的cookie 和 url 中查找 jsessionid.
 * 如果找到就去内存中查找对于的客户端session容器，如果没有，为当前客户端，创建一个session.
 * 如果cookie 被禁用，那么可以通过url 来传递，但是局限很大，具体看下面代码。
 *
 * session 默认有效时长是 30分钟，如果30分钟内内操作，服务器端会默认销毁掉
 */
@RestController
@RequestMapping("session")
public class SessionController {
	
	@RequestMapping("buy")
	public String buy(HttpServletRequest request,HttpServletResponse response) {
		//服务器通过客户端回传的 jsessionid 获取，对应客户端的session.

        //如果当期没获取到jsessionid就会自己创建一个。
		HttpSession session = request.getSession();
		
		// 是当前session的失效时间，单位秒。如果设置的值为零或负数，则表示会话将永远不会超时。
		session.setMaxInactiveInterval(30*60);
		
		return "you buy a "+session.getAttribute("shop");
	}
	
	@RequestMapping("add")
	public String add(HttpServletRequest request,HttpServletResponse response) {
		//如果当期没获取到jsessionid就会自己创建一个。
		HttpSession session = request.getSession();
		
		session.setAttribute("shop","computer");
		
		return "add success";
	}
	
	@RequestMapping("home")
	public String home(HttpServletRequest request,HttpServletResponse response) {
		StringBuffer sb = new StringBuffer();
		
		 //cookie 被禁用后自己通过url 传递 sessionid，
        //局限问题：同一个用户,自己打开多个浏览器中传递sessionid,除非自己手动copy URL.
        //不同于Cookie ,cookie是保存在本地的，多个窗口访问同一个 地址（www.baidu.com），回去本地去读，cookie值，cookie 保存了jsessionid
        //同样也要注意，每个浏览器厂商保存cookie的位置和方式不同，不容浏览器也不能通过cookie 获取 sessionid.
        
		String jsessionid = request.getSession().getId();
		System.out.println("jsessionid: " + jsessionid);
		 //注意连接后面试';'不是问号。
        sb.append("<a href='"+request.getContextPath()+"/session/add;jsessionid="+jsessionid+"'>add</a><br>");
        sb.append("<a href='"+request.getContextPath()+"/session/buy;jsessionid="+jsessionid+"'>buy</a>");
        return sb.toString();
	}
}
