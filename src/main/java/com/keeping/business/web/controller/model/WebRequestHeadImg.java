package com.keeping.business.web.controller.model;

public class WebRequestHeadImg {
	
	private long userId;			//用户ID

	private String proImgPath;	//Profile用户图标

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getProImgPath() {
		return proImgPath;
	}

	public void setProImgPath(String proImgPath) {
		this.proImgPath = proImgPath;
	}
	
}
