package org.jacob.spring.constructor.init1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
public class IApplicationRunner1 implements ApplicationRunner {

	private static final Logger log = LoggerFactory.getLogger(IApplicationRunner1.class);

	public void run(ApplicationArguments args) throws Exception {

		log.info("=========init 执行了================{}", this.getClass().getName());

	}

}
