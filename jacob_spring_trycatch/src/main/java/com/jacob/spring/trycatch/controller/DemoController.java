package com.jacob.spring.trycatch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jacob.spring.trycatch.bean.R;
import com.jacob.spring.trycatch.bean.User;
import com.jacob.spring.trycatch.enums.ResponseEnum;
import com.jacob.spring.trycatch.ex.GeneraBusinessException;

@RestController
public class DemoController {

	private static final Logger log = LoggerFactory.getLogger(DemoController.class);

	@RequestMapping("say")
	public Object say() throws GeneraBusinessException {
		User user = new User(-1, "jacob");
		log.info("hello===============try---catch");
		ResponseEnum.CAN_NOT_IS_NULL.assertUserIsValid(user);
		return new R("0000", String.valueOf(Math.random()));

	}

}
