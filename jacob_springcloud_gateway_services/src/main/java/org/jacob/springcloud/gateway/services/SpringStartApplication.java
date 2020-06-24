package org.jacob.springcloud.gateway.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SpringStartApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringStartApplication.class, args);
	}
}
