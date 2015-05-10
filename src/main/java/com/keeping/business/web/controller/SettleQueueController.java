package com.keeping.business.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.common.rescode.BusinessCenterOrderStatus;
import com.keeping.business.common.rescode.BusinessCenterResCode;
import com.keeping.business.common.rescode.BusinessCenterSettleQueueStatus;
import com.keeping.business.common.util.StringUtil;
import com.keeping.business.service.OrderService;
import com.keeping.business.service.SettleQueueService;
import com.keeping.business.service.UserService;
import com.keeping.business.web.controller.converter.JsonConverter;
import com.keeping.business.web.controller.converter.ReorgQueue;
import com.keeping.business.web.controller.model.IdObject;
import com.keeping.business.web.controller.model.Order;
import com.keeping.business.web.controller.model.SettleQueue;
import com.keeping.business.web.controller.model.StepObject;
import com.keeping.business.web.controller.model.User;
import com.keeping.business.web.controller.model.WebResult;
import com.keeping.business.web.controller.model.WebResultList;
import com.keeping.business.web.controller.model.WebResultObject;

@Controller
@RequestMapping("/settlequeue.do")
public class SettleQueueController {

	/** 日志 */
//	private Logger logger = LoggerFactory.getLogger(UserController.class);

	/** 用户信息Service */
	@Resource
	private SettleQueueService settleQueueService;
	@Resource
	private OrderService orderService;
	@Resource
	private UserService userService;

	@RequestMapping(params = "action=alllist")
	@ResponseBody
	public WebResultList<SettleQueue> getsettlequeues(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		
		List<SettleQueue> settlequeues = new ArrayList<SettleQueue>();
		
		try {
			
			String jsonStr = request.getParameter("param");
			StepObject step = JsonConverter.getFromJsonString(jsonStr,
					StepObject.class);
			
			if (null == step || step.getStep() == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< settlequeueController.getsettlequeue() > 获取维修订单请求信息不正确: " + step.getStep());
			} else {
				
				settlequeues = settleQueueService.getSettleQueueByStep(Integer.parseInt(step.getStep()));
				
				List<Integer> userIdList = new ArrayList<Integer>();
				List<Integer> orderIdList = new ArrayList<Integer>();
				List<User> users = new ArrayList<User>();
				for (int i=0; i<settlequeues.size(); i++){
					userIdList.add(settlequeues.get(i).getUserId());
					orderIdList.add(settlequeues.get(i).getOrderId());
					
					User user = userService.getByUserId(userIdList.get(i));
					users.add(user);
				}
				
				if(settlequeues.size() > 0){
					
					List<Order> orders = orderService.getByOrdersId(orderIdList);
					
					ReorgQueue.reorgSettleQueue(settlequeues, users, orders);
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
//			logger.error("< settlequeueController.getsettlequeue() > 获取维修列表失败." + e.getMessage());
		}
		
		return JsonConverter.getResultObject(code, msg, settlequeues);
	}
	
	@RequestMapping(params = "action=getone")
	@ResponseBody
	public WebResultObject<SettleQueue> getsettlequeue(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();

		SettleQueue settlequeue = new SettleQueue();
		
		try {
			
			String jsonStr = request.getParameter("param");
			IdObject idObject = JsonConverter.getFromJsonString(jsonStr,IdObject.class);
			
			if (null == idObject || idObject.getId() == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< settlequeueController.getsettlequeue() > 获取维修订单请求信息不正确: " + idObject.getId());
			} else {
				
				settlequeue = settleQueueService.getSettleQueueById(idObject.getId());
				
				if (settlequeue != null && settlequeue.getId() != null){
					Integer userId = settlequeue.getUserId();
					Integer orderId = settlequeue.getOrderId();
					
					User user = userService.getByUserId(userId);
					Order order = orderService.queryOrderById(orderId);

					settlequeue.setUser(user);
					settlequeue.setOrder(order);
					
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
//			logger.error("< settlequeueController.getsettlequeue() > 获取维修列表失败." + e.getMessage());
		}
		
		return JsonConverter.getResultObject(code, msg, settlequeue);
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
			
			SettleQueue settlequeue = JsonConverter.getFromJsonString(jsonStr,
					SettleQueue.class);
	
			if (StringUtil.isNull(jsonStr) || settlequeue == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< settlequeueController.start() > 订单维修订单信息为空或没有权限。" + jsonStr);
			} else{
				
				settlequeue = settleQueueService.getSettleQueueById(settlequeue.getId());
				
				if (settlequeue != null){
					
					settlequeue.setStep(BusinessCenterSettleQueueStatus.SETTLEQUEUE_STATUS_START.getId());
					settleQueueService.updateSettleQueue(settlequeue);
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
//			logger.error("< settlequeueController.start() > 挂起任务失败." + e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
//			logger.error("< settlequeueController.start() > 挂起任务返回出错." + e.getMessage());
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
			
			SettleQueue settlequeue = JsonConverter.getFromJsonString(jsonStr,
					SettleQueue.class);
	
			if (StringUtil.isNull(jsonStr) || settlequeue == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< settlequeueController.cancel() > 订单维修订单信息为空或没有权限。" + jsonStr);
			} else{
				
				settlequeue = settleQueueService.getSettleQueueById(settlequeue.getId());
				
				if (settlequeue != null){
					
					settlequeue.setStep(BusinessCenterSettleQueueStatus.SETTLEQUEUE_STATUS_CANCEL.getId());
					settleQueueService.updateSettleQueue(settlequeue);
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
//			logger.error("< settlequeueController.cancel() > 挂起任务失败." + e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
//			logger.error("< settlequeueController.cancel() > 挂起任务返回出错." + e.getMessage());
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
			
			SettleQueue settlequeue = JsonConverter.getFromJsonString(jsonStr,
					SettleQueue.class);

			if (StringUtil.isNull(jsonStr) || settlequeue == null ) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< settlequeueController.finish() > 维修订单信息为空或没有权限。" + jsonStr);
			}else{
				settlequeue = settleQueueService.getSettleQueueById(settlequeue.getId());
				
				if (settlequeue.getStep() == BusinessCenterSettleQueueStatus.SETTLEQUEUE_STATUS_START.getId()){
					
					Order order = orderService.queryOrderById(settlequeue.getOrderId());
					order.setStatus(BusinessCenterOrderStatus.ORDER_STATUS_DONE.getId());
					orderService.updateOrder(order);              //修改订单状态
					
					Date now = new Date();
					java.sql.Timestamp dateTime = new java.sql.Timestamp(now.getTime());
					
					settlequeue.setEndTime(dateTime);
					settlequeue.setStep(BusinessCenterSettleQueueStatus.SETTLEQUEUE_STATUS_FINISH.getId());
					
					settleQueueService.updateSettleQueue(settlequeue);   //更新settlequeue订单
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
//			logger.error("< settlequeueController.finish() > 维修订单发送清洗车间失败." + e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
//			logger.error("< settlequeueController.finish() > 发送清洗车间返回出错." + e.getMessage());
			throw e;
		}
	}
	
}
