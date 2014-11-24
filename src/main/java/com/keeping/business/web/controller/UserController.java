package com.keeping.business.web.controller;

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
import com.keeping.business.service.UserService;
import com.keeping.business.web.controller.converter.JsonConverter;
import com.keeping.business.web.controller.converter.WebUserConverter;
import com.keeping.business.web.controller.model.LoginReq;
import com.keeping.business.web.controller.model.UserProfile;
import com.keeping.business.web.controller.model.WebResult;
import com.keeping.business.web.controller.model.WebResultObject;

@Controller
@RequestMapping("/user.do")
public class UserController {
	
    /**日志 */
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    
	/**用户信息Service */
    @Resource
	private UserService userService;

	@RequestMapping(params = "action=login") 
	@ResponseBody
	public WebResultObject<UserProfile> login(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		//UserIdObject reqUserId = new UserIdObject();
		UserProfile userProfile = new UserProfile();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);

		response.setHeader("Access-Control-Allow-Origin", "*");
		
		try {
			//验证请求参数
			String jsonStr = request.getParameter("param");
			LoginReq req = JsonConverter.getFromJsonString(jsonStr, LoginReq.class);

			if (StringUtil.isNull(jsonStr) || req == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< UserController.login() > 登录请求信息不正确。" + jsonStr);
			}else {
				userProfile = WebUserConverter.getUserProfile(
						userService.login(req.getUsername(), req.getPasswd()));
				
				//将用户信息保存在session中
				session.setAttribute(PlatfromConstants.STR_USER_PROFILE, userProfile);
			}
		}catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		}catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
			logger.error("< UserController.login() > 登录错误." + e.getMessage());
		}

		//返回结果
		try{
			return JsonConverter.getResultObject(code, msg, userProfile);
		}catch (Exception e) {
			session.removeAttribute(PlatfromConstants.STR_USER_PROFILE);
			session.invalidate();
			logger.error("< UserController.login() > 登录返回出错." + e.getMessage());
			throw e;
		}
		
	}
	
	@RequestMapping(params = "action=add") 
	@ResponseBody
	public WebResult addUser(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		try {
			
			UserProfile admin = (UserProfile) session.getAttribute(PlatfromConstants.STR_USER_PROFILE);
			
			//验证请求参数
			String jsonStr = request.getParameter("param");
			UserProfile regUser = JsonConverter.getFromJsonString(jsonStr, UserProfile.class);

			if (StringUtil.isNull(jsonStr) || regUser == null || null == admin || "admin".equals(admin.getUserName()) == false) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< UserController.addUser() > 注册用户信息为空或没有权限。" + jsonStr);
			}else {
				regUser.setIsAdmin(0);
				regUser.setIsValid(1);
				//注册
				userService.addUser(WebUserConverter.getUser(regUser));
			}
		}catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		}catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
			logger.error("< UserController.login() > 添加用户错误." + e.getMessage());
		}

		//返回结果
		try{
			return JsonConverter.getResultSignal(code, msg);
		}catch (Exception e) {
			session.removeAttribute(PlatfromConstants.STR_USER_PROFILE);
			session.invalidate();
			logger.error("< UserController.addUser() > 添加用户返回出错." + e.getMessage());
			throw e;
		}
	}
	
}
