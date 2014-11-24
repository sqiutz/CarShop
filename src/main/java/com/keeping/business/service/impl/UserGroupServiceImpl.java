package com.keeping.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.keeping.business.dal.dao.UserGroupDao;
import com.keeping.business.dal.model.UserGroupDo;
import com.keeping.business.service.UserGroupService;
import com.keeping.business.web.controller.model.UserGroup;

public class UserGroupServiceImpl implements UserGroupService{
	
	/**用户信息DAO */
    private UserGroupDao userGroupDao;

	public List<UserGroup> queryAll() {
		// TODO Auto-generated method stub
		List<UserGroupDo> userGroupDoes = userGroupDao.queryAll();
		
		List<UserGroup> userGroups = new ArrayList<UserGroup>();
		
		for (int i = 0; i < userGroupDoes.size(); i++){
			userGroups.get(i).setId(userGroupDoes.get(i).getId());
			userGroups.get(i).setGroupName(userGroupDoes.get(i).getGroupName());
			userGroups.get(i).setComment(userGroupDoes.get(i).getComment());
		}
		
		return userGroups;
	}


	public UserGroupDao getUserGroupDao() {
		return userGroupDao;
	}

	public void setUserGroupDao(UserGroupDao userGroupDao) {
		this.userGroupDao = userGroupDao;
	}

}
