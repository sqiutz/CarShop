package com.keeping.business.service;


import java.util.Date;
import java.util.List;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.dal.model.UserWorkloadDo;
import com.keeping.business.web.controller.model.Order;
import com.keeping.business.web.controller.model.User;
import com.keeping.business.web.controller.model.UserWorkload;

public interface UserWorkloadService {

    public List<UserWorkload> queryByUserWorkloadUserid(Integer userId) throws BusinessServiceException;
	
	public List<Integer> queryAllUsers(Date createTime);
	
	public void addUserWorkload(UserWorkload userWorkload);
	
	public void updateUserWorkload(UserWorkload userWorkload);
}
