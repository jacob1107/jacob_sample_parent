package com.jacob.spring.trycatch.ex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneraBusinessException extends Exception {
	private static final long serialVersionUID = -7973854042254266166L;
	private static final Logger log = LoggerFactory.getLogger(GeneraBusinessException.class);

	private String code;
	private String message;

	public GeneraBusinessException(int code, String message) {
		log.info(message);
		this.code = String.valueOf(code);
		this.message = message;

	}

	public GeneraBusinessException(Throwable throwable, int code, String message) {
		super(throwable);
		log.info(message);
		this.code = String.valueOf(code);
		this.message = message;

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
