package org.jacob.spring.constructor.controller;

import javax.annotation.Resource;
import org.jacob.spring.constructor.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConstructorController {

	@Resource
	private UserService userService;

	@RequestMapping("/constructor")
	public String postTest() {
		userService.service();
		return "hello Constructor!";
	}

}
