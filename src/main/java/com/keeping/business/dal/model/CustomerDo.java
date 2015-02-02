package com.keeping.business.dal.model;

import java.sql.Timestamp;

public class CustomerDo {
	
	private Integer id;

	private String userName;		//用户名
    
	private String passwd;
	
	private String mobilePhone;
	
	private String policeNum;

    private Timestamp createTime;
    
    private Timestamp modifyTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getPoliceNum() {
		return policeNum;
	}

	public void setPoliceNum(String policeNum) {
		this.policeNum = policeNum;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

}
