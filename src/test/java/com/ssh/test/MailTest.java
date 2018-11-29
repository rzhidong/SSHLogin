package com.ssh.test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ssh.service.MailService;

public class MailTest {
	
private ApplicationContext context;
	
	
	{
		context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
	}	
	
	@Test
	public void testMail() {
		MailService mailService = (MailService) context.getBean("mailService");
		
		List<String > recipients = new LinkedList<String>();
		recipients.add("rzhidong@126.com");
		recipients.add("327547207@qq.com");
		recipients.add("455591433@qq.com");
		
		
		//mailService.send(recipients, "新员工入职信息", "hello");
		
		//mailService.sendWithVelocity(recipients, "Velocity");
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		model.put("username", recipients.get(0));
		String validateStr = "https://blog.csdn.net/rzhidong";
		String content = "验证信息-用于注册成功时候的验证, 请点击下面的安全链接,用于验证个人信息<br><a href='"+validateStr+"'>验证信息</a>";
		model.put("content", content);
		
		mailService.sendWithFreeMarker(recipients.get(0), "FreeMarker",model);
		
		//mailService.sendWithThread(recipients, "thread");
	}

}
