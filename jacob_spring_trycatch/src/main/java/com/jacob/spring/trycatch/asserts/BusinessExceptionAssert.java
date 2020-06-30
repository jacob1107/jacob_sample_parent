package com.jacob.spring.trycatch.asserts;

import java.text.MessageFormat;

import org.springframework.util.StringUtils;

import com.jacob.spring.trycatch.bean.User;
import com.jacob.spring.trycatch.enums.IResponseEnum;
import com.jacob.spring.trycatch.ex.GeneraBusinessException;

public interface BusinessExceptionAssert extends IResponseEnum, BaseAssert {
	// jdk1.8特性：
	// （1）实现类不需要自己再重写default方法，会自动继承该方法 。按照1.8之前的，接口里的每个方法，实现类都是需要重写的。
	// (2)同时带来的多继承中的一个二义性问题：如果一个类实现了两个接口（可以看做是“多继承”），这两个接口又同时都包含了一个名字相同的default方法，那么会发生什么情况？
	// 在这样的情况下，编译器会报错。
	default GeneraBusinessException newException(Object... args) {
		// getMessage() 调用的是 IResponseEnum
		String msg = MessageFormat.format(this.getMessage(), args);
		return new GeneraBusinessException(this.getCode(), msg);
	}

	default GeneraBusinessException newException(Throwable throwable, Object... args) {
		// getMessage() 调用的是 IResponseEnum
		String msg = MessageFormat.format(this.getMessage(), args);
		return new GeneraBusinessException(throwable, this.getCode(), msg);
	}

	// 这里可以进行拓展, 更具体化的业务异常判断
	default void assertUserIsValid(User user) throws GeneraBusinessException {
		if (user.getAge() <= 0)
			throw newException("age");

		if (StringUtils.isEmpty(user.getUsername()))
			throw newException("username");

	}

	// 每个assert方法都是对应每个ResponseEnum 的逻辑判断，通过这种方式，主要是为了减少业务代码里的很多if(和枚举相关的判断)
	// 所以，枚举类型越多，这里的assert方法也就越多
}
