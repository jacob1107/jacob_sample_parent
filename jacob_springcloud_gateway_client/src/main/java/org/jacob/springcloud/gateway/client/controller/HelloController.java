package org.jacob.springcloud.gateway.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	private static final Logger log = LoggerFactory.getLogger(HelloController.class);

	@RequestMapping("/a")
	public String a() {
		log.info("========this is a method=================");
		return "this is a methood";

	}

}
