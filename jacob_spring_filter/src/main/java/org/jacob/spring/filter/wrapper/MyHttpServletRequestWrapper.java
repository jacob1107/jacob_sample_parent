package org.jacob.spring.filter.wrapper;

import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MyHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 缓存流
     */
    private byte[] cacheBody;

    /**
     * 构造方法
     *
     * @param request
     * @throws IOException
     */
    public MyHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    /**
     * 获取缓冲流
     *
     * @return
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream(), StandardCharsets.UTF_8));
    }

    /**
     * 获取流
     *
     * @return
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 1. 初始化缓存
        if (cacheBody == null) {
            cacheBody = StreamUtils.copyToByteArray(super.getInputStream());
        }
        // 3. 从缓存中返回流
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(cacheBody);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }
}
