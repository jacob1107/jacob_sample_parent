package org.jacob.spring.filter.filter;

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
import java.util.HashMap;
import java.util.Map;

/**
 * 添加参数拦截器
 * json.put("encryptionfilter", "json");
 * parameterMap.put("encryptionfilter", new String[]{"from"});
 */

//@Order(5)
//@WebFilter(filterName = "encryptionFilter", urlPatterns = "/*")
public class EncryptionFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(EncryptionFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        logger.info("EncryptionFilter==========exec========{}", System.currentTimeMillis());
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
                JSONObject json = JSONObject.parseObject(content);
                json.put("encryptionfilter", "json");
                content = json.toJSONString();
                //将参数放入重写的方法中
                request = new BodyRequestWrapper(request, content);
            }
            //form表单形式
        } else if ((contentType.equals(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                || contentType.contains(MediaType.MULTIPART_FORM_DATA_VALUE))
                && !request.getParameterMap().isEmpty()) {
            //重新添加几个数据
            Map<String, String[]> parameterMap = new HashMap<>();
            parameterMap.put("encryptionfilter", new String[]{"from"});
            //将参数放入重写的方法中
            request = new ParameterRequestWrapper(request, parameterMap);
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

        } finally {
            if (null != inputStream) {
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

