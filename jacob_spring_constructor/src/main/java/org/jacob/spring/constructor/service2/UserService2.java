package org.jacob.spring.constructor.service2;

import javax.annotation.Resource;

import org.jacob.spring.constructor.service3.UserService3;
import org.jacob.spring.constructor.service4.UserService4;
import org.springframework.stereotype.Service;

@Service
public class UserService2 {

	@Resource
	private UserService3 service3;
	@Resource
	private UserService4 service4;

	public UserService2(UserService3 service3, UserService4 service4) {
		this.service3 = service3;
		this.service4 = service4;
	}

	public void service() {
		service3.service();
		service4.service();
		System.err.println("org.jacob.spring.constructor.service2");
	}

}
