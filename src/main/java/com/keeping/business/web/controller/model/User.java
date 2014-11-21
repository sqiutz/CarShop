package com.keeping.business.web.controller.model;

import java.util.Date;

public class User {

	private long userId;			//ID

	private String userName;		//用户名
	
	private int userType;		//用户区分
	
	private int isAdmin;			//管理员标记
	
	private String proImgPath;	//Profile用户图标
	
	private int registerDate;	//注册日期

	private String passwd;
	
	private int isValid;			//是否激活

    private Date createTime;
    
    private Date modifyTime;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getProImgPath() {
		return proImgPath;
	}

	public void setProImgPath(String proImgPath) {
		this.proImgPath = proImgPath;
	}

	public int getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(int registerDate) {
		this.registerDate = registerDate;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public int getIsValid() {
		return isValid;
	}

	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}
