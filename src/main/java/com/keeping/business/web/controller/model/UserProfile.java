package com.keeping.business.web.controller.model;

public class UserProfile {

	private Integer id;			//用户ID
	
	private String userName;		//username
	
	private Integer isAdmin;			//是否管理员
	
	private String proImgPath;	//Profile用户图标

	private String passwd;
	
	private Integer isValid;
	
	private Integer groupId;
	
	private String counter;
	
	private String groupName;
	
	private Integer isBooker;

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCounter() {
		return counter;
	}

	public void setCounter(String counter) {
		this.counter = counter;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getIsBooker() {
		return isBooker;
	}

	public void setIsBooker(Integer isBooker) {
		this.isBooker = isBooker;
	}
}
