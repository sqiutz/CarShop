package com.keeping.business.web.controller.model;

public class WebResult {
	
	private String code;		//返回错误码
	
	private String msg;		//返回错误信息

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
