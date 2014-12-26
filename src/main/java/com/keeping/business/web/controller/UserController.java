package com.keeping.business.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.common.rescode.BusinessCenterResCode;
import com.keeping.business.common.util.PlatformPar;
import com.keeping.business.common.util.PlatfromConstants;
import com.keeping.business.common.util.StringUtil;
import com.keeping.business.service.UserGroupService;
import com.keeping.business.service.UserService;
import com.keeping.business.web.controller.converter.JsonConverter;
import com.keeping.business.web.controller.converter.WebUserConverter;
import com.keeping.business.web.controller.model.CounterObject;
import com.keeping.business.web.controller.model.LoginReq;
import com.keeping.business.web.controller.model.User;
import com.keeping.business.web.controller.model.UserGroup;
import com.keeping.business.web.controller.model.UserProfile;
import com.keeping.business.web.controller.model.WebResult;
import com.keeping.business.web.controller.model.WebResultList;
import com.keeping.business.web.controller.model.WebResultObject;

@Controller
@RequestMapping("/user.do")
public class UserController {

	/** 日志 */
	private Logger logger = LoggerFactory.getLogger(UserController.class);

	/** 用户信息Service */
	@Resource
	private UserService userService;

	@Resource
	private UserGroupService userGroupService;

	@RequestMapping(params = "action=login")
	@ResponseBody
	public WebResultObject<UserProfile> login(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		UserProfile userProfile = new UserProfile();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);
		response.setHeader("Access-Control-Allow-Origin", "*");

		try {
			// 验证请求参数
			String jsonStr = request.getParameter("param");
			LoginReq req = JsonConverter.getFromJsonString(jsonStr,
					LoginReq.class);

			if (StringUtil.isNull(jsonStr) || req == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< UserController.login() > 登录请求信息不正确." + jsonStr);
			} else {
				userProfile = WebUserConverter.getUserProfile(userService
						.login(req.getUsername(), req.getPasswd()));
				UserGroup userGroup = userGroupService.queryById(userProfile.getGroupId());
				userProfile.setGroupName(userGroup.getGroupName());
				userProfile.setIsBooker(0);
				
				// 将用户信息保存在session中
				session.setAttribute(PlatfromConstants.STR_USER_PROFILE,
						userProfile);
				UserProfile u = (UserProfile)session.getAttribute(PlatfromConstants.STR_USER_PROFILE);
				System.out.println(null == u ? "u is null" : u.getUserName());
			}
		} catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		} catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
			logger.error("< UserController.login() > 登录错误." + e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultObject(code, msg, userProfile);
		} catch (Exception e) {
			session.removeAttribute(PlatfromConstants.STR_USER_PROFILE);
			session.invalidate();
			logger.error("< UserController.login() > 登录返回出错." + e.getMessage());
			throw e;
		}

	}
	
	@RequestMapping(params = "action=logout")
	@ResponseBody
	public WebResult logout(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		UserProfile userProfile = new UserProfile();
		HttpSession session = request.getSession();
		response.setHeader("Access-Control-Allow-Origin", "*");

		try {
			
			UserProfile u = (UserProfile)session.getAttribute(PlatfromConstants.STR_USER_PROFILE);
			
			User user = userService.queryUserByName(u.getUserName());
			user.setCounter(null);
			userService.modifyUser(user);
			
			session.removeAttribute(PlatfromConstants.STR_USER_PROFILE);
			session.invalidate();

		} catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		} catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
			logger.error("< UserController.login() > 登录错误." + e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
			session.removeAttribute(PlatfromConstants.STR_USER_PROFILE);
			session.invalidate();
			logger.error("< UserController.login() > 登录返回出错." + e.getMessage());
			throw e;
		}

	}
	
	@RequestMapping(params = "action=checklogin")
	@ResponseBody
	public WebResultObject<UserProfile> checkLogin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		HttpSession session = request.getSession();
		UserProfile userProfile = null;
		try {
			userProfile = (UserProfile)session.getAttribute(PlatfromConstants.STR_USER_PROFILE);
			System.out.println(null == userProfile ? "null" : userProfile.getUserName());
			if(null != userProfile) {
				userProfile = WebUserConverter.getUserProfile(
						userService.queryUserByName(userProfile.getUserName()));
			}			
		}
		catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
			logger.error("< UserController.checkLogin() > 获取登录信息错误." + e.getMessage());
		}
		
		try {
			return JsonConverter.getResultObject(code, msg, userProfile);
		}
		catch (Exception e) {
			logger.error("< UserController.checkLogin() > 登录信息返回出错." + e.getMessage());
			throw e;
		}
	}
	
	@RequestMapping(params = "action=checkcounter")
	@ResponseBody
	public WebResultObject<User> checkCounter(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		HttpSession session = request.getSession();
		
		User user = null;
		try {
			UserProfile logUser = (UserProfile) session
					.getAttribute(PlatfromConstants.STR_USER_PROFILE);
			String jsonStr = request.getParameter("param");
			CounterObject counter = JsonConverter.getFromJsonString(jsonStr,
					CounterObject.class);
			if (counter == null ) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< UserController.checkCounter() > 信息为空或没有权限."
						+ counter);
			} 
			else if (null == session || null == logUser || null == logUser.getUserName()){
				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
				logger.error("< UserController.checkCounter() > session is null." + counter.getCounter());
			}else{
				// 检查用户名是否已经存在
				user = userService.queryUserByCounter(counter.getCounter());
				
				if (user != null){
					code = BusinessCenterResCode.NAME_EXIST.getCode();
					msg = BusinessCenterResCode.NAME_EXIST.getMsg();
				}
			}
			
		}
		catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
			logger.error("< UserController.checkCounter() > 获取登录信息错误." + e.getMessage());
		}
		
		try {
			return JsonConverter.getResultObject(code, msg, user);
		}
		catch (Exception e) {
			logger.error("< UserController.checkCounter() > 登录信息返回出错." + e.getMessage());
			throw e;
		}
	}

	@RequestMapping(params = "action=alllist")
	@ResponseBody
	public WebResultList<UserProfile> getAllUsers(Integer status,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);

		List<User> userList = new ArrayList<User>();
		List<UserProfile> userProfileList = new ArrayList<UserProfile>();
		try {
			userList = userService.queryAll();
			for (int i = 0; i < userList.size(); i++) {
				userProfileList.add(WebUserConverter.getUserProfile(userList
						.get(i)));
			}
		} catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		} catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
			logger.error("< UserController.getAllUsers() > 获取用户列表失败."
					+ e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultObject(code, msg, userProfileList);
		} catch (Exception e) {
			logger.error("< UserController.getAllUsers() > 获取用户列表返回出错."
					+ e.getMessage());
			throw e;
		}
	}

	@RequestMapping(params = "action=add")
	@ResponseBody
	public WebResult addUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);

		response.setHeader("Access-Control-Allow-Origin", "*");

		try {

			UserProfile admin = (UserProfile) session
					.getAttribute(PlatfromConstants.STR_USER_PROFILE);
			System.out.println(null == admin ? "admin is null" : admin.getUserName());

			// 验证请求参数
			String jsonStr = request.getParameter("param");
			UserProfile regUser = JsonConverter.getFromJsonString(jsonStr,
					UserProfile.class);
			if (StringUtil.isNull(jsonStr) || regUser == null ) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< UserController.addUser() > 注册用户信息为空或没有权限."
						+ jsonStr);
			} 
			else if (null == session || null == admin || null == admin.getUserName()){
				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
				logger.error("< UserController.addUser() > session is null." + jsonStr);
			} 
			else if (admin.getIsAdmin() == 0){
				code = BusinessCenterResCode.SYS_NO_ADMIN.getCode();
				msg = BusinessCenterResCode.SYS_NO_ADMIN.getMsg();
				logger.error("< UserController.addUser() > you are not admin." + jsonStr);
			}
			else{
				regUser.setIsAdmin(0);
				regUser.setIsValid(1);

				// 检查用户名是否已经存在
				User user = userService.queryUserByName(regUser.getUserName());
				
				if (user == null){
					userService.addUser(WebUserConverter.getUser(regUser));
				} else {
					code = BusinessCenterResCode.NAME_EXIST.getCode();
					msg = BusinessCenterResCode.NAME_EXIST.getMsg();
					System.out.println("用户名存在 " + code + " " + msg);
				}
			}
		} catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		} catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
			logger.error("< UserController.login() > 添加用户错误" + e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
			session.removeAttribute(PlatfromConstants.STR_USER_PROFILE);
			session.invalidate();
			logger.error("< UserController.addUser() > 添加用户返回出错."
					+ e.getMessage());
			throw e;
		}
	}
	
	@RequestMapping(params = "action=delete")
	@ResponseBody
	public WebResult deleteUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);

		response.setHeader("Access-Control-Allow-Origin", "*");

		try {

			UserProfile admin = (UserProfile) session
					.getAttribute(PlatfromConstants.STR_USER_PROFILE);

			// 验证请求参数
			String jsonStr = request.getParameter("param");
			UserProfile regUser = JsonConverter.getFromJsonString(jsonStr,
					UserProfile.class);
			if (StringUtil.isNull(jsonStr) || regUser == null ) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< UserController.deleteUser() > 注册用户信息为空或没有权限."
						+ jsonStr);
			} 
			else if (null == session || null == admin || null == admin.getUserName()){
				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
				logger.error("< UserController.deleteUser() > session is null." + jsonStr);
			} 
			else if (admin.getIsAdmin() == 0){
				code = BusinessCenterResCode.SYS_NO_ADMIN.getCode();
				msg = BusinessCenterResCode.SYS_NO_ADMIN.getMsg();
				logger.error("< UserController.deleteUser() > you are not admin." + jsonStr);
			}
			else{

				// 检查用户名是否已经存在
				User user = userService.queryUserByName(regUser.getUserName());
				
				if (user != null){
					userService.deleteUser(WebUserConverter.getUser(regUser));
				} else {
					code = BusinessCenterResCode.SYS_NOT_EXIST.getCode();
					msg = BusinessCenterResCode.SYS_NOT_EXIST.getMsg();
					System.out.println("用户不存在 " + code + " " + msg);
				}
			}
		} catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		} catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
			logger.error("< UserController.deleteUser() > 删除用户错误" + e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
			session.removeAttribute(PlatfromConstants.STR_USER_PROFILE);
			session.invalidate();
			logger.error("< UserController.deleteUser() > 删除用户返回出错."
					+ e.getMessage());
			throw e;
		}
	}
	
	@RequestMapping(params = "action=modifyuser")
	@ResponseBody
	public WebResult modifyUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);

		response.setHeader("Access-Control-Allow-Origin", "*");

		try {

			UserProfile admin = (UserProfile) session
					.getAttribute(PlatfromConstants.STR_USER_PROFILE);

			// 验证请求参数
			String jsonStr = request.getParameter("param");
			System.out.println(jsonStr);
			UserProfile regUser = JsonConverter.getFromJsonString(jsonStr,
					UserProfile.class);

			if (StringUtil.isNull(jsonStr) || regUser == null ) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< UserController.modifyUser() > 修改用户信息为空或没有权限."
						+ jsonStr);
			} else if (null == session || null == admin || null == admin.getUserName()){
				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
				logger.error("< UserController.modifyUser() > session is null." + jsonStr);
			} else{
		
				String counter = regUser.getCounter();
				if(counter != null && counter.equals("") == false){
					User existUser = userService.queryUserByCounter(counter);
					if(existUser != null){
						existUser.setCounter(null);
						userService.modifyUser(existUser);
					}
				}
				
				//修改用户信息
				userService.modifyUser(WebUserConverter.getUser(regUser));
				
				
			}
		} catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		} catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
			logger.error("< UserController.modifyUser() > 修改用户错误." + e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
			session.removeAttribute(PlatfromConstants.STR_USER_PROFILE);
			session.invalidate();
			logger.error("< UserController.addUser() > 修改用户返回出错."
					+ e.getMessage());
			throw e;
		}
	}

	@RequestMapping(params = "action=disablecounter")
	@ResponseBody
	public WebResult disableUserCounter(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);

		response.setHeader("Access-Control-Allow-Origin", "*");

		try {

			UserProfile admin = (UserProfile) session
					.getAttribute(PlatfromConstants.STR_USER_PROFILE);

			// 验证请求参数
			String jsonStr = request.getParameter("param");
			System.out.println(jsonStr);
			UserProfile regUser = JsonConverter.getFromJsonString(jsonStr,
					UserProfile.class);

			if (StringUtil.isNull(jsonStr) || regUser == null ) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< UserController.modifyUser() > 修改用户信息为空或没有权限."
						+ jsonStr);
			} else if (null == session || null == admin || null == admin.getUserName()){
				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
				logger.error("< UserController.modifyUser() > session is null." + jsonStr);
			} else{
		
				User existUser = userService.getByUserId(regUser.getId());
				existUser.setCounter(null);
				
				//修改用户信息
				userService.modifyUser(existUser);
				
				
			}
		} catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		} catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
			logger.error("< UserController.modifyUser() > 修改用户错误." + e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
			session.removeAttribute(PlatfromConstants.STR_USER_PROFILE);
			session.invalidate();
			logger.error("< UserController.addUser() > 修改用户返回出错."
					+ e.getMessage());
			throw e;
		}
	}

	
	@RequestMapping(params = "action=allgroups")
	@ResponseBody
	public WebResultList<UserGroup> getAllGroup(Integer status,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);

		List<UserGroup> groupList = new ArrayList<UserGroup>();
		try {
			groupList = userGroupService.queryAll();
		} catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		} catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
			logger.error("< UserController.getAllGroup() > 获取用户组列表失败."
					+ e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultObject(code, msg, groupList);
		} catch (Exception e) {
			logger.error("< UserController.getAllGroup() > 获取用户组列表返回出错."
					+ e.getMessage());
			throw e;
		}
	}
	
	@RequestMapping(params = "action=addgroup")
	@ResponseBody
	public WebResult addGroup(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);

		try {
			UserProfile admin = (UserProfile) session.getAttribute(PlatfromConstants.STR_USER_PROFILE);

			// 验证请求参数
			String jsonStr = request.getParameter("param");
			UserGroup userGroup = JsonConverter.getFromJsonString(jsonStr,UserGroup.class);

			if (StringUtil.isNull(jsonStr) || userGroup == null ) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< UserController.addGroup() > parameter is null."
						+ jsonStr);
			} else if (null == session || null == admin || null == admin.getUserName()){
				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
				logger.error("< UserController.addGroup() > session is null." + jsonStr);
			} else if (admin.getIsAdmin() == 0){
				code = BusinessCenterResCode.SYS_NO_ADMIN.getCode();
				msg = BusinessCenterResCode.SYS_NO_ADMIN.getMsg();
				logger.error("< UserController.addGroup() > you are not admin." + jsonStr);
			}else{
				// 注册
				userGroupService.addGroup(userGroup);
			}
		} catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		} catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
			logger.error("< UserController.addGroup() > 获取用户组列表失败."
					+ e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
			logger.error("< UserController.addGroup() > 获取用户组列表返回出错."
					+ e.getMessage());
			throw e;
		}
	}
	
	@RequestMapping(params = "action=deletegroup")
	@ResponseBody
	public WebResult deleteGroup(Integer id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);

		try {
			UserProfile admin = (UserProfile) session.getAttribute(PlatfromConstants.STR_USER_PROFILE);

			if (id == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< UserController.deleteGroup() > parameter is null。"
						+ id);
			} else if (null == session || null == admin || null == admin.getUserName()){
				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
				logger.error("< UserController.deleteGroup() > session is null");
			} else if (admin.getIsAdmin() == 0){
				code = BusinessCenterResCode.SYS_NO_ADMIN.getCode();
				msg = BusinessCenterResCode.SYS_NO_ADMIN.getMsg();
				logger.error("< UserController.deleteGroup() > you are not admin");
			}else{
				// 注册
				userGroupService.deleteGroup(id);
			}
		} catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		} catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
			logger.error("< UserController.deleteGroup() > fail to delete user group."
					+ e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
			logger.error("< UserController.deleteGroup() > fail to delete user group."
					+ e.getMessage());
			throw e;
		}
	}
}
