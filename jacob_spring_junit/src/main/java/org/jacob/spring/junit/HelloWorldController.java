package org.jacob.spring.junit;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@RequestMapping(value = "/hello")
	public String hello(String name) {
		return "Hello " + name + "!";
	}
}
