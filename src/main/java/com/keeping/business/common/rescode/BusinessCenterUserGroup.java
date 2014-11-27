package com.keeping.business.common.rescode;

public enum BusinessCenterUserGroup {

	SYS_ADMIN(0, "admin"),
	SYS_BOOKER(1, "booker"),
	SYS_SERVICER(2, "servicer");
	
	private Integer id;
	private String groupName;
	
	private BusinessCenterUserGroup(Integer id, String groupName){
		this.id = id;
		this.groupName = groupName;
	}
	
	public Integer getId(){
		return id;
	}
	
	public String getGroupName(){
		return groupName;
	}
}
