package com.keeping.business.dal.dao;

import java.util.List;

import com.keeping.business.dal.model.UserGroupDo;

public interface UserGroupDao extends BaseDao<UserGroupDo>{
	
	public List<UserGroupDo> queryAll();

}
