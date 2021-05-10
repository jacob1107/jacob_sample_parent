package org.jacob.spring.hibernate.bean;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author jacob
 *
 */
@Data
public class Man {

	@NotNull(message = "name 不能为空")
	private String name;

	@Override
	public String toString() {
		return "Man [name=" + name + "]";
	}

}
