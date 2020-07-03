package org.jacob.spring.sentinel.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;

public class ComsterUrlBlockHandler implements UrlBlockHandler {

	@Override
	public void blocked(HttpServletRequest request, HttpServletResponse response, BlockException ex)
			throws IOException {
		String msg = "";
		if (ex instanceof AuthorityException) {
			msg = "系统限流了UrlBlockHandler....黑名单";
		}
		if (ex instanceof DegradeException) {
			msg = "系统限流了UrlBlockHandler....Degrade";
		}
		if (ex instanceof FlowException) {
			msg = "系统限流了UrlBlockHandler....Flow";
		}
		if (ex instanceof ParamFlowException) {
			msg = "系统限流了UrlBlockHandler....ParamFlow";
		}
		if (ex instanceof SystemBlockException) {
			msg = "系统限流了UrlBlockHandler....SYSTEM";
		}
		response.setContentType("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.print(msg);
		printWriter.close();

	}

}
