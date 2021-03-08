package org.jacob.spring.controller;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class HttpController {

	/**
	 * 
	 * @param version
	 * @return
	 * @PathVariable  使用说明 可以匹配 正则表达式，固定值，并传值给req 参数
	 */
	@RequestMapping("/hello/{version:[a-z]}")
	public String hello(@PathVariable String version) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return version + "============" + UUID.randomUUID().toString();
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(HttpController.class, args);
	}

}
