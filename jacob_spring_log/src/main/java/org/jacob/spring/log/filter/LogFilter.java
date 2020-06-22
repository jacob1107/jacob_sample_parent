package org.jacob.spring.log.filter;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class LogFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		MDC.put("uuid", uuid);
		chain.doFilter(request, response);
		MDC.clear();

	}

}
