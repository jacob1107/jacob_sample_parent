package org.jacob.spring.hibernate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AllExecptionHandler {

	private static final Logger log = LoggerFactory.getLogger(AllExecptionHandler.class);

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public String handleException(Exception ex) {
		if (ex instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException e = (MethodArgumentNotValidException) ex;
			FieldError fieldError = e.getBindingResult().getFieldError();
			log.info("异常信息============{}{}", fieldError.getDefaultMessage(), fieldError.getField());

		}
		System.out.println("程序异常：" + ex.toString());
		return "参数异常";
	}
}