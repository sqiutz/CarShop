package com.keeping.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.common.rescode.BusinessCenterResCode;
import com.keeping.business.dal.dao.UserDao;
import com.keeping.business.dal.model.UserDo;
import com.keeping.business.service.UserService;
import com.keeping.business.service.converter.UserConverter;
import com.keeping.business.web.controller.UserController;
import com.keeping.business.web.controller.model.User;

public class UserServiceImpl implements UserService{
	
	/**日志 */
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	/**用户信息DAO */
    private UserDao userDao;

	public int checkValidUsername(String username) {
		// TODO Auto-generated method stub
		Integer total = userDao.checkValidUsername(username);
		return total;
	}
    
	public List<User> queryAll() {
		List<UserDo> userDoes = userDao.queryAll();
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < userDoes.size(); i++){
			users.add(UserConverter.getUser(userDoes.get(i)));
		}
		return users;
	}
	
	public User login(String username, String passwd) throws BusinessServiceException {
		UserDo userDo = userDao.queryByUsername(username);
		if (userDo == null) {
    		logger.error("用户名或者密码错误！" + BusinessCenterResCode.LOGIN_USER_NOT_EXIST.getMsg());
    		throw new BusinessServiceException(BusinessCenterResCode.LOGIN_USER_NOT_EXIST.getCode(),
    				BusinessCenterResCode.LOGIN_USER_NOT_EXIST.getMsg());
		}
		if ((userDo.getIsValid() == 0)) {
    		logger.error("用户名或者密码错误！" + BusinessCenterResCode.LOGIN_USER_NOT_ALIVE.getMsg());
    		throw new BusinessServiceException(BusinessCenterResCode.LOGIN_USER_NOT_ALIVE.getCode(),
    				BusinessCenterResCode.LOGIN_USER_NOT_ALIVE.getMsg());
		}
    	if (!userDo.getPasswd().equals(passwd)) {//failure
    		System.out.println(passwd);
    		logger.error("用户名或者密码错误！" + BusinessCenterResCode.LOGIN_PASSWORD_NOT_RIGHT.getMsg());
    		throw new BusinessServiceException(BusinessCenterResCode.LOGIN_PASSWORD_NOT_RIGHT.getCode(),
    				BusinessCenterResCode.LOGIN_PASSWORD_NOT_RIGHT.getMsg());
    	}    	
    	return UserConverter.getUser(userDo);
	}

	public void addUser(User user) throws BusinessServiceException {
		if(null == user) {
			return;
		}		
		userDao.addUser(UserConverter.getUserDo(user));
	}
	
	public User queryUserByName(String userName)
			throws BusinessServiceException {
		UserDo userDo = userDao.queryByUsername(userName);
		if(null == userDo) {
			return null;
		}
		return UserConverter.getUser(userDo);
	}
	
	public void modifyUser(User user) throws BusinessServiceException {
		if(null == user) {
			return;
		}		
		userDao.modifyUser(UserConverter.getUserDo(user));
	}

	public int resendRegisterConfirmEmail(String email)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean checkUniqueEmail(String email)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		return false;
	}


	public User getUser(User user) throws BusinessServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public User getUserById(long userId) throws BusinessServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<User> getUsersById(List<Long> userIdList)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public int modifyHeadImg(User user) throws BusinessServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int modifyPasswd(long userId, String oldPasswd, String newPasswd,
			boolean isResetPwd) throws BusinessServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int retrievePasswd(String email) throws BusinessServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int confirmRegistry(String email, String token)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

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
