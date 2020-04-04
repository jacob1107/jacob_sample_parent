package org.jacob.spring.constructor.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.jacob.spring.constructor.*")
public class StartConstructorApplication {
	public static void main(String[] args) {
		SpringApplication.run(StartConstructorApplication.class, args);
	}
}
