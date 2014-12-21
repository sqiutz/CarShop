package com.keeping.business.dal.dao;

import java.util.List;

import com.keeping.business.dal.model.OrderDo;
import com.keeping.business.dal.model.UserDo;
import com.keeping.business.dal.model.UserWorkloadDo;

public interface UserWorkloadDao extends BaseDao<OrderDo>{

	public List<UserWorkloadDo> queryByUserWorkloadUserid(UserWorkloadDo userWorkloadDo);
	
	public List<Integer> queryAllUsers(UserWorkloadDo userWorkloadDo);
	
	public void addUserWorkload(UserWorkloadDo userWorkloadDo);
	
	public void updateUserWorkload(UserWorkloadDo userWorkloadDo);
	
}
