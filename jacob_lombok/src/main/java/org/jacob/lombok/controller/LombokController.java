package org.jacob.lombok.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class LombokController {

	@RequestMapping("/lombok")
	public String postTest() {

		return "hello Lombok!";
	}

}
