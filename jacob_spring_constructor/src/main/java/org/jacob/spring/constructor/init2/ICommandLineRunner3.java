package org.jacob.spring.constructor.init2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 3)
public class ICommandLineRunner3 implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(ICommandLineRunner3.class);

	public void run(String... args) throws Exception {
		log.info("=========init 执行了================{}", this.getClass().getName());
	}

}
