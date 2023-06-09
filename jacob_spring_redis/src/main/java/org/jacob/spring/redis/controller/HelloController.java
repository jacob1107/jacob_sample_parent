package org.jacob.spring.redis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

@RestController
public class HelloController {

	private static final Logger log = LoggerFactory.getLogger(HelloController.class);


	private JedisCluster jedisCluster;
	@RequestMapping("/say")
	public String helloworld3(String name, String sex,int age) {
		// int i = 1 / 0;
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("sentinel=============================1");
		return String.valueOf(Math.random())+"sentinel1";
	}

}
