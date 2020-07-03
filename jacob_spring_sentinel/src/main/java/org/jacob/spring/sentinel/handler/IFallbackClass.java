package org.jacob.spring.sentinel.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IFallbackClass {

	private static final Logger log = LoggerFactory.getLogger(IFallbackClass.class);

	public static String fallbackHandler(String name, String sex, int age) {
		log.info("Oops: ....IFallbackClass 方法");
		return "....IFallbackClass 方法";
	}
}
