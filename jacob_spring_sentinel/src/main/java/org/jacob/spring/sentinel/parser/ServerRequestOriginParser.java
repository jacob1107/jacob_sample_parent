package org.jacob.spring.sentinel.parser;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;

@Component
public class ServerRequestOriginParser implements RequestOriginParser {
	/**
	 * 区分来源：本质通过request域获取来源标识 根据不同来源如app/pc，最后返回结果值交给sentinel流控匹配处理
	 */
	@Override
	public String parseOrigin(HttpServletRequest request) {
		String servName = request.getParameter("servName");
		// 这个不是授权规则必须的判断
		if (StringUtils.isEmpty(servName)) {
			throw new RuntimeException("servName不能为空");
		}
		return servName;
	}
}