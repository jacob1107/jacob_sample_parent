package org.jacob.spring.sentinel.controller;

import org.jacob.spring.sentinel.handler.IBlockHandler;
import org.jacob.spring.sentinel.handler.IFallbackClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;

@RestController
public class HelloController {

	private static final Logger log = LoggerFactory.getLogger(HelloController.class);

	@RequestMapping("/say")
	@SentinelResource(value = "say", blockHandler = "iblockhandler", blockHandlerClass = IBlockHandler.class, fallbackClass = IFallbackClass.class, fallback = "fallbackHandler")
	// @SentinelResource(value = "helloworld3", fallbackClass =
	// IFallbackClass.class, fallback = "fallbackHandler")
	public String helloworld3(String name, String sex,int age) {

		// int i = 1 / 0;
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return String.valueOf(Math.random());
	}

	public static String helloworld3Handler(BlockException ex) {

		log.info("Oops: " + ex.getClass().getCanonicalName());
		return "系统限流了111....";
	}
}
