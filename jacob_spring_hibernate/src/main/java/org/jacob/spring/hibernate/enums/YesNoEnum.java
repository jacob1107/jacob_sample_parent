package org.jacob.spring.hibernate.enums;

public enum YesNoEnum {
	YES(1, "是"), NO(2, "否");

	private Integer code;
	private String msg;

	YesNoEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
