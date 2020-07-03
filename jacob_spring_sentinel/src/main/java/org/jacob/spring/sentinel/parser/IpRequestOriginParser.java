package org.jacob.spring.sentinel.parser;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;

public class IpRequestOriginParser implements RequestOriginParser {
	@Override
	public String parseOrigin(HttpServletRequest httpServletRequest) {
		return httpServletRequest.getRemoteAddr();
	}
}
