package com.ssh.test;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.junit.Test;

import com.ssh.utils.DateUtils;
import com.ssh.utils.MailUtils;
import com.ssh.utils.SystemUtils;
import com.ssh.utils.UUIDUtils;

public class UtilTest {
	@Test
	public void testDate() {
		Date date1 = DateUtils.getCurrentDate();
		Date date2 = DateUtils.getDateAfter(1);
		System.out.println(date1 + "\t" + date2);
		System.out.println(DateUtils.isBeforeSpeciDate(date1, date2));
	}

	@Test
	public void testUUID() throws Exception {
		System.out.println(UUIDUtils.getUUID());
		byte[] bs = "123456".getBytes("UTF-8");
		System.out.println(String.valueOf(bs));
	}

	@Test
	public void testMail() {
//		int res = MailUtils.sendEmail("smtp.yeah.net", "rzhidong@yeah.net", "yeah2012", "rzhidong@yeah.net",
//				new String[] { "327547207@qq.com", "rzhidong@126.com" // 这里就是一系列的收件人的邮箱了
//				}, "节日祝福", "祝你国庆节快乐,欢迎来我的blog: <a href='https://blog.csdn.net/rzhidong'>我的blog</a>,祝您生活愉快!",
//				"text/html;charset=utf-8");
//
//		System.out.println("\n发送结果:" + res);
		System.out.println(MailUtils.isMailValidate("1232@1.cn"));
	}
	
	

}
