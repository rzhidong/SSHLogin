package com.ssh.utils;

import java.util.UUID;

/**
 * 序列号生成工具类
 * @author 
 *
 */
public class UUIDUtils {
	
	/**
	 * 比较普通的序列号生成方法
	 * @return
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

}
