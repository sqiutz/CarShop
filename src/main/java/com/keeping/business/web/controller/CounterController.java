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
import com.keeping.business.service.CounterService;
import com.keeping.business.web.controller.converter.JsonConverter;
import com.keeping.business.web.controller.model.Counter;
import com.keeping.business.web.controller.model.UserProfile;
import com.keeping.business.web.controller.model.WebResult;
import com.keeping.business.web.controller.model.WebResultList;
import com.keeping.business.web.controller.model.WebResultObject;

@Controller
@RequestMapping("/counter.do")
public class CounterController {

	/** 日志 */
//	private Logger logger = LoggerFactory.getLogger(CounterController.class);

	/** 用户信息Service */
	@Resource
	private CounterService counterService;
	
	@RequestMapping(params = "action=getall")
	@ResponseBody
	public WebResultList<Counter> getAllCounters(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);

		List<Counter> counterList = new ArrayList<Counter>();
		
		try {
			
			counterList = counterService.queryAll();;
				
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
//			logger.error("< CounterController.getCounter() >  获取属性信息失败." + e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultObject(code, msg, counterList);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
//			logger.error("< CounterController.getCounter() > 获取属性信息返回出错." + e.getMessage());
			throw e;
		}
	}

	@RequestMapping(params = "action=counter")
	@ResponseBody
	public WebResultObject<Counter> getCounter(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);

		Counter counter = new Counter();
		try {
			String jsonStr = request.getParameter("param");
			System.out.println(jsonStr);
			Counter counterReq = JsonConverter.getFromJsonString(jsonStr,
					Counter.class);
			System.out.println(counterReq.getName());
			if (counterReq == null || counterReq.getName() == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< CounterController.getCounter() > 获取属性信息为空或没有权限." + jsonStr);
			}else{
				System.out.println("enter service");
				counter = counterService.queryByKey(counterReq.getName());
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
//			logger.error("< CounterController.getCounter() >  获取属性信息失败."
//					+ e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultObject(code, msg, counter);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
//			logger.error("< CounterController.getCounter() > 获取属性信息返回出错."
//					+ e.getMessage());
			throw e;
		}
	}

	@RequestMapping(params = "action=add")
	@ResponseBody
	public WebResult addCounter(HttpServletRequest request,
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
			Counter addCounter = JsonConverter.getFromJsonString(jsonStr,
					Counter.class);
			if (StringUtil.isNull(jsonStr) || addCounter == null ) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< CounterController.addCounter() > 属性信息为空或没有权限."
//						+ jsonStr);
			} 
			else if (null == session || null == admin || null == admin.getUserName()){
				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
//				logger.error("< CounterController.addCounter() > session is null." + jsonStr);
			} 
			else if (admin.getIsAdmin() == 0){
				code = BusinessCenterResCode.SYS_NO_ADMIN.getCode();
				msg = BusinessCenterResCode.SYS_NO_ADMIN.getMsg();
//				logger.error("< CounterController.addCounter() > you are not admin." + jsonStr);
			}
			else{
				// 检查用户名是否已经存在
				Counter counter = counterService.queryByKey(addCounter.getName()); 
				
				if (counter == null){
					counterService.addCounter(addCounter);
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
	public WebResult modifyCounter(HttpServletRequest request,
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
			Counter modifyCounter = JsonConverter.getFromJsonString(jsonStr,
					Counter.class);

			if (StringUtil.isNull(jsonStr) || modifyCounter == null ) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< CounterController.modifyCounter() > 修改属性为空或没有权限."
//						+ jsonStr);
			} else if (null == session || null == admin || null == admin.getUserName()){
				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
//				logger.error("< CounterController.modifyCounter() > session is null." + jsonStr);
			} else if (admin.getIsAdmin() == 0){
				code = BusinessCenterResCode.SYS_NO_ADMIN.getCode();
				msg = BusinessCenterResCode.SYS_NO_ADMIN.getMsg();
//				logger.error("< CounterController.modifyCounter() > you are not admin." + jsonStr);
			}
			else{
				//修改用户信息
				counterService.modifyCounter(modifyCounter);
			}
		} catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		} catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
//			logger.error("<  CounterController.modifyCounter() > 修改属性错误." + e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
			session.removeAttribute(PlatfromConstants.STR_USER_PROFILE);
			session.invalidate();
//			logger.error("<  CounterController.modifyCounter()  > 修改属性返回出错."
//					+ e.getMessage());
			throw e;
		}
	}

	
}
