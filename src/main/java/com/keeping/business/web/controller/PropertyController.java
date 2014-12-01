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
import com.keeping.business.service.PropertyService;
import com.keeping.business.web.controller.converter.JsonConverter;
import com.keeping.business.web.controller.model.IdObject;
import com.keeping.business.web.controller.model.Property;
import com.keeping.business.web.controller.model.UserProfile;
import com.keeping.business.web.controller.model.WebResult;
import com.keeping.business.web.controller.model.WebResultObject;

@Controller
@RequestMapping("/property.do")
public class PropertyController {

	/** 日志 */
	private Logger logger = LoggerFactory.getLogger(PropertyController.class);

	/** 用户信息Service */
	@Resource
	private PropertyService propertyService;

	@RequestMapping(params = "action=property")
	@ResponseBody
	public WebResultObject<Property> getProperty(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);

		Property property = new Property();
		try {
			UserProfile admin = (UserProfile) session
					.getAttribute(PlatfromConstants.STR_USER_PROFILE);
			String jsonStr = request.getParameter("param");
			System.out.println(jsonStr);
			Property propertyReq = JsonConverter.getFromJsonString(jsonStr,
					Property.class);
			System.out.println(propertyReq.getKey());
			if (propertyReq == null || property.getKey() == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< PropertyController.getProperty() > 获取属性信息为空或没有权限."
						+ jsonStr);
			}else if (null == session || null == admin || null == admin.getUserName()){
				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
				logger.error("<PropertyController.getProperty() > session is null." + propertyReq);
			}
			else{
				property = propertyService.queryByKey(propertyReq.getKey());
			}
		} catch (BusinessServiceException ex) {
			System.out.println(ex.getMessage());
			System.out.println(ex.getStackTrace());
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
			logger.error("< PropertyController.getProperty() >  获取属性信息失败."
					+ e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultObject(code, msg, property);
		} catch (Exception e) {
			logger.error("< PropertyController.getProperty() > 获取属性信息返回出错."
					+ e.getMessage());
			throw e;
		}
	}

	@RequestMapping(params = "action=add")
	@ResponseBody
	public WebResult addProperty(HttpServletRequest request,
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
			Property addProperty = JsonConverter.getFromJsonString(jsonStr,
					Property.class);
			if (StringUtil.isNull(jsonStr) || addProperty == null ) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< PropertyController.addProperty() > 属性信息为空或没有权限."
						+ jsonStr);
			} 
			else if (null == session || null == admin || null == admin.getUserName()){
				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
				logger.error("< PropertyController.addProperty() > session is null." + jsonStr);
			} 
			else if (admin.getIsAdmin() == 0){
				code = BusinessCenterResCode.SYS_NO_ADMIN.getCode();
				msg = BusinessCenterResCode.SYS_NO_ADMIN.getMsg();
				logger.error("< PropertyController.addProperty() > you are not admin." + jsonStr);
			}
			else{
				// 检查用户名是否已经存在
				Property property = propertyService.queryByKey(addProperty.getKey()); 
				
				if (property == null){
					propertyService.addProperty(addProperty);
				} else {
					code = BusinessCenterResCode.NAME_EXIST.getCode();
					msg = BusinessCenterResCode.NAME_EXIST.getMsg();
					System.out.println("属性存在 " + code + " " + msg);
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
	
	@RequestMapping(params = "action=modify")
	@ResponseBody
	public WebResult modifyProperty(HttpServletRequest request,
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
			Property modifyProperty = JsonConverter.getFromJsonString(jsonStr,
					Property.class);

			if (StringUtil.isNull(jsonStr) || modifyProperty == null ) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< PropertyController.modifyProperty() > 修改属性为空或没有权限."
						+ jsonStr);
			} else if (null == session || null == admin || null == admin.getUserName()){
				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
				logger.error("< PropertyController.modifyProperty() > session is null." + jsonStr);
			} else if (admin.getIsAdmin() == 0){
				code = BusinessCenterResCode.SYS_NO_ADMIN.getCode();
				msg = BusinessCenterResCode.SYS_NO_ADMIN.getMsg();
				logger.error("< PropertyController.modifyProperty() > you are not admin." + jsonStr);
			}
			else{
				//修改用户信息
				propertyService.modifyProperty(modifyProperty);
			}
		} catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		} catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
			logger.error("<  PropertyController.modifyProperty() > 修改属性错误." + e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
			session.removeAttribute(PlatfromConstants.STR_USER_PROFILE);
			session.invalidate();
			logger.error("<  PropertyController.modifyProperty()  > 修改属性返回出错."
					+ e.getMessage());
			throw e;
		}
	}

	
}
