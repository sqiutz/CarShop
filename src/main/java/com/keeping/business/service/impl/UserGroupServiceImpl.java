package com.keeping.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.keeping.business.dal.dao.UserGroupDao;
import com.keeping.business.dal.model.UserGroupDo;
import com.keeping.business.service.UserGroupService;
import com.keeping.business.service.converter.UserGroupConverter;
import com.keeping.business.web.controller.model.UserGroup;

public class UserGroupServiceImpl implements UserGroupService{
	
	/**用户信息DAO */
    private UserGroupDao userGroupDao;
    
	public UserGroup queryById(Integer id) {
		// TODO Auto-generated method stub
		UserGroupDo userGroupDo = userGroupDao.queryById(id);
		UserGroup userGroup = new UserGroup();;
		
		if(userGroupDo == null){
			return userGroup;
		}
		
		return UserGroupConverter.getUserGroup(userGroupDo);	
	}
	
	public UserGroup queryByName(String groupName) {
		// TODO Auto-generated method stub
		UserGroupDo userGroupDo = userGroupDao.queryByName(groupName);
		UserGroup userGroup = new UserGroup();;
		
		if(userGroupDo == null){
			return userGroup;
		}
		
		return UserGroupConverter.getUserGroup(userGroupDo);	
	}

	public List<UserGroup> queryAll() {
		// TODO Auto-generated method stub
		List<UserGroupDo> userGroupDoes = userGroupDao.queryAll();
		
		List<UserGroup> userGroups = new ArrayList<UserGroup>();
		
		for (int i = 0; i < userGroupDoes.size(); i++){
			UserGroup userGroup = new UserGroup();
			userGroup.setId(userGroupDoes.get(i).getId());
			userGroup.setGroupName(userGroupDoes.get(i).getGroupName());
			userGroup.setComment(userGroupDoes.get(i).getComment());
			userGroups.add(userGroup);
		}
		
		return userGroups;
	}
	
	public void addGroup(UserGroup userGroup) {
		// TODO Auto-generated method stub
		UserGroupDo userGroupDo = new UserGroupDo();
		
		userGroupDo.setGroupName(userGroup.getGroupName());
		userGroupDo.setComment(userGroup.getComment());
		
		userGroupDao.insertGroup(userGroupDo);
	}


	public void deleteGroup(Integer id) {
		// TODO Auto-generated method stub
		userGroupDao.deleteGroup(id);
	}


	public UserGroupDao getUserGroupDao() {
		return userGroupDao;
	}

	public void setUserGroupDao(UserGroupDao userGroupDao) {
		this.userGroupDao = userGroupDao;
	}

}
