package com.jacob.spring.apollo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jacob.spring.apollo.service.InitApolloService;

@RestController
public class HelloController {
	
	@Value("${dubbo}")
	private String apolloName;
	@RequestMapping("/hello")
	public String  hello() {
		return  apolloName+InitApolloService.getValue("test");
	}

}
