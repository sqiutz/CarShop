package com.keeping.business.service;

import java.util.List;

import com.keeping.business.web.controller.model.UserGroup;

public interface UserGroupService {

	public List<UserGroup> queryAll();
	
	public void addGroup(UserGroup userGroup);
	
	public void deleteGroup(Integer id);
	
	public UserGroup queryById(Integer id);
	
	public UserGroup queryByName(String groupName);
	
}
