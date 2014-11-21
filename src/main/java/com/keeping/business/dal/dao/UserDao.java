package com.keeping.business.dal.dao;

import java.util.List;

import com.keeping.business.dal.model.UserDo;

public interface UserDao extends BaseDao<UserDo>{
	
	public List<UserDo> queryAll();
	/**
     * 查询用户是否存在
     * 
     * @param String
     * @return Integer
     */
	public Integer checkUniqueUsername(String username);

	/**
     * 查询用户是否有效激活
     * 
     * @param String
     * @return Integer
     */
	public Integer checkValidUsername(String username);
	
	/**
     * 根据用户部分信息查询用户信息
     * 
     * @param UserDO
     * @return UserDO
     */
	public UserDo query(UserDo userDo);

	/**
     * 查询用户信息
     * 
     * @param List<Long>
     * @return List<UserDO>
     */
	public List<UserDo> queryByUsersId(List<Long> userIdList);
	
	/**
     * 根据用户email查询用户信息
     * 
     * @param String
     * @return UserDO
     */
	public UserDo queryByUsername(String userName);

	/**
     * 根据用户email查询用户密码
     * 
     * @param Long
     * @return String
     */
	public String queryPasswd(long id);

	/**
     * 修改用户头像
     * 
     * @param UserDO
     * @return Integer
     */
	public int modifyHeadImg(UserDo userDo);
	
	/**
     * 修改用户密码
     * 
     * @param UserDO
     * @return Integer
     */
	public int modifyPasswdById(UserDo userDo);


	/**
     * 修改用户密码
     * 
     * @param UserDO
     * @return Integer
     */
	public int modifyPasswdByUsername(UserDo userDo);
}
