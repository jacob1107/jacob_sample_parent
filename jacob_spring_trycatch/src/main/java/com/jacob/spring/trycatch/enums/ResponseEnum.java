package com.jacob.spring.trycatch.enums;

import com.jacob.spring.trycatch.asserts.BusinessExceptionAssert;

public enum ResponseEnum implements BusinessExceptionAssert {
	// {x}占位符
	CAN_NOT_IS_NULL(1001, "{0}不能为空"),;

	ResponseEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * 异常码
	 */
	private int code;
	/**
	 * 异常消息
	 */
	private String message;

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
