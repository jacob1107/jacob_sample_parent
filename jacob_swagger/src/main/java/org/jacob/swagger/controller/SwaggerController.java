package org.jacob.swagger.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
public class SwaggerController {

	@RequestMapping("/hello")
	public String postTest() {
		return "hello swagger!";
	}

	@ApiOperation(value = "swagger测试", notes = "swagger测试", protocols = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username", value = "用户名", dataType = "String", paramType = "query"), })
	@GetMapping("/test")
	String test(String username) {
		return " res 测试";
	}

}
