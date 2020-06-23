package org.jacob.spring.sentinel.controller;

import org.jacob.spring.sentinel.ex.HandlerException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;

@RestController
public class HelloController {

	@RequestMapping("/helloworld3")
	@SentinelResource(value = "helloworld3", blockHandler = "helloworld3Handler")
	public String helloworld3() {

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "hello Sentinel333 !";
	}

	public static String helloworld3Handler(BlockException ex) {

		System.out.println("Oops: " + ex.getClass().getCanonicalName());
		return "系统限流了111....";
	}
}
