package org.jacob.spring.hibernate.bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.jacob.spring.hibernate.enums.YesNoEnum;
import org.jacob.spring.hibernate.validator.EnumValidator;

import lombok.Data;

@Data
public class Bean {

	@EnumValidator(value = YesNoEnum.class, message = "是否可转定可选值:1是2否")
	private Integer payable;

	// 嵌套验证 只能用@Valid
	@Valid
	// @NotNull
	private Man man;

	@Valid
	@NotNull
	private Dog dog;

	public static void main(String[] args) {

		Man man = new Man();
		man.setName("账单");
		Bean bean = new Bean();
		bean.setMan(man);
		bean.setPayable(1);

	}

	@Override
	public String toString() {
		return "Bean [payable=" + payable + ", man=" + man + ", dog=" + dog + "]";
	}
}
