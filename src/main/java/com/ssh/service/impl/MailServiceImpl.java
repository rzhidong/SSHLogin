package com.ssh.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.ssh.service.MailService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service("mailService")
public class MailServiceImpl implements MailService {

	@Autowired
	private SimpleMailMessage mailMessage;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	@Autowired
	private Configuration freeMarkerConfiguration;
	
	public void send(List<String> recipients, String emailSubject, String emailContent) {
		// TODO Auto-generated method stub
		try {
			for (String recp : recipients) {
				mailMessage.setTo(recp);
	            mailMessage.setSubject(emailSubject);
	            mailMessage.setText(emailContent);
	            
	            mailSender.send(mailMessage);
			}
             System.out.println(recipients+"邮件发送完毕");
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(recipients+" 邮件发送失败");
			System.err.println(e.getMessage());
		}
	}

	public void sendWithVelocity(List<String> recipients, String emailSubject) {
		// TODO Auto-generated method stub
		try {
			for (String recp : recipients) {
	            Map<String, Object> modal = new HashMap<String, Object>();
	            modal.put("username", recp);
	            modal.put("url", "https://blog.csdn.net/rzhidong");
	            modal.put("email", "rzhidong@yeah.net");
	            //使用VelocityEngineUtils将Velocity模板与模型数据合并成String
	            @SuppressWarnings("deprecation")
				String text = VelocityEngineUtils
	                    .mergeTemplateIntoString(velocityEngine, "mailTemplete/velocity/mail.vm", "UTF-8", modal);
	            
	            mailSender.send(textToHtml(recp, emailSubject, text));
	            
			}
             System.out.println(recipients+"邮件发送完毕");
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(recipients+" 邮件发送失败");
			System.err.println(e.getMessage());
		}
	}

	public boolean sendWithFreeMarker(String recipients, String emailSubject,Map<String, Object> model) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
	            Template template = freeMarkerConfiguration.getTemplate("mail.ftl");
	            String text =FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
	            
	            mailSender.send(textToHtml(recipients, emailSubject, text));
	            
             System.out.println(recipients+"邮件发送完毕");
             flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(recipients+" 邮件发送失败");
			System.err.println(e.getMessage());
			throw new RuntimeException(recipients+" 邮件发送失败");
		}
		return flag;
	}

	public void sendWithThread(final List<String> recipients, final String emailSubject) {
		// TODO Auto-generated method stub
		Runnable runnable = new Runnable() {
			
			public void run() {
				System.out.println(recipients+"\t"+emailSubject);
				try {
					for (String recp : recipients) {
						mailMessage.setTo(recp);
			            mailMessage.setSubject(emailSubject);
			            
			            Map<String, Object> model = new HashMap<String, Object>();
			            model.put("username", recp);
			            model.put("url", "https://blog.csdn.net/rzhidong");
			            
			            Template template = freeMarkerConfiguration.getTemplate("mail.ftl");
			            String text =FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
			            
			            mailSender.send(textToHtml(recp, emailSubject, text));
			            
					}
		             System.out.println(recipients+"邮件发送完毕");
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(recipients+" 邮件发送失败");
					System.out.println(e.getMessage());
				}
				
			}
		};
		new Thread(runnable).start();
	}
	
	// 发送HTML内容邮件 
	public MimeMessage textToHtml(String recp,String emailSubject,String text) {
		
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = null ;
        try {
        	messageHelper = new MimeMessageHelper(mimeMessage,true, "UTF-8");
            
            messageHelper.setFrom(mailMessage.getFrom());
            messageHelper.setTo(recp);
            messageHelper.setSubject(emailSubject);
            messageHelper.setText(text,true);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mimeMessage;
	}

	

}
