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
import com.keeping.business.common.util.StringUtil;
import com.keeping.business.service.UserService;
import com.keeping.business.web.controller.converter.JsonConverter;
import com.keeping.business.web.controller.converter.WebUserConverter;
import com.keeping.business.web.controller.model.LoginReq;
import com.keeping.business.web.controller.model.User;
import com.keeping.business.web.controller.model.UserIdObject;
import com.keeping.business.web.controller.model.UserProfile;
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
	public WebResultObject<UserIdObject> login(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		Long start = System.currentTimeMillis();
//		User user = userService.login("aaa", "test");
//		userService.queryAll(null);
		Integer total = userService.checkValidUsername("test");
		Long end = System.currentTimeMillis();
		
		System.out.println("Time is:" + (end - start) + "  and total is:" + total);
		
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		
		UserIdObject reqUserId = new UserIdObject();
		
		return JsonConverter.getResultObject(code, msg, reqUserId);
		
//		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
//		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
//		UserIdObject reqUserId = new UserIdObject();
//		HttpSession session = request.getSession();
//		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);
//		
//		try {
//			//验证请求参数
//			String jsonStr = request.getParameter("param");
//			LoginReq req = JsonConverter.getFromJsonString(jsonStr, LoginReq.class);
//
//
//			if (StringUtil.isNull(jsonStr) || req == null) {
//				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
//				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< UserController.login() > 登录请求信息不正确。" + jsonStr);
//			}else {
//				UserProfile userProfile = WebUserConverter.getUserProfile(
//						userService.login(req.getUsername(), req.getPasswd()));
//				reqUserId = new UserIdObject();
//				reqUserId.setUserId(userProfile.getUserId());
//				
//				//将用户信息保存在session中
////				session.setAttribute(PlatfromConstants.STR_USER_PROFILE, userProfile);
//			}
//		}catch (BusinessServiceException ex) {
//			code = ex.getErrorCode();
//			msg = ex.getErrorMessage();
//		}catch (Exception e) {
//			code = BusinessCenterResCode.SYS_ERROR.getCode();
//			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
//			logger.error("< UserController.login() > 登录错误." + e.getMessage());
//		}
//
//		//返回结果
//		try{
//			return JsonConverter.getResultObject(code, msg, reqUserId);
//		}catch (Exception e) {
////			session.removeAttribute(PlatfromConstants.STR_USER_PROFILE);
//			session.invalidate();
//			logger.error("< UserController.login() > 登录返回出错." + e.getMessage());
//			throw e;
//		}
		
	}

}
