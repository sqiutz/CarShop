package com.keeping.business.web.controller.model;

import java.util.List;

public class WebResultList <T>{
	
	private String code;		//返回错误码
	
	private String msg;		//返回错误信息
	
	private List<T> resList;	//返回数据列表

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

	public List<T> getResList() {
		return resList;
	}

	public void setResList(List<T> resList) {
		this.resList = resList;
	}
	
}
