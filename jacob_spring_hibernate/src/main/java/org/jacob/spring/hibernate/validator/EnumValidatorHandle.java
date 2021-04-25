package org.jacob.spring.hibernate.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnumValidatorHandle implements ConstraintValidator<EnumValidator, Object>, Annotation {
	private List<Object> values = new ArrayList<>();

	@Override
	public void initialize(EnumValidator enumValidator) {
		Class<?> clz = enumValidator.value();
		Object[] objects = clz.getEnumConstants();
		try {
			Method method = clz.getMethod(enumValidator.method());
			if (Objects.isNull(method)) {

				throw new Exception(String.format("枚举对象%s缺少名为%s的方法", clz.getName(), enumValidator.method()));
			}
			Object value;
			for (Object obj : objects) {
				value = method.invoke(obj);
				values.add(value);
			}
		} catch (Exception e) {
			log.error("处理枚举校验异常:{}", e);
		}
	}

	@Override
	public Class<? extends Annotation> annotationType() {
		return null;
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
		if (value instanceof String) {
			String valueStr = (String) value;
			return StringUtils.isEmpty(valueStr) || values.contains(value);
		}
		return Objects.isNull(value) || values.contains(value);
	}
}