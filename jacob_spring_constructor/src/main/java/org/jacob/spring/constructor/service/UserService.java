package org.jacob.spring.constructor.service;

import javax.annotation.Resource;

import org.jacob.spring.constructor.service1.UserService1;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Resource
	private UserService1 service1;

	public void service() {
		service1.service();
		System.err.println("org.jacob.spring.constructor.service3");
	}

}
