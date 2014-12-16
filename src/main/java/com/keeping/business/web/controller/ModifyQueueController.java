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
import com.keeping.business.common.rescode.BusinessCenterModifyQueueStatus;
import com.keeping.business.common.rescode.BusinessCenterOrderStatus;
import com.keeping.business.common.rescode.BusinessCenterResCode;
import com.keeping.business.common.rescode.BusinessCenterServeQueueStatus;
import com.keeping.business.common.rescode.BusinessCenterUserGroup;
import com.keeping.business.common.util.PlatformPar;
import com.keeping.business.common.util.PlatfromConstants;
import com.keeping.business.common.util.StringUtil;
import com.keeping.business.service.ModifyQueueService;
import com.keeping.business.service.OrderService;
import com.keeping.business.service.ServeQueueService;
import com.keeping.business.service.UserService;
import com.keeping.business.web.controller.converter.JsonConverter;
import com.keeping.business.web.controller.converter.ReorgQueue;
import com.keeping.business.web.controller.converter.WebUserConverter;
import com.keeping.business.web.controller.model.CashQueue;
import com.keeping.business.web.controller.model.IdObject;
import com.keeping.business.web.controller.model.LoginReq;
import com.keeping.business.web.controller.model.ModifyQueue;
import com.keeping.business.web.controller.model.Order;
import com.keeping.business.web.controller.model.ServeQueue;
import com.keeping.business.web.controller.model.StepObject;
import com.keeping.business.web.controller.model.User;
import com.keeping.business.web.controller.model.UserProfile;
import com.keeping.business.web.controller.model.WebResult;
import com.keeping.business.web.controller.model.WebResultList;
import com.keeping.business.web.controller.model.WebResultObject;

@Controller
@RequestMapping("/modifyqueue.do")
public class ModifyQueueController {

	/** 日志 */
	private Logger logger = LoggerFactory.getLogger(UserController.class);

	/** 用户信息Service */
	@Resource
	private ModifyQueueService modifyQueueService;
	@Resource
	private OrderService orderService;
	@Resource
	private UserService userService;

	@RequestMapping(params = "action=getone")
	@ResponseBody
	public WebResultObject<ModifyQueue> getModifyQueue(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();

		ModifyQueue modifyQueue = new ModifyQueue();
		
		try {
			
			String jsonStr = request.getParameter("param");
			IdObject idObject = JsonConverter.getFromJsonString(jsonStr,IdObject.class);
			
			if (null == idObject || idObject.getId() == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< ModifyQueueController.getModifyQueue() > 获取维修订单请求信息不正确: " + idObject.getId());
			} else {
				
				modifyQueue = modifyQueueService.getModifyQueueById(idObject.getId());
				
				if (modifyQueue != null && modifyQueue.getId() != null){
					Integer userId = modifyQueue.getUserId();
					Integer orderId = modifyQueue.getOrderId();
					
					User user = userService.getByUserId(userId);
					Order order = orderService.queryOrderById(orderId);

					modifyQueue.setUser(user);
					modifyQueue.setOrder(order);
					
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
			logger.error("< ModifyQueueController.getModifyQueue() > 获取维修列表失败."
					+ e.getMessage());
		}
		
		return JsonConverter.getResultObject(code, msg, modifyQueue);
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
			
			ModifyQueue modifyQueue = JsonConverter.getFromJsonString(jsonStr,
					ModifyQueue.class);
	
			if (StringUtil.isNull(jsonStr) || modifyQueue == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< ModifyQueueController.start() > 订单维修订单信息为空或没有权限。"
						+ jsonStr);
			} else{
				
				modifyQueue = modifyQueueService.getModifyQueueById(modifyQueue.getId());
				
				if (modifyQueue != null){
					
					modifyQueue.setStep(BusinessCenterModifyQueueStatus.MODIFYQUEUE_STATUS_START.getId());
					modifyQueueService.updateModifyQueue(modifyQueue);
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
			logger.error("< ModifyQueueController.start() > 挂起任务失败."
					+ e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
			logger.error("< ModifyQueueController.start() > 挂起任务返回出错."
					+ e.getMessage());
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
			
			ModifyQueue modifyQueue = JsonConverter.getFromJsonString(jsonStr,
					ModifyQueue.class);
	
			if (StringUtil.isNull(jsonStr) || modifyQueue == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< ModifyQueueController.hold() > 订单维修订单信息为空或没有权限。"
						+ jsonStr);
			} else{
				
				modifyQueue = modifyQueueService.getModifyQueueById(modifyQueue.getId());
				
				if (modifyQueue != null){
					
					modifyQueue.setStep(BusinessCenterModifyQueueStatus.MODIFYQUEUE_STATUS_HOLD.getId());
					modifyQueueService.updateModifyQueue(modifyQueue);
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
			logger.error("< ModifyQueueController.hold() > 挂起任务失败."
					+ e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
			logger.error("< ModifyQueueController.hold() > 挂起任务返回出错."
					+ e.getMessage());
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
			
			ModifyQueue modifyQueue = JsonConverter.getFromJsonString(jsonStr,
					ModifyQueue.class);

			if (StringUtil.isNull(jsonStr) || modifyQueue == null ) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< ModifyQueueController.finish() > 维修订单信息为空或没有权限。"
						+ jsonStr);
			}else{
				modifyQueue = modifyQueueService.getModifyQueueById(modifyQueue.getId());
				Order order = orderService.queryOrderById(modifyQueue.getOrderId());
				order.setStatus(BusinessCenterOrderStatus.ORDER_STATUS_WASH.getId());
				orderService.updateOrder(order);              //修改订单状态
				
				Date now = new Date();
				java.sql.Timestamp dateTime = new java.sql.Timestamp(now.getTime());
				
				modifyQueue.setEndTime(dateTime);
				modifyQueue.setStep(BusinessCenterModifyQueueStatus.MODIFYQUEUE_STATUS_FINISH.getId());
				
				modifyQueueService.updateModifyQueue(modifyQueue);   //更新modifyQueue订单
				
				CashQueue cashQueue = new CashQueue();
				cashQueue.setStep(BusinessCenterCashQueueStatus.CASHQUEUE_STATUS_MODIFYING.getId());
				cashQueue.setOrderId(modifyQueue.getOrderId());
				cashQueue.setUserId(modifyQueue.getUserId());
				
				modifyQueueService.addModifyQueue(modifyQueue);
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
			logger.error("< ModifyQueueController.finish() > 维修订单发送清洗车间失败."
					+ e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
			logger.error("< ModifyQueueController.finish() > 发送清洗车间返回出错."
					+ e.getMessage());
			throw e;
		}
	}
	
}