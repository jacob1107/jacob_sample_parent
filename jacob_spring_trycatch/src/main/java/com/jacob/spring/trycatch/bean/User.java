package com.jacob.spring.trycatch.bean;

public class User {

	private int age;
	private String username;

	public User(int age, String username) {
		super();
		this.age = age;
		this.username = username;
	}

	@Override
	public String toString() {
		return "User [age=" + age + ", username=" + username + "]";
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
