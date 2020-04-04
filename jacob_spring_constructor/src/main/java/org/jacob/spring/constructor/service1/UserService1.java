package org.jacob.spring.constructor.service1;

import org.jacob.spring.constructor.service2.UserService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService1 {
	@Autowired
	private UserService2 service2;

	public void service() {
		service2.service();
		System.err.println("org.jacob.spring.constructor.service1");
	}

}
