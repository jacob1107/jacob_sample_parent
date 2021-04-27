package org.jacob.spring.hibernate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.jacob.spring.hibernate.bean.Dog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DogValidatorHandle implements ConstraintValidator<DogValidator, Dog> {

	private static final Logger log = LoggerFactory.getLogger(DogValidatorHandle.class);

	@Override
	public boolean isValid(Dog value, ConstraintValidatorContext context) {
		log.info("自定义校验注解执行==========");
		context.disableDefaultConstraintViolation();
		if (value.getAge() > 0) {
			if ("1".equals(value.getName())) {
				context.buildConstraintViolationWithTemplate("name 内容不对").addConstraintViolation();
				return false;
			}
		}

		return true;
	}

}