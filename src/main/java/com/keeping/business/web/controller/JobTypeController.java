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
import com.keeping.business.service.JobTypeService;
import com.keeping.business.web.controller.converter.JsonConverter;
import com.keeping.business.web.controller.model.JobType;
import com.keeping.business.web.controller.model.UserProfile;
import com.keeping.business.web.controller.model.WebResult;
import com.keeping.business.web.controller.model.WebResultList;
import com.keeping.business.web.controller.model.WebResultObject;

@Controller
@RequestMapping("/jobtype.do")
public class JobTypeController {

	/** 日志 */
//	private Logger logger = LoggerFactory.getLogger(JobTypeController.class);

	/** 用户信息Service */
	@Resource
	private JobTypeService jobtypeService;
	
	@RequestMapping(params = "action=getall")
	@ResponseBody
	public WebResultList<JobType> getAllJobTypes(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);

		List<JobType> jobtypeList = new ArrayList<JobType>();
		
		try {
			
			jobtypeList = jobtypeService.queryAll();;
				
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
//			logger.error("< JobTypeController.getJobType() >  获取属性信息失败."
//					+ e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultObject(code, msg, jobtypeList);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
//			logger.error("< JobTypeController.getJobType() > 获取属性信息返回出错."
//					+ e.getMessage());
			throw e;
		}
	}

	@RequestMapping(params = "action=jobtype")
	@ResponseBody
	public WebResultObject<JobType> getJobType(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);

		JobType jobtype = new JobType();
		try {
			String jsonStr = request.getParameter("param");
			System.out.println(jsonStr);
			JobType jobtypeReq = JsonConverter.getFromJsonString(jsonStr,
					JobType.class);
			System.out.println(jobtypeReq.getName());
			if (jobtypeReq == null || jobtypeReq.getName() == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< JobTypeController.getJobType() > 获取属性信息为空或没有权限."
//						+ jsonStr);
			}else{
				System.out.println("enter service");
				jobtype = jobtypeService.queryByKey(jobtypeReq.getName());
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
//			logger.error("< JobTypeController.getJobType() >  获取属性信息失败."
//					+ e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultObject(code, msg, jobtype);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
//			logger.error("< JobTypeController.getJobType() > 获取属性信息返回出错."
//					+ e.getMessage());
			throw e;
		}
	}

	@RequestMapping(params = "action=add")
	@ResponseBody
	public WebResult addJobType(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);

		try {

			UserProfile admin = (UserProfile) session
					.getAttribute(PlatfromConstants.STR_USER_PROFILE);
			System.out.println(null == admin ? "admin is null" : admin.getUserName());

			// 验证请求参数
			String jsonStr = request.getParameter("param");
			JobType addJobType = JsonConverter.getFromJsonString(jsonStr,
					JobType.class);
			if (StringUtil.isNull(jsonStr) || addJobType == null ) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< JobTypeController.addJobType() > 属性信息为空或没有权限."
//						+ jsonStr);
			} 
			else if (null == session || null == admin || null == admin.getUserName()){
				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
//				logger.error("< JobTypeController.addJobType() > session is null." + jsonStr);
			} 
			else if (admin.getIsAdmin() == 0){
				code = BusinessCenterResCode.SYS_NO_ADMIN.getCode();
				msg = BusinessCenterResCode.SYS_NO_ADMIN.getMsg();
//				logger.error("< JobTypeController.addJobType() > you are not admin." + jsonStr);
			}
			else{
				// 检查用户名是否已经存在
				JobType jobtype = jobtypeService.queryByKey(addJobType.getName()); 
				
				if (jobtype == null){
					jobtypeService.addJobType(addJobType);
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
//			logger.error("< UserController.login() > 添加用户错误" + e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
			session.removeAttribute(PlatfromConstants.STR_USER_PROFILE);
			session.invalidate();
//			logger.error("< UserController.addUser() > 添加用户返回出错."
//					+ e.getMessage());
			throw e;
		}
	}
	
	@RequestMapping(params = "action=modify")
	@ResponseBody
	public WebResult modifyJobType(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);

		try {

			UserProfile admin = (UserProfile) session
					.getAttribute(PlatfromConstants.STR_USER_PROFILE);

			// 验证请求参数
			String jsonStr = request.getParameter("param");
			JobType modifyJobType = JsonConverter.getFromJsonString(jsonStr,
					JobType.class);

			if (StringUtil.isNull(jsonStr) || modifyJobType == null ) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< JobTypeController.modifyJobType() > 修改属性为空或没有权限."
//						+ jsonStr);
			} 
//			else if (null == session || null == admin || null == admin.getUserName()){
//				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
//				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
//				logger.error("< JobTypeController.modifyJobType() > session is null." + jsonStr);
//			} 
//			else if (admin.getIsAdmin() == 0){
//				code = BusinessCenterResCode.SYS_NO_ADMIN.getCode();
//				msg = BusinessCenterResCode.SYS_NO_ADMIN.getMsg();
//				logger.error("< JobTypeController.modifyJobType() > you are not admin." + jsonStr);
//			}
			else{
				//修改用户信息
				jobtypeService.modifyJobType(modifyJobType);
			}
		} catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		} catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
//			logger.error("<  JobTypeController.modifyJobType() > 修改属性错误." + e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
			session.removeAttribute(PlatfromConstants.STR_USER_PROFILE);
			session.invalidate();
//			logger.error("<  JobTypeController.modifyJobType()  > 修改属性返回出错."
//					+ e.getMessage());
			throw e;
		}
	}

	@RequestMapping(params = "action=delete")
	@ResponseBody
	public WebResult deleteJobType(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);

		try {

			UserProfile admin = (UserProfile) session
					.getAttribute(PlatfromConstants.STR_USER_PROFILE);

			// 验证请求参数
			String jsonStr = request.getParameter("param");
			JobType modifyJobType = JsonConverter.getFromJsonString(jsonStr,
					JobType.class);

			if (StringUtil.isNull(jsonStr) || modifyJobType == null ) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< JobTypeController.modifyJobType() > 修改属性为空或没有权限."
//						+ jsonStr);
			} else if (null == session || null == admin || null == admin.getUserName()){
				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
//				logger.error("< JobTypeController.modifyJobType() > session is null." + jsonStr);
			} else if (admin.getIsAdmin() == 0){
				code = BusinessCenterResCode.SYS_NO_ADMIN.getCode();
				msg = BusinessCenterResCode.SYS_NO_ADMIN.getMsg();
//				logger.error("< JobTypeController.modifyJobType() > you are not admin." + jsonStr);
			}
			else{
				//修改用户信息
				jobtypeService.deleteJobType(modifyJobType.getId());
			}
		} catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		} catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
//			logger.error("<  JobTypeController.modifyJobType() > 修改属性错误." + e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
			session.removeAttribute(PlatfromConstants.STR_USER_PROFILE);
			session.invalidate();
//			logger.error("<  JobTypeController.modifyJobType()  > 修改属性返回出错."
//					+ e.getMessage());
			throw e;
		}
	}
}
