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
import com.keeping.business.common.rescode.BusinessCenterOrderStatus;
import com.keeping.business.common.rescode.BusinessCenterResCode;
import com.keeping.business.common.rescode.BusinessCenterServeQueueStatus;
import com.keeping.business.common.rescode.BusinessCenterUserGroup;
import com.keeping.business.common.util.PlatformPar;
import com.keeping.business.common.util.PlatfromConstants;
import com.keeping.business.common.util.StringUtil;
import com.keeping.business.service.CashQueueService;
import com.keeping.business.service.OrderService;
import com.keeping.business.service.ServeQueueService;
import com.keeping.business.service.UserService;
import com.keeping.business.web.controller.converter.JsonConverter;
import com.keeping.business.web.controller.converter.ReorgQueue;
import com.keeping.business.web.controller.converter.WebUserConverter;
import com.keeping.business.web.controller.model.IdObject;
import com.keeping.business.web.controller.model.LoginReq;
import com.keeping.business.web.controller.model.CashQueue;
import com.keeping.business.web.controller.model.Order;
import com.keeping.business.web.controller.model.ServeQueue;
import com.keeping.business.web.controller.model.StepObject;
import com.keeping.business.web.controller.model.User;
import com.keeping.business.web.controller.model.UserProfile;
import com.keeping.business.web.controller.model.WebResult;
import com.keeping.business.web.controller.model.WebResultList;
import com.keeping.business.web.controller.model.WebResultObject;

@Controller
@RequestMapping("/cashqueue.do")
public class CashQueueController {

	/** 日志 */
//	private Logger logger = LoggerFactory.getLogger(UserController.class);

	/** 用户信息Service */
	@Resource
	private CashQueueService cashQueueService;
	@Resource
	private OrderService orderService;
	@Resource
	private UserService userService;

	@RequestMapping(params = "action=alllist")
	@ResponseBody
	public WebResultList<CashQueue> getCashQueues(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();

		
		List<CashQueue> cashQueues = new ArrayList<CashQueue>();
		
		try {
			
			String jsonStr = request.getParameter("param");
			StepObject step = JsonConverter.getFromJsonString(jsonStr,
					StepObject.class);
			
			if (null == step || step.getStep() == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< CashQueueController.getCashQueue() > 获取维修订单请求信息不正确: " + step.getStep());
			} else {
				
				cashQueues = cashQueueService.getCashQueueByStep(Integer.parseInt(step.getStep()));
				
				List<Integer> userIdList = new ArrayList<Integer>();
				List<Integer> orderIdList = new ArrayList<Integer>();
				List<User> users = new ArrayList<User>();
				for (int i=0; i<cashQueues.size(); i++){
					userIdList.add(cashQueues.get(i).getUserId());
					orderIdList.add(cashQueues.get(i).getOrderId());
					
					User user = userService.getByUserId(userIdList.get(i));
					users.add(user);
				}
				
				if(cashQueues.size() > 0){
					
					List<Order> orders = orderService.getByOrdersId(orderIdList);
					
					ReorgQueue.reorgCashQueue(cashQueues, users, orders);
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
//			logger.error("< CashQueueController.getCashQueue() > 获取维修列表失败." + e.getMessage());
		}
		
		return JsonConverter.getResultObject(code, msg, cashQueues);
	}
	
	@RequestMapping(params = "action=getone")
	@ResponseBody
	public WebResultObject<CashQueue> getCashQueue(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();

		CashQueue cashQueue = new CashQueue();
		
		try {
			
			String jsonStr = request.getParameter("param");
			IdObject idObject = JsonConverter.getFromJsonString(jsonStr,IdObject.class);
			
			if (null == idObject || idObject.getId() == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< CashQueueController.getCashQueue() > 获取维修订单请求信息不正确: " + idObject.getId());
			} else {
				
				cashQueue = cashQueueService.getCashQueueById(idObject.getId());
				
				if (cashQueue != null && cashQueue.getId() != null){
					Integer userId = cashQueue.getUserId();
					Integer orderId = cashQueue.getOrderId();
					
					User user = userService.getByUserId(userId);
					Order order = orderService.queryOrderById(orderId);

					cashQueue.setUser(user);
					cashQueue.setOrder(order);
					
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
//			logger.error("< CashQueueController.getCashQueue() > 获取维修列表失败." + e.getMessage());
		}
		
		return JsonConverter.getResultObject(code, msg, cashQueue);
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
			
			CashQueue cashQueue = JsonConverter.getFromJsonString(jsonStr,
					CashQueue.class);
	
			if (StringUtil.isNull(jsonStr) || cashQueue == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< CashQueueController.start() > 订单维修订单信息为空或没有权限。" + jsonStr);
			} else{
				
				cashQueue = cashQueueService.getCashQueueById(cashQueue.getId());
				
				if (cashQueue != null){
					
					cashQueue.setStep(BusinessCenterCashQueueStatus.CASHQUEUE_STATUS_START.getId());
					cashQueueService.updateCashQueue(cashQueue);
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
//			logger.error("< CashQueueController.start() > 挂起任务失败." + e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
//			logger.error("< CashQueueController.start() > 挂起任务返回出错." + e.getMessage());
			throw e;
		}
	}
	
	@RequestMapping(params = "action=cancel")
	@ResponseBody
	public WebResult cancel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		
		try {
			String jsonStr = request.getParameter("param");
			
			CashQueue cashQueue = JsonConverter.getFromJsonString(jsonStr,
					CashQueue.class);
	
			if (StringUtil.isNull(jsonStr) || cashQueue == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< CashQueueController.cancel() > 订单维修订单信息为空或没有权限。" + jsonStr);
			} else{
				
				cashQueue = cashQueueService.getCashQueueById(cashQueue.getId());
				
				if (cashQueue != null){
					
					cashQueue.setStep(BusinessCenterCashQueueStatus.CASHQUEUE_STATUS_CANCEL.getId());
					cashQueueService.updateCashQueue(cashQueue);
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
//			logger.error("< CashQueueController.cancel() > 挂起任务失败." + e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
//			logger.error("< CashQueueController.cancel() > 挂起任务返回出错." + e.getMessage());
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
			
			CashQueue cashQueue = JsonConverter.getFromJsonString(jsonStr,
					CashQueue.class);

			if (StringUtil.isNull(jsonStr) || cashQueue == null ) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< CashQueueController.finish() > 维修订单信息为空或没有权限。" + jsonStr);
			}else{
				cashQueue = cashQueueService.getCashQueueById(cashQueue.getId());
				
				if (cashQueue.getStep() == BusinessCenterCashQueueStatus.CASHQUEUE_STATUS_START.getId()){
					
					Order order = orderService.queryOrderById(cashQueue.getOrderId());
					order.setStatus(BusinessCenterOrderStatus.ORDER_STATUS_WASH.getId());
					orderService.updateOrder(order);              //修改订单状态
					
					Date now = new Date();
					java.sql.Timestamp dateTime = new java.sql.Timestamp(now.getTime());
					
					cashQueue.setEndTime(dateTime);
					cashQueue.setStep(BusinessCenterCashQueueStatus.CASHQUEUE_STATUS_FINISH.getId());
					
					cashQueueService.updateCashQueue(cashQueue);   //更新cashQueue订单
				}
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
//			logger.error("< CashQueueController.finish() > 维修订单发送清洗车间失败." + e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
//			logger.error("< CashQueueController.finish() > 发送清洗车间返回出错." + e.getMessage());
			throw e;
		}
	}
	
}
