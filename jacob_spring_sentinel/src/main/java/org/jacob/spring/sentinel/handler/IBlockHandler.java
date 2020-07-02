package org.jacob.spring.sentinel.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class IBlockHandler {

	private static final Logger log = LoggerFactory.getLogger(IBlockHandler.class);

	public static String ihelloworld3Handler(String name, String sex, BlockException ex) {
		log.info("Oops: " + ex.getClass().getCanonicalName());
		return "系统限流了....IBlockHandler 方法";
	}
}
