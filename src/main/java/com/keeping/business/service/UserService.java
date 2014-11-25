package com.keeping.business.service;

import java.util.List;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.dal.dao.UserDao;
import com.keeping.business.web.controller.model.User;

public interface UserService {

	public List<User> queryAll();
	
	public int checkValidUsername(String username);
	
	  /**
     * 用户登录
     * 
     * @param username
     * @param passwd
     * @return User
     * @throws Exception
     */
	public User login(String username, String passwd) throws BusinessServiceException;
	
	/**
     * 新建用户
     * 
     * @param UserRegistry
     * @return Integer
     */
    public int addUser(User user) throws BusinessServiceException;

    /**
     * 重发注册确认信
     * 
     * @param String
     * @return Integer
     * @throws BusinessServiceException
     */
    public int resendRegisterConfirmEmail(String email) throws BusinessServiceException;
    
	/**
     * 验证邮箱
     * 
     * @param String
     * @return boolean
     */
    public boolean checkUniqueEmail(String email) throws BusinessServiceException;
    
	/**
     * 修改用户信息
     * 
     * @param User
     * @return Integer
     */
    public int modifyUser(final User user) throws BusinessServiceException;

	/**
     * 根据用户信息查询用户信息
     * 
     * @param User
     * @return User
     */
    public User getUser(User user) throws BusinessServiceException;

	/**
     * 根据用户ID查询用户信息
     * 
     * @param userId
     * @return User
     */
    public User getUserById(long userId) throws BusinessServiceException;

	/**
     * 根据用户ID查询用户信息
     * 
     * @param List<Long>
     * @return List<User>
     */
    public List<User> getUsersById(List<Long> userIdList) throws BusinessServiceException;

	/**
     * 修改用户头像
     * 
     * @param User
     * @return Integer
     */
    public int modifyHeadImg(final User user) throws BusinessServiceException;

	/**
     * 修改用户密码
     * 
     * @param userId
     * @param oldPasswd
     * @param newPasswd
     * @param isResetPwd
     * @return Integer
     */
    public int modifyPasswd(long userId, String oldPasswd, 
    		String newPasswd, boolean isResetPwd) throws BusinessServiceException;

	/**
     * 找回密码
     * 
     * @param email
     * @return Integer
     */
    public int retrievePasswd(String email) throws BusinessServiceException;

	/**
     * 确认注册信息
     * 
     * @param String email
     * @param String token
     * @return Integer
     */
    public int confirmRegistry(String email, String token) throws BusinessServiceException;

	/**
     * 找回密码确认
     * 
     * @param email
     * @param token
     * @return Integer
     */
    public int retrievePasswdConfirm(String email, String token) throws BusinessServiceException;
}
