package com.ssh.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统工具类
 * @author 
 *
 */
public class SystemUtils {
	
	/**
	 * 获得项目的根路径
	 * @param request
	 * @return
	 */
	public static String getProjectRootPath(HttpServletRequest request){
		return request.getContextPath();
	}
	
	/**
	 * 获得项目在浏览器地址栏显示的完整根路径
	 * @param request
	 * @return
	 */
	public static String getProjectURLPath(HttpServletRequest request){
		return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
	}
	
}
