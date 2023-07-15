package org.jacob.spring.filter.filter;

import org.jacob.spring.filter.wrapper.MyHttpServletRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 所有数据的拦截器
 *
 */
//@Order(2)
//@WebFilter(filterName = "myhttpservletrequestwrapperFilter", urlPatterns = "/*")
public class MyHttpServletRequestWrapperFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(MyHttpServletRequestWrapperFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 对 request 进行包装
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        MyHttpServletRequestWrapper requestWrapper = new MyHttpServletRequestWrapper((HttpServletRequest) request);
        logger.info("MyHttpServletRequestWrapperFilter==========exec========{}",System.currentTimeMillis());
        chain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {

    }

} 
