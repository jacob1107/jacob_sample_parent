package org.jaco.lombok.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
//@EqualsAndHashCode(callSuper = true)
public class User {
	
	private String name;
	private String sex;
	/*
	 * public String getName() { return name; } public void setName(String name) {
	 * this.name = name; } public String getSex() { return sex; } public void
	 * setSex(String sex) { this.sex = sex; }
	 */

}
