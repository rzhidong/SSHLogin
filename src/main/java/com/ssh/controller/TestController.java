package com.ssh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssh.service.TestService;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@RequestMapping("/springmvc")
	@ResponseBody
	public String springmvc() {
		return "springmvc test success";
	}
	
	@Autowired
	private TestService testService;
	
	@RequestMapping("/spring")
	@ResponseBody
	public String spring() {
		return testService.test();
	}
	
}
