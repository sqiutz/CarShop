package com.keeping.business.web.controller.model;

public class WebResultObject <T> {
	
	public String code;		//返回错误码
	
	public String msg;		//返回错误信息
	
	public T obj;				//返回对象

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

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}
}
