package com.ssh.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class CookieController {
	
	@RequestMapping("/getData")
	public String getData(HttpServletRequest request,HttpServletResponse response,String value) {
		//客户端浏览器会根据服务器地址（例如：www.baidu.com），来查询本地保存的cookie 并发送给服务器端(例如：百度服务器)，
		//服务器会封装到request 中，然后通过request.getCookies()就可以获取到 cookie数据 。
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for(Cookie cookie : cookies) {
				if (cookie.getName().equals("lastTime") && cookie.getValue().equals(value)) {
					return "you last Time: "+cookie.getValue();
				}
			}
		}
		
		Cookie cookie = new Cookie("lastTime", value);
		
		//设置60秒失效， 设置为0 就会立即失效，如果不设置，cookie会生存到本次浏览器关闭（注意是浏览器关闭，不是关闭这个网站的标签）
		cookie.setMaxAge(60);
		
		 //设置浏览器在访问项目时，发送该cookie,否则访问 服务器时，都会带上这个cookie.
		cookie.setPath(request.getContextPath());
		
		response.addCookie(cookie);
		
		return "It's you first time to be here.";
		
	}
}
