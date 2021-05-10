package org.jacob.spring.hibernate;

import org.jacob.spring.hibernate.bean.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@SpringBootApplication
public class HelloController {

	/**
	 * 
	 * @NotEmpty 用在集合类上面
	 * 
	 * @NotBlank 用在String上面
	 * 
	 * @NotNull 用在基本类型上
	 * 
	 */
	@RequestMapping("/hello")
	public String postTest(@RequestBody @Validated Bean bean) {
		log.info("info======={}", bean);

		return "hello Lombok!";
	}

	public static void main(String[] args) {

		SpringApplication.run(HelloController.class, args);
	}

}
