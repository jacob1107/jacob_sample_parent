package org.jacob.spring.hibernate.bean;

import org.jacob.spring.hibernate.validator.DogValidator;

import lombok.Data;

@Data
@DogValidator
public class Dog {

	private String name = "1";
	private String sex;
	private int age = 3;

	@Override
	public String toString() {
		return "Dog [name=" + name + ", sex=" + sex + ", age=" + age + "]";
	}

}
