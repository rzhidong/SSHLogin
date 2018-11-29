package com.ssh.service;

import java.util.List;
import java.util.Map;

public interface MailService {
	
	/**
	 * 
	 * @param recipients 收件人:支持群发
	 * @param emailSubject 邮件的主题
	 * @param emailContent 邮件的内容
	 */
	public void send(List<String> recipients,String emailSubject,String emailContent);
	
	public void sendWithVelocity(List<String> recipients,String emailSubject);
	
	public boolean sendWithFreeMarker(String recipients,String emailSubject,Map<String, Object> model);
	
	public void sendWithThread(List<String> recipients,String emailSubject);
	
}
