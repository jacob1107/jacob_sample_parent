package com.jacob.spring.trycatch.bean;

public class R {

	private String code;
	private String msg;

	public R(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "R [code=" + code + ", msg=" + msg + "]";
	}

	public R() {
		super();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
