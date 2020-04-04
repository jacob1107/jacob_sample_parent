package org.jaco.lombok.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.jacob.lombok.*")
public class AppLombokStart {
	public static void main(String[] args) {
		SpringApplication.run(AppLombokStart.class, args);
	}
}
