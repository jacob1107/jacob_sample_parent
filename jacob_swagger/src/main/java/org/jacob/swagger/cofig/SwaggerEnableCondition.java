package org.jacob.swagger.cofig;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class SwaggerEnableCondition implements Condition {

	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		String active = context.getEnvironment().getProperty("spring.profiles.active");
		return "dev".equals(active) || "test".equals(active);
	}

}
