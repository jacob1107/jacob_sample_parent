package org.jacob.spring.h2.bean;

import javax.persistence.Entity;

import lombok.Data;
import lombok.ToString;

@Entity
@ToString
@Data
public class Product {
	@javax.persistence.Id
	private String Id;
	private String name;
}