package com.keeping.business.web.controller.model;

import java.util.List;

public class UserWorkloadList {
	
	private Integer userId;
	
	private String userName;
	
	private Float totalLoad;
	
	private Integer totalLoadPercentage;
	
	private List<UserWorkload> userWorkloadList;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<UserWorkload> getUserWorkloadList() {
		return userWorkloadList;
	}

	public void setUserWorkloadList(List<UserWorkload> userWorkloadList) {
		this.userWorkloadList = userWorkloadList;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Float getTotalLoad() {
		return totalLoad;
	}

	public void setTotalLoad(Float totalLoad) {
		this.totalLoad = totalLoad;
	}

	public Integer getTotalLoadPercentage() {
		return totalLoadPercentage;
	}

	public void setTotalLoadPercentage(Integer totalLoadPercentage) {
		this.totalLoadPercentage = totalLoadPercentage;
	}

}
