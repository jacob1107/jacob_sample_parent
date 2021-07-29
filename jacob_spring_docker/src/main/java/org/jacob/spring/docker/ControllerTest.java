package org.jacob.spring.docker;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerTest {

	@RequestMapping(path = "docker")
	public String hello() {
		return UUID.randomUUID().toString();
	}

}
