package com.keeping.business.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.dal.dao.UserDao;
import com.keeping.business.dal.model.UserDo;
import com.keeping.business.service.UserService;
import com.keeping.business.web.controller.model.User;

public class UserServiceImpl implements UserService{
	
	/**用户信息DAO */
    private UserDao userDao;

	@Override
	public int checkValidUsername(String username) {
		// TODO Auto-generated method stub
		Integer total = userDao.checkValidUsername(username);
		return total;
	}
    
	@Override
	public List<User> queryAll(User user) {
		// TODO Auto-generated method stub
		List<UserDo> userDoes = userDao.queryAll();
		
		return null;
	}
	
	@Override
	public User login(String username, String passwd)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		UserDo userDo = userDao.queryByUsername(username);
		User user = new User();
		if (null == userDo){
			user.setUserName("test");
		}else{
			user.setUserName(userDo.getUserName());
		}
		
		return user;
	}

	@Override
	public int addUser(User user) throws BusinessServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int resendRegisterConfirmEmail(String email)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean checkUniqueEmail(String email)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int modifyUser(User user) throws BusinessServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User getUser(User user) throws BusinessServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserById(long userId) throws BusinessServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUsersById(List<Long> userIdList)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int modifyHeadImg(User user) throws BusinessServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int modifyPasswd(long userId, String oldPasswd, String newPasswd,
			boolean isResetPwd) throws BusinessServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int retrievePasswd(String email) throws BusinessServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int confirmRegistry(String email, String token)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int retrievePasswdConfirm(String email, String token)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}



}
