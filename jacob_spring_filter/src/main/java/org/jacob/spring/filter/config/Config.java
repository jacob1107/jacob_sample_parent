package org.jacob.spring.filter.config;

import org.jacob.spring.filter.filter.EncryptionFilter;
import org.jacob.spring.filter.filter.JsonToFomFilter;
import org.jacob.spring.filter.filter.MyHttpServletRequestWrapperFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Order 值越小越先执行
 *
 * @Author jacob
 * @Date 2023/7/15 14:26
 * @Version 1.0
 */
@Configuration
public class Config {
    @Bean
    public FilterRegistrationBean registerEncryptionFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean<>();
        bean.setOrder(2);
        bean.setFilter(new EncryptionFilter());
        // 匹配所有url
        bean.addUrlPatterns("/*");
        return bean;
    }

    @Bean
    public FilterRegistrationBean registerJsonToFomFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean<>();
        bean.setOrder(3);
        bean.setFilter(new JsonToFomFilter());
        // 匹配所有url
        bean.addUrlPatterns("/*");
        return bean;
    }

    @Bean
    public FilterRegistrationBean registerMyHttpServletRequestWrapperFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean<>();
        bean.setOrder(0);
        bean.setFilter(new MyHttpServletRequestWrapperFilter());
        // 匹配所有url
        bean.addUrlPatterns("/*");
        return bean;
    }
}
