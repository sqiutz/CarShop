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
//	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	/**用户信息DAO */
    private UserDao userDao;
    
	@Override
	public Integer queryUserCountByGroupAndBook(User user) {
		// TODO Auto-generated method stub
		
		UserDo userDo = new UserDo();
		userDo.setIsBooker(user.getIsBooker());
		userDo.setCounter(user.getCounter());
		userDo.setGroupId(user.getGroupId());
		
		Integer total = userDao.queryUserCountByGroupAndBook(userDo);
		
		return total;
	}

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
	
	public List<User> queryAllByGroup(Integer groupId) throws BusinessServiceException {
		// TODO Auto-generated method stub
		List<UserDo> userDoes = userDao.queryAllByGroup(groupId);
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < userDoes.size(); i++){
			users.add(UserConverter.getUser(userDoes.get(i)));
		}
		return users;
	}
	
	public User login(String username, String passwd) throws BusinessServiceException {
		UserDo userDo = userDao.queryByUsername(username);
		if (userDo == null) {
//    		logger.error("用户名或者密码错误！" + BusinessCenterResCode.LOGIN_USER_NOT_EXIST.getMsg());
    		throw new BusinessServiceException(BusinessCenterResCode.LOGIN_USER_NOT_EXIST.getCode(),
    				BusinessCenterResCode.LOGIN_USER_NOT_EXIST.getMsg());
		}
		if ((userDo.getIsValid() == 0)) {
//    		logger.error("用户名或者密码错误！" + BusinessCenterResCode.LOGIN_USER_NOT_ALIVE.getMsg());
    		throw new BusinessServiceException(BusinessCenterResCode.LOGIN_USER_NOT_ALIVE.getCode(),
    				BusinessCenterResCode.LOGIN_USER_NOT_ALIVE.getMsg());
		}
    	if (!userDo.getPasswd().equals(passwd)) {//failure
    		System.out.println(passwd);
//    		logger.error("用户名或者密码错误！" + BusinessCenterResCode.LOGIN_PASSWORD_NOT_RIGHT.getMsg());
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
	
	public void deleteUser(User user) throws BusinessServiceException {
		// TODO Auto-generated method stub
		if(null == user || user.getId() == null){
			return;
		}
		
		userDao.deleteUser(user.getId());
	}
	
	public User queryUserByName(String userName)
			throws BusinessServiceException {
		UserDo userDo = userDao.queryByUsername(userName);
		if(null == userDo) {
			return null;
		}
		return UserConverter.getUser(userDo);
	}
	

	public User queryUserByCounter(String counter)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		UserDo userDo = userDao.queryByCounter(counter);
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

	public List<User> getByUsersId(List<Integer> userIdList) throws BusinessServiceException {
		// TODO Auto-generated method stub
		List<UserDo> userDoes = userDao.queryByUsersId(userIdList);
		
		List<User> users = new ArrayList<User>();
		
		for (int i=0; i<userDoes.size(); i++){
			users.add(UserConverter.getUser(userDoes.get(i)));
		}
		
		return users;
	}

	public User getByUserId(Integer id) throws BusinessServiceException {
		// TODO Auto-generated method stub
		UserDo userDo = userDao.queryByUserId(id);
		
		User user = null;
		
		if (userDo == null){
			return new User();
		}
		
		user = UserConverter.getUser(userDo);
		
		return user;
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
