package com.ssh.test;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class HibernateTest {
	
	private ApplicationContext context;
	
	
	{
		context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
	}	
	
	@Test
	public void testDataSource() {
		DataSource dataSource = (DataSource) context.getBean("dataSource");
		try {
			System.out.println(dataSource.getConnection());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}
	

}
