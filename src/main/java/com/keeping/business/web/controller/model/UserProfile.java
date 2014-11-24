package com.keeping.business.web.controller.model;

public class UserProfile {

	private long id;			//用户ID
	
	private String userName;		//username
	
	private int isAdmin;			//是否管理员
	
	private String proImgPath;	//Profile用户图标

	private String passwd;
	
	private Integer isValid;
	
	private Integer groupId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
}
