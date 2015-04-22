package com.keeping.business.web.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.keeping.business.common.rescode.BusinessCenterCashQueueStatus;
import com.keeping.business.common.rescode.BusinessCenterIssueQueueStatus;
import com.keeping.business.common.rescode.BusinessCenterOrderStatus;
import com.keeping.business.common.rescode.BusinessCenterResCode;
import com.keeping.business.common.rescode.BusinessCenterServeQueueStatus;
import com.keeping.business.common.rescode.BusinessCenterUserGroup;
import com.keeping.business.common.util.PlatformPar;
import com.keeping.business.common.util.PlatfromConstants;
import com.keeping.business.common.util.StringUtil;
import com.keeping.business.service.CashQueueService;
import com.keeping.business.service.JobTypeService;
import com.keeping.business.service.IssueQueueService;
import com.keeping.business.service.OrderService;
import com.keeping.business.service.ServeQueueService;
import com.keeping.business.service.UserService;
import com.keeping.business.service.UserWorkloadService;
import com.keeping.business.web.controller.converter.JsonConverter;
import com.keeping.business.web.controller.converter.ReorgQueue;
import com.keeping.business.web.controller.converter.WebUserConverter;
import com.keeping.business.web.controller.model.CashQueue;
import com.keeping.business.web.controller.model.IdObject;
import com.keeping.business.web.controller.model.JobType;
import com.keeping.business.web.controller.model.LoginReq;
import com.keeping.business.web.controller.model.IssueQueue;
import com.keeping.business.web.controller.model.Order;
import com.keeping.business.web.controller.model.ServeQueue;
import com.keeping.business.web.controller.model.StepObject;
import com.keeping.business.web.controller.model.User;
import com.keeping.business.web.controller.model.UserProfile;
import com.keeping.business.web.controller.model.UserWorkload;
import com.keeping.business.web.controller.model.WebResult;
import com.keeping.business.web.controller.model.WebResultList;
import com.keeping.business.web.controller.model.WebResultObject;

@Controller
@RequestMapping("/issuequeue.do")
public class IssueQueueController {

	/** 日志 */
//	private Logger logger = LoggerFactory.getLogger(UserController.class);

	/** 用户信息Service */
	@Resource
	private IssueQueueService issueQueueService;
	@Resource
	private OrderService orderService;
	@Resource
	private UserService userService;
	@Resource
	private JobTypeService jobtypeService;
	
	@RequestMapping(params = "action=getone")
	@ResponseBody
	public WebResultObject<IssueQueue> getIssueQueue(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();

		IssueQueue issueQueue = new IssueQueue();
		
		try {
			
			String jsonStr = request.getParameter("param");
			IdObject idObject = JsonConverter.getFromJsonString(jsonStr,IdObject.class);
			
			if (null == idObject || idObject.getId() == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< IssueQueueController.getIssueQueue() > 获取维修订单请求信息不正确: " + idObject.getId());
			} else {
				
				issueQueue = issueQueueService.getIssueQueueById(idObject.getId());
				
				if (issueQueue != null && issueQueue.getId() != null){
					Integer userId = issueQueue.getUserId();
					Integer orderId = issueQueue.getOrderId();
					Integer issuerId = issueQueue.getIssuerId();
					
					User user = userService.getByUserId(userId);
					Order order = orderService.queryOrderById(orderId);
					User issuer = userService.getByUserId(issuerId);

					issueQueue.setUser(user);
					issueQueue.setOrder(order);
					issueQueue.setIssuer(issuer);
					
				}else{
					code = BusinessCenterResCode.ORDER_NOT_EXIST.getCode();
					msg = BusinessCenterResCode.ORDER_NOT_EXIST.getMsg();
				}
				
			}
		} catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
//			logger.error("< IssueQueueController.getIssueQueue() > 获取维修列表失败."
//					+ e.getMessage());
		}
		
		return JsonConverter.getResultObject(code, msg, issueQueue);
	}
	
	@RequestMapping(params = "action=allocate")
	@ResponseBody
	public WebResultObject<IssueQueue> allocateIssueQueue(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();

		IssueQueue issueQueue = new IssueQueue();
		
		try {
			
			String jsonStr = request.getParameter("param");
			IssueQueue issueQueueObject = JsonConverter.getFromJsonString(jsonStr,IssueQueue.class);
			
			if (null == issueQueueObject || issueQueueObject.getId() == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< IssueQueueController.getIssueQueue() > 获取维修订单请求信息不正确: " + idObject.getId());
			} else {
				
				issueQueue = issueQueueService.getIssueQueueById(issueQueueObject.getId());
				
				if (issueQueue != null && issueQueue.getId() != null){
					
					if(issueQueueObject.getUserId() != null){
						issueQueue.setUserId(issueQueueObject.getUserId());
					}
					
					Date now = new Date();
					
					issueQueue.setCreateTime(now);
					issueQueue.setIssuerId(issueQueueObject.getIssuerId());
					issueQueue.setStep(BusinessCenterIssueQueueStatus.MODIFYQUEUE_STATUS_ISSUE.getId());
					issueQueueService.addIssueQueue(issueQueue);
					
				}else{
					code = BusinessCenterResCode.ORDER_NOT_EXIST.getCode();
					msg = BusinessCenterResCode.ORDER_NOT_EXIST.getMsg();
				}
				
			}
		} catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
//			logger.error("< IssueQueueController.getIssueQueue() > 获取维修列表失败."
//					+ e.getMessage());
		}
		
		return JsonConverter.getResultObject(code, msg, issueQueue);
	}

	@RequestMapping(params = "action=start")
	@ResponseBody
	public WebResult start(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		
		try {
			String jsonStr = request.getParameter("param");
			
			IssueQueue issueQueue = JsonConverter.getFromJsonString(jsonStr,
					IssueQueue.class);
	
			if (StringUtil.isNull(jsonStr) || issueQueue == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< IssueQueueController.start() > 订单维修订单信息为空或没有权限。"
//						+ jsonStr);
			} else{
				
				issueQueue = issueQueueService.getIssueQueueById(issueQueue.getId());
				
				if (issueQueue != null && issueQueue.getStep() > 0){
					
					Integer step = issueQueue.getStep();
					
					if (step < BusinessCenterIssueQueueStatus.MODIFYQUEUE_STATUS_FINISH.getId()){
						Date now = new Date();
						issueQueue.setStep(BusinessCenterIssueQueueStatus.MODIFYQUEUE_STATUS_START.getId());
						issueQueue.setStartTime(now);
						issueQueueService.updateIssueQueue(issueQueue);
					}
				}else{
					code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
					msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				}

			}
		} catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
//			logger.error("< IssueQueueController.start() > 挂起任务失败."
//					+ e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
//			logger.error("< IssueQueueController.start() > 挂起任务返回出错."
//					+ e.getMessage());
			throw e;
		}
	}
	
	@RequestMapping(params = "action=hold")
	@ResponseBody
	public WebResult hold(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		
		try {
			String jsonStr = request.getParameter("param");
			
			IssueQueue issueQueue = JsonConverter.getFromJsonString(jsonStr,
					IssueQueue.class);
	
			if (StringUtil.isNull(jsonStr) || issueQueue == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< IssueQueueController.hold() > 订单维修订单信息为空或没有权限。"
//						+ jsonStr);
			} else{
				
				issueQueue = issueQueueService.getIssueQueueById(issueQueue.getId());
				
				if (issueQueue != null && issueQueue.getStep() > 0){
					
					issueQueue.setStep(BusinessCenterIssueQueueStatus.MODIFYQUEUE_STATUS_HOLD.getId());
					issueQueueService.updateIssueQueue(issueQueue);
				}else{
					code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
					msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				}

			}
		} catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
//			logger.error("< IssueQueueController.hold() > 挂起任务失败."
//					+ e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
//			logger.error("< IssueQueueController.hold() > 挂起任务返回出错."
//					+ e.getMessage());
			throw e;
		}
	}

	@RequestMapping(params = "action=finish")
	@ResponseBody
	public WebResult finish(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		
		try {
			String jsonStr = request.getParameter("param");
			
			IssueQueue issueQueue = JsonConverter.getFromJsonString(jsonStr,
					IssueQueue.class);
	
			if (StringUtil.isNull(jsonStr) || issueQueue == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< IssueQueueController.hold() > 订单维修订单信息为空或没有权限。"
//						+ jsonStr);
			} else{
				
				issueQueue = issueQueueService.getIssueQueueById(issueQueue.getId());
				
				if (issueQueue != null && issueQueue.getStep() > 0){
					
					issueQueue.setStep(BusinessCenterIssueQueueStatus.MODIFYQUEUE_STATUS_FINISH.getId());
					issueQueueService.updateIssueQueue(issueQueue);
				}else{
					code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
					msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				}

			}
		} catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
//			logger.error("< IssueQueueController.hold() > 挂起任务失败."
//					+ e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
//			logger.error("< IssueQueueController.hold() > 挂起任务返回出错."
//					+ e.getMessage());
			throw e;
		}
	}
	
}
