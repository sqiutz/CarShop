package com.keeping.business.dal.dao;

import java.util.List;

import com.keeping.business.dal.model.UserGroupDo;

public interface UserGroupDao extends BaseDao<UserGroupDo>{
	
	public List<UserGroupDo> queryAll();
	
	public void insertGroup(UserGroupDo userGroupDp);
	
	public void deleteGroup(Integer id);
	
	public UserGroupDo queryById(Integer id);
	
	public UserGroupDo queryByName(String groupName);

}
