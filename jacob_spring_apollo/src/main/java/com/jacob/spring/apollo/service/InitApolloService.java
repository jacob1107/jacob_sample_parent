package com.jacob.spring.apollo.service;

import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.jacob.spring.apollo.init.ApolloConfigUtil;

@Component
public class InitApolloService implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(InitApolloService.class);
	private String[] namespaces = { NameSpaces.application.name(), NameSpaces.cat.name(), NameSpaces.dubbo.name() };

	private static Properties properties;

	public static String getValue(String key) {
		String result = properties.getProperty(key);
		logger.info(String.format("Loading key : %s with value: %s", key, result));
		return result;
	}

	@Override
	public void run(String... args) throws Exception {
		properties = ApolloConfigUtil.exec(namespaces);
	}

}
