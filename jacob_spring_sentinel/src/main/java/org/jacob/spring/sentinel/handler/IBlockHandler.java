package org.jacob.spring.sentinel.handler;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;

public class IBlockHandler {

	private static final Logger log = LoggerFactory.getLogger(IBlockHandler.class);

	public static String iblockhandler(String name, String sex, int age, BlockException ex) {
		log.info("Oops=============={}", ex.getClass().getName());
		if (ex instanceof AuthorityException) {
			return "系统限流了===IBlockHandler....AuthorityException";
		}
		if (ex instanceof DegradeException) {
			return "系统限流了===IBlockHandler....DegradeException";
		}
		if (ex instanceof FlowException) {
			return "系统限流了===IBlockHandler....FlowException";
		}
		if (ex instanceof ParamFlowException) {
			return "系统限流了===IBlockHandler....ParamFlowException";
		}
		if (ex instanceof SystemBlockException) {
			return "系统限流了===IBlockHandler....SystemBlockException";
		}

		return "系统限流了===IBlockHandler....IBlockHandler 方法" + ex.getClass().getName();
	}
}
