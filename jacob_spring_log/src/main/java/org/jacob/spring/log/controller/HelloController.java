package org.jacob.spring.log.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jacob.spring.log.mdc.thread.MDCRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.ttl.threadpool.TtlExecutors;

@RestController
public class HelloController {

	private static final Logger log = LoggerFactory.getLogger(HelloController.class);
	private ExecutorService executorService = Executors.newFixedThreadPool(5);
	private ExecutorService executors = TtlExecutors.getTtlExecutorService(executorService);

	@RequestMapping("/hello")
	public String hello() {

		log.trace("==============trace=================");
		log.info("===============info================");
		log.error("==============error=================");
		log.debug("==============debug=================");
		log.warn("===============warn================");
		log.info("========{}{}=============", "hello", "jacob");

		try {
			int i = 1 / 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 10; i++) {
			new Thread(execute()).start();
			new Thread(new MDCRunnable(execute2())).start();
			executorService.submit(execute());
			executors.submit(execute());
		}

		return "hello";

	}

	private Runnable execute() {
		return new Runnable() {

			@Override
			public void run() {

				log.info("thread==========原始===========info");
			}
		};
	}

	private Runnable execute2() {
		return new Runnable() {

			@Override
			public void run() {
				log.info("thread==========MDC===========info");
			}
		};
	}

	private Runnable execute3() {
		return new Runnable() {

			@Override
			public void run() {
				log.info("thread==========alibaba===========info");
			}
		};
	}

}
