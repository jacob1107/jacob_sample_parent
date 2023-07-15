package org.jacob.spring.filter.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jacob.spring.filter.wrapper.BodyRequestWrapper;
import org.jacob.spring.filter.wrapper.ParameterRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * json 转from 拦截器
 */
//@Order(3)
//@WebFilter(filterName = "jsontofomFilter", urlPatterns = "/*")
public class JsonToFomFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(JsonToFomFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("JsonToFomFilter==========exec========{}", System.currentTimeMillis());
        HttpServletRequest request = (HttpServletRequest) req;

        String contentType = request.getContentType();
        //body形式（json）
        if (contentType.equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                || contentType.equals(MediaType.APPLICATION_JSON_VALUE)) {
            //获取request的body参数
            String content = getBody(request);
            //如果body中存在数据放入HttpServletRequest
            if (StringUtils.isNotEmpty(content)) {
                //参数中放入新的参数
                Map<String, String[]> map = (Map<String, String[]>) JSON.parseObject(content, Map.class);
               /* Map<String, String[]> parameterMap = new HashMap<>();
                Set set = map.keySet();
                for (Object o : set) {
                    parameterMap.put(String.valueOf(o), new String[]{String.valueOf(map.get(o))});
                }*/
                //将参数放入重写的方法中
                request = new ParameterRequestWrapper(request, map);
            }
        }
        chain.doFilter(request, response);
    }

    //获取Request的body数据
    private String getBody(ServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public void destroy() {

    }
}

