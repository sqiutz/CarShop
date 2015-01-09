package com.keeping.business.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.dal.dao.OrderDao;
import com.keeping.business.dal.dao.UserWorkloadDao;
import com.keeping.business.dal.model.OrderDo;
import com.keeping.business.dal.model.UserDo;
import com.keeping.business.dal.model.UserWorkloadDo;
import com.keeping.business.service.OrderService;
import com.keeping.business.service.UserWorkloadService;
import com.keeping.business.service.converter.OrderConverter;
import com.keeping.business.service.converter.UserConverter;
import com.keeping.business.service.converter.UserWorkloadConverter;
import com.keeping.business.web.controller.UserController;
import com.keeping.business.web.controller.model.Order;
import com.keeping.business.web.controller.model.User;
import com.keeping.business.web.controller.model.UserWorkload;

public class UserWorkloadServiceImpl implements UserWorkloadService {

	/** 用户信息DAO */
	private UserWorkloadDao userWorkloadDao;

	/** 日志 */
//	private Logger logger = LoggerFactory.getLogger(UserController.class);

	public List<UserWorkload> queryByUserWorkloadUserid(Integer userId)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		List<UserWorkload> userworkload_front = new ArrayList<UserWorkload>();
		
		UserWorkloadDo input = new UserWorkloadDo();
		input.setUserId(userId);
		
		List<UserWorkloadDo> userworkloadDoes = userWorkloadDao.queryByUserWorkloadUserid(input);

		if (userworkloadDoes == null) {
			return userworkload_front;
		} else {
			for (int i = 0; i < userworkloadDoes.size(); i++) {
				userworkload_front.add(UserWorkloadConverter.getUserWorkload(userworkloadDoes.get(i)));
			}
		}

		return userworkload_front;
	}

	public List<Integer> queryAllUsers(Date createTime) {
		// TODO Auto-generated method stub
		List<Integer> userIds = new ArrayList<Integer>();
		
		UserWorkloadDo input = new UserWorkloadDo();
		input.setCreateTime(createTime);
		
		userIds = userWorkloadDao.queryAllUsers(input);
		
		return userIds;
	}

	public void addUserWorkload(UserWorkload userWorkload) {
		// TODO Auto-generated method stub

		UserWorkloadDo userWorkloadDo = UserWorkloadConverter.getUserWorkloadDo(userWorkload);

		userWorkloadDao.addUserWorkload(userWorkloadDo);
	}

	public void updateUserWorkload(UserWorkload userWorkload) {
		// TODO Auto-generated method stub
		UserWorkloadDo userWorkloadDo = UserWorkloadConverter.getUserWorkloadDo(userWorkload);

		userWorkloadDao.updateUserWorkload(userWorkloadDo);
	}
	
	@Override
	public UserWorkload queryByUserWorkloadQueueid(Integer modifyqueueId) {
		// TODO Auto-generated method stub
		UserWorkload userWorkload = new UserWorkload();
		UserWorkloadDo userWorkloadDo = userWorkloadDao.queryByUserWorkloadQueueid(modifyqueueId);
		
		userWorkload = UserWorkloadConverter.getUserWorkload(userWorkloadDo);
		
		return userWorkload;
	}

	public UserWorkloadDao getUserWorkloadDao() {
		return userWorkloadDao;
	}

	public void setUserWorkloadDao(UserWorkloadDao userWorkloadDao) {
		this.userWorkloadDao = userWorkloadDao;
	}

}
