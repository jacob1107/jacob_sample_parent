package org.jacob.spring.sentinel.config;

import javax.annotation.PostConstruct;

import org.jacob.spring.sentinel.handler.ComsterUrlBlockHandler;
import org.jacob.spring.sentinel.parser.IpRequestOriginParser;
import org.jacob.spring.sentinel.parser.ServerRequestOriginParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;

@Configuration
public class AopConfiguration {

	@PostConstruct
	public void init() {
		// 黑白名单
		WebCallbackManager.setRequestOriginParser(new IpRequestOriginParser());
		WebCallbackManager.setRequestOriginParser(new ServerRequestOriginParser());
		// 自定义默认跳转页面
		WebCallbackManager.setUrlBlockHandler(new ComsterUrlBlockHandler());
	}

	@Bean
	public SentinelResourceAspect sentinelResourceAspect() {
		return new SentinelResourceAspect();
	}
}
