package org.jacob.spring.redis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

@RestController
public class HelloController {

	private static final Logger log = LoggerFactory.getLogger(HelloController.class);

	/**
	 * 从 Redis 2.6.12 版本开始， SET 命令的行为可以通过一系列参数来修改：
	 *
	 * EX seconds ： 将键的过期时间设置为 seconds 秒。 执行 SET key value EX seconds 的效果等同于执行 SETEX key seconds value 。
	 *
	 * PX milliseconds ： 将键的过期时间设置为 milliseconds 毫秒。 执行 SET key value PX milliseconds 的效果等同于执行 PSETEX key milliseconds value 。
	 *
	 * NX ： 只在键不存在时， 才对键进行设置操作。 执行 SET key value NX 的效果等同于执行 SETNX key value 。
	 *
	 * XX ： 只在键已经存在时， 才对键进行设置操作。
	 *
	 * 预测：因为 SET 命令可以通过参数来实现 SETNX 、 SETEX 以及 PSETEX 命令的效果， 所以 Redis 将来的版本可能会移除并废弃 SETNX 、 SETEX 和 PSETEX 这三个命令。
	 */
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
