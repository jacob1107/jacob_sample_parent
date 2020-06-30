package com.jacob.spring.trycatch.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.jacob.spring.trycatch.bean.R;
import com.jacob.spring.trycatch.ex.GeneraBusinessException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * 全局异常处理
	 * 
	 * @param request
	 * @param response
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ GeneraBusinessException.class })
	public Object exceptionHandler(HttpServletRequest request, HttpServletResponse response,
			GeneraBusinessException e) {
		log.error("[GlobalExceptionHandler][exceptionHandler] exception", e);
		R r = new R(e.getCode(), e.getMessage());
		return r;
	}

	@ExceptionHandler({ NoHandlerFoundException.class })
	public Object noHandlerFoundException(HttpServletRequest request, HttpServletResponse response,
			NoHandlerFoundException e) {
		log.error("[NoHandlerFoundException][noHandlerFoundException] exception", e);
		R r = new R("4xx", e.getMessage());
		return r;
	}
}