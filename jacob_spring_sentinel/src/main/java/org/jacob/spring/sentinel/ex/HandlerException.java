package org.jacob.spring.sentinel.ex;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class HandlerException {

	public static String helloworld3Handler(BlockException ex) {

		System.out.println("Oops: " + ex.getClass().getCanonicalName());
		return "系统限流了111....";
	}
}
