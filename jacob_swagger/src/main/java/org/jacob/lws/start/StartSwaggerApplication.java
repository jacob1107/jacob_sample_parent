package org.jacob.lws.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.jacob.lws.*")
public class StartSwaggerApplication {
	public static void main(String[] args) {
		SpringApplication.run(StartSwaggerApplication.class, args);
	}
}
