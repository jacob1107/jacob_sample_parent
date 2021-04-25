package org.jacob.spring.hibernate.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author tlj
 * @date 2019/7/9
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Constraint(validatedBy = EnumValidatorHandle.class)
public @interface EnumValidator {
	Class<?> value();

	String message() default "入参值不在正确枚举中";

	String method() default "getCode";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
