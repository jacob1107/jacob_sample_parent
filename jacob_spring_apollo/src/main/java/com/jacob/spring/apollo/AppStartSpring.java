package com.jacob.spring.apollo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

@SpringBootApplication
@EnableApolloConfig
public class AppStartSpring {
	public static void main(String[] args) {
		SpringApplication.run(AppStartSpring.class, args);
	}
}
