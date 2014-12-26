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
import com.keeping.business.web.controller.model.EstimationTime;
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
@RequestMapping("/servequeue.do")
public class ServeQueueController {

	/** 日志 */
	private Logger logger = LoggerFactory.getLogger(UserController.class);

	/** 用户信息Service */
	@Resource
	private ModifyQueueService modifyQueueService;
	@Resource
	private ServeQueueService serveQueueService;
	@Resource
	private OrderService orderService;
	@Resource
	private UserService userService;

	/**
	 * 获取订单列表
	 * 
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return
	 * @return N/A
	 */
	@RequestMapping(params = "action=alllist")
	@ResponseBody
	public WebResultList<ServeQueue> getAllServeQueues(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();

		List<ServeQueue> serveQueueList = new ArrayList<ServeQueue>();
		try {
			String jsonStr = request.getParameter("param");
			StepObject step = JsonConverter.getFromJsonString(jsonStr,
					StepObject.class);
			if (null == step) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< ServeQueueController.getAllServeQueues() > 获取服务订单列表请求信息不正确: " + step);
			} else {
				serveQueueList = serveQueueService.getServeQueueByStep(Integer.parseInt(step.getStep()));
				System.out.println("retrun from serveQueueService " + serveQueueList.size());
				List<Integer> userIdList = new ArrayList<Integer>();
				List<Integer> orderIdList = new ArrayList<Integer>();
				List<User> users = new ArrayList<User>();
				for (int i=0; i<serveQueueList.size(); i++){
					System.out.println("serveQueueList.get(i).getUserId() " + serveQueueList.get(i).getUserId());
					userIdList.add(serveQueueList.get(i).getUserId());
					System.out.println("serveQueueList.get(i).getOrderId() " + serveQueueList.get(i).getOrderId());
					orderIdList.add(serveQueueList.get(i).getOrderId());
					
					User user = userService.getByUserId(userIdList.get(i));
					users.add(user);
					
					Date now = new Date();
					java.sql.Timestamp dateTime = new java.sql.Timestamp(now.getTime());
					
					Long timeInterval = dateTime.getTime() - serveQueueList.get(i).getStartTime().getTime();
					Integer elapseTime = new Integer(timeInterval.intValue());
					serveQueueList.get(i).setDelayTime(elapseTime);
				}
				
				if(serveQueueList.size() > 0){
					
					List<Order> orders = orderService.getByOrdersId(orderIdList);
					System.out.println("retrun from orderService " + orders.size());
					
					if(step.getIsBook() != null && 1 == step.getIsBook()){
						ReorgQueue.reorgBookServeQueue(serveQueueList, users, orders);
					}else if (step.getIsBook() != null && step.getIsBook() == 0){
						ReorgQueue.reorgNoBookServeQueue(serveQueueList, users, orders);   
					}else{
						ReorgQueue.reorgServeQueue(serveQueueList, users, orders);
					}
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
			logger.error("< ServeQueueController.getAllServeQueues() > 获取排队列表失败."
					+ e.getMessage());
		}

		return JsonConverter.getResultObject(code, msg, serveQueueList);
	}
	
	@RequestMapping(params = "action=getestimationtime")
	@ResponseBody
	public WebResultList<EstimationTime> getAllEstimationTime(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();

		List<EstimationTime> estimationTimes = new ArrayList<EstimationTime>();
		try {
			String jsonStr = request.getParameter("param");
			EstimationTime estimationTime = JsonConverter.getFromJsonString(jsonStr,
					EstimationTime.class);
			if (null == estimationTime) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< ServeQueueController.getAllEstimationTime() > 查询订单时间信息请求信息不正确: " + estimationTime.getStartTime());
			} else {
				
				estimationTimes = serveQueueService.getElapseTimeByTime(estimationTime.getStartTime(), estimationTime.getEndTime());
				
			}
		} catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
			logger.error("< ServeQueueController.getAllEstimationTime() > 获取排队列表预估时间失败."
					+ e.getMessage());
		}

		return JsonConverter.getResultObject(code, msg, estimationTimes);
	}


	@RequestMapping(params = "action=getone")
	@ResponseBody
	public WebResultList<ServeQueue> getServeQueue(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);

		List<ServeQueue> serveQueueList = new ArrayList<ServeQueue>();
		try {
			UserProfile logUser = (UserProfile) session
					.getAttribute(PlatfromConstants.STR_USER_PROFILE);
			
			String jsonStr = request.getParameter("param");
			StepObject step = JsonConverter.getFromJsonString(jsonStr,
					StepObject.class);
			if (null == step) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< ServeQueueController.getServeQueue() > 获取服务订单请求信息不正确: " + step);
			} else if (null == session || null == logUser || null == logUser.getUserName()){
				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
				logger.error("< ServeQueueController.getServeQueue() > session is null." + jsonStr);
			}  
			else {
				ServeQueue serveQueue = new ServeQueue();
				serveQueue.setStep(Integer.parseInt(step.getStep()));
				serveQueue.setUserId(logUser.getId());
				serveQueueList = serveQueueService.getServeQueueByStepAndUserId(serveQueue);
				
				List<Integer> userIdList = new ArrayList<Integer>();
				List<Integer> orderIdList = new ArrayList<Integer>();
				List<User> users = new ArrayList<User>();
				for (int i=0; i<serveQueueList.size(); i++){
					System.out.println("serveQueueList.get(i).getUserId() " + serveQueueList.get(i).getUserId());
					userIdList.add(serveQueueList.get(i).getUserId());
					System.out.println("serveQueueList.get(i).getOrderId() " + serveQueueList.get(i).getOrderId());
					orderIdList.add(serveQueueList.get(i).getOrderId());
				
					User user = userService.getByUserId(userIdList.get(i));
					users.add(user);
					
					Date now = new Date();
					java.sql.Timestamp dateTime = new java.sql.Timestamp(now.getTime());
					
					Long timeInterval = dateTime.getTime() - serveQueueList.get(i).getStartTime().getTime();
					Integer elapseTime = new Integer(timeInterval.intValue());
					serveQueueList.get(i).setDelayTime(elapseTime);
				}
				
				if(serveQueueList.size() > 0){

					List<Order> orders = orderService.getByOrdersId(orderIdList);
					System.out.println("retrun from orderService " + orders.size());
					
					ReorgQueue.reorgServeQueue(serveQueueList, users, orders);   //need to verify
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
			logger.error("< ServeQueueController.getAllServeQueues() > 获取排队列表失败."
					+ e.getMessage());
		}

		return JsonConverter.getResultObject(code, msg, serveQueueList);
	}

	@RequestMapping(params = "action=getlatestone")
	@ResponseBody
	public WebResultObject<ServeQueue> getLatestServeQueue(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);

		List<ServeQueue> serveQueueList = new ArrayList<ServeQueue>();
		try {
			
			String jsonStr = request.getParameter("param");
			StepObject step = JsonConverter.getFromJsonString(jsonStr,
					StepObject.class);
			
			if (null == step) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< ServeQueueController.getServeQueue() > 获取服务订单请求信息不正确: " + step);
			} else {
				
				serveQueueList = serveQueueService.getServeQueueByStep(BusinessCenterServeQueueStatus.SERVEQUEUE_STATUS_SERVING.getId());
				
				List<Integer> userIdList = new ArrayList<Integer>();
				List<Integer> orderIdList = new ArrayList<Integer>();
				List<User> users = new ArrayList<User>();
				for (int i=0; i<serveQueueList.size(); i++){
					userIdList.add(serveQueueList.get(i).getUserId());
					orderIdList.add(serveQueueList.get(i).getOrderId());
				
					User user = userService.getByUserId(userIdList.get(i));
					users.add(user);
				}
				
				if(serveQueueList.size() > 0){

					List<Order> orders = orderService.getByOrdersId(orderIdList);
					
					ReorgQueue.reorgServeQueue(serveQueueList, users, orders);   //need to verify
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
			logger.error("< ServeQueueController.getAllServeQueues() > 获取排队列表失败."
					+ e.getMessage());
		}

		return JsonConverter.getResultObject(code, msg, serveQueueList.get(0));
	}
	
	/**
	 * 添加serveQueue订单
	 * 
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return
	 * @return N/A
	 * @throws Exception
	 */
	@RequestMapping(params = "action=call")
	@ResponseBody
	public WebResult call(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);
		response.setHeader("Access-Control-Allow-Origin", "*");

		try {
//			String jsonStr = request.getParameter("param");
			UserProfile loginUser = (UserProfile) session
					.getAttribute(PlatfromConstants.STR_USER_PROFILE);

//			if (StringUtil.isNull(jsonStr) || loginUser == null ) {
//				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
//				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< ServeQueueController.call() > 注册用户信息为空或没有权限。"
//						+ jsonStr);
//			} else 
			if (null == session || null == loginUser || null == loginUser.getUserName()){
				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
				logger.error("< ServeQueueController.call() > session is null。");
			} else if (null == loginUser.getGroupName() && loginUser.getGroupName()!= BusinessCenterUserGroup.SYS_SERVICER.getGroupName()){
				code = BusinessCenterResCode.SYS_NO_ADMIN.getCode();
				msg = BusinessCenterResCode.SYS_NO_ADMIN.getMsg();
				logger.error("< ServeQueueController.call() > you are not role。");
			}else{
				
				ServeQueue serveQueueV = new ServeQueue();
				serveQueueV.setStep(0);
				serveQueueV.setUserId(loginUser.getId());
				List<ServeQueue> serveQueueList = serveQueueService.getServeQueueByStepAndUserId(serveQueueV);
				
				User user = userService.queryUserByName(loginUser.getUserName());
				
				if (serveQueueList != null && serveQueueList.size() == 0 && user.getIsBooker() != null){
				
				Order order = orderService.queryFirstForServeQueue(user.getIsBooker());
				if (order != null && order.getId() == null){
					Integer booker = user.getIsBooker() > 0 ? 0 : 1;
					order = orderService.queryFirstForServeQueue(booker);
				}
				if (order != null && order.getId() != null){
					order.setStatus(BusinessCenterOrderStatus.ORDER_STATUS_SERVE.getId());
					orderService.updateOrder(order);              //修改订单状态
					
					Date now = new Date();
					java.sql.Timestamp dateTime = new java.sql.Timestamp(now.getTime());
					
					ServeQueue serveQueue = new ServeQueue();
					serveQueue.setStartTime(dateTime);
					serveQueue.setEndTime(dateTime);
					serveQueue.setModifyTime(dateTime);
					serveQueue.setOrderId(order.getId());
					serveQueue.setStep(BusinessCenterServeQueueStatus.SERVEQUEUE_STATUS_SERVING.getId());
					serveQueue.setUserId(loginUser.getId());
					
					serveQueueService.addServeQueue(serveQueue);   //添加ServeQueue订单
				}else{
					code = BusinessCenterResCode.ORDER_NOT_EXIST.getCode();
					msg = BusinessCenterResCode.ORDER_NOT_EXIST.getMsg();
				}
				}else{
					code = BusinessCenterResCode.ORDER_EXIST.getCode();
					msg = BusinessCenterResCode.ORDER_EXIST.getMsg();
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
			logger.error("< OrderController.startOrder() > 取号预约失败."
					+ e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
			logger.error("< OrderController.startOrder() > 取号预约返回出错."
					+ e.getMessage());
			throw e;
		}
	}

	/**
	 * 添加serveQueue订单
	 * 
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return
	 * @return N/A
	 * @throws Exception
	 */
	@RequestMapping(params = "action=hold")
	@ResponseBody
	public WebResult hold(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);
		response.setHeader("Access-Control-Allow-Origin", "*");

		try {
			String jsonStr = request.getParameter("param");
			UserProfile loginUser = (UserProfile) session
					.getAttribute(PlatfromConstants.STR_USER_PROFILE);
			System.out.println(jsonStr);
			ServeQueue serveQueue = JsonConverter.getFromJsonString(jsonStr,
					ServeQueue.class);
			System.out.println("ServeQueue " + (null == serveQueue ? "null" : serveQueue.getId()));

			if (StringUtil.isNull(jsonStr) || serveQueue == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< ServeQueueController.hold() > 订单信息为空或没有权限。"
						+ jsonStr);
			} else if (null == session || null == loginUser || null == loginUser.getUserName()){
				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
				logger.error("< ServeQueueController.hold() > session is null。");
			} else if (null == loginUser.getGroupName() && loginUser.getGroupName()!= BusinessCenterUserGroup.SYS_SERVICER.getGroupName()){
				code = BusinessCenterResCode.SYS_NO_ADMIN.getCode();
				msg = BusinessCenterResCode.SYS_NO_ADMIN.getMsg();
				logger.error("< ServeQueueController.hold() > you are not role。");
			}else{
				
				serveQueue = serveQueueService.getServeQueueById(serveQueue.getId());
				
				if (serveQueue != null){
					Order order = orderService.queryOrderById(serveQueue.getOrderId());
					
					if (order != null){
						order.setStatus(BusinessCenterOrderStatus.ORDER_STATUS_HOLD.getId());
						orderService.updateOrder(order);
					}
					
					Date now = new Date();
					java.sql.Timestamp dateTime = new java.sql.Timestamp(now.getTime());
					
					Long timeInterval = dateTime.getTime() - serveQueue.getStartTime().getTime();
					Integer elapseTime = new Integer(timeInterval.intValue());
					elapseTime = serveQueue.getElapseTime() + elapseTime;
					
					serveQueue.setElapseTime(elapseTime);
					serveQueue.setStartTime(dateTime);
					serveQueue.setEndTime(dateTime);
					serveQueue.setModifyTime(dateTime);
					serveQueue.setStep(BusinessCenterServeQueueStatus.SERVEQUEUE_STATUS_HOLD.getId());
					serveQueueService.updateServeQueue(serveQueue);
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
			logger.error("< ServeQueueController.hold() > 挂起任务失败."
					+ e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
			logger.error("< ServeQueueController.hold() > 挂起任务返回出错."
					+ e.getMessage());
			throw e;
		}
	}
	
	/**
	 * 添加serveQueue订单
	 * 
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return
	 * @return N/A
	 * @throws Exception
	 */
	@RequestMapping(params = "action=cancelhold")
	@ResponseBody
	public WebResult cancelHold(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);
		response.setHeader("Access-Control-Allow-Origin", "*");

		try {
			String jsonStr = request.getParameter("param");
			UserProfile loginUser = (UserProfile) session
					.getAttribute(PlatfromConstants.STR_USER_PROFILE);
			
			ServeQueue serveQueue = JsonConverter.getFromJsonString(jsonStr,
					ServeQueue.class);

			if (StringUtil.isNull(jsonStr) || serveQueue == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< ServeQueueController.hold() > 订单信息为空或没有权限。"
						+ jsonStr);
			} else if (null == session || null == loginUser || null == loginUser.getUserName()){
				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
				logger.error("< ServeQueueController.hold() > session is null。");
			} else if (null == loginUser.getGroupName() && loginUser.getGroupName()!= BusinessCenterUserGroup.SYS_SERVICER.getGroupName()){
				code = BusinessCenterResCode.SYS_NO_ADMIN.getCode();
				msg = BusinessCenterResCode.SYS_NO_ADMIN.getMsg();
				logger.error("< ServeQueueController.hold() > you are not role。");
			}else{
			
				serveQueue = serveQueueService.getServeQueueById(serveQueue.getId());
				
				if (serveQueue != null){
					Order order = orderService.queryOrderById(serveQueue.getOrderId());
					
					if (order != null){
						order.setStatus(BusinessCenterOrderStatus.ORDER_STATUS_SERVE.getId());
						orderService.updateOrder(order);
					}
					
					Date now = new Date();
					java.sql.Timestamp dateTime = new java.sql.Timestamp(now.getTime());
					
					serveQueue.setStartTime(dateTime);
					serveQueue.setModifyTime(dateTime);
					serveQueue.setUserId(loginUser.getId());
					serveQueue.setStep(BusinessCenterServeQueueStatus.SERVEQUEUE_STATUS_SERVING.getId());
					serveQueueService.updateServeQueue(serveQueue);
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
			logger.error("< ServeQueueController.cancelhold() > 取号预约失败."
					+ e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
			logger.error("< ServeQueueController.startOrder() > 取号预约返回出错."
					+ e.getMessage());
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
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);
		response.setHeader("Access-Control-Allow-Origin", "*");

		try {
			String jsonStr = request.getParameter("param");
			UserProfile loginUser = (UserProfile) session
					.getAttribute(PlatfromConstants.STR_USER_PROFILE);
			
			ServeQueue serveQueue = JsonConverter.getFromJsonString(jsonStr,
					ServeQueue.class);

			if (StringUtil.isNull(jsonStr) || serveQueue == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< ServeQueueController.cancel() > 订单信息为空或没有权限。"
						+ jsonStr);
			} else if (null == session || null == loginUser || null == loginUser.getUserName()){
				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
				logger.error("< ServeQueueController.cancel() > session is null。");
			} else if (null == loginUser.getGroupName() && loginUser.getGroupName()!= BusinessCenterUserGroup.SYS_ADMIN.getGroupName()){
				code = BusinessCenterResCode.SYS_NO_ADMIN.getCode();
				msg = BusinessCenterResCode.SYS_NO_ADMIN.getMsg();
				logger.error("< ServeQueueController.cancel() > you are not role。");
			}else{
			
				serveQueue = serveQueueService.getServeQueueById(serveQueue.getId());
				
				if (serveQueue != null){
					Order order = orderService.queryOrderById(serveQueue.getOrderId());
					
					if (order != null){
						order.setStatus(BusinessCenterOrderStatus.ORDER_STATUS_CANCEL.getId());
						orderService.updateOrder(order);
					}
					
					Date now = new Date();
					java.sql.Timestamp dateTime = new java.sql.Timestamp(now.getTime());
					
					serveQueue.setEndTime(dateTime);
					serveQueue.setModifyTime(dateTime);
					serveQueue.setStep(BusinessCenterServeQueueStatus.SERVEQUEUE_STATUS_CANCEL.getId());
					serveQueueService.updateServeQueue(serveQueue);
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
			logger.error("< ServeQueueController.cancel() > 取号预约失败."
					+ e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
			logger.error("< ServeQueueController.cancel() > 取号预约返回出错."
					+ e.getMessage());
			throw e;
		}
	}
	
	@RequestMapping(params = "action=send")
	@ResponseBody
	public WebResult sendWorkShop(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);
		response.setHeader("Access-Control-Allow-Origin", "*");

		try {
			String jsonStr = request.getParameter("param");
			UserProfile logUser = (UserProfile) session
					.getAttribute(PlatfromConstants.STR_USER_PROFILE);
			
			ServeQueue serveQueue = JsonConverter.getFromJsonString(jsonStr,
					ServeQueue.class);

			if (StringUtil.isNull(jsonStr) || serveQueue == null ) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< ServeQueueController.sendWorkShop() > 发送车间信息为空或没有权限。"
						+ jsonStr);
			} else if (null == session || null == logUser || null == logUser.getUserName()){
				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
				logger.error("< ServeQueueController.sendWorkShop() > session is null。");
			} else{
				serveQueue = serveQueueService.getServeQueueById(serveQueue.getId());
				Order order = orderService.queryOrderById(serveQueue.getOrderId());
				order.setStatus(BusinessCenterOrderStatus.ORDER_STATUS_MODIFY.getId());
				orderService.updateOrder(order);              //修改订单状态
				
				Date now = new Date();
				java.sql.Timestamp dateTime = new java.sql.Timestamp(now.getTime());
				
				Long timeInterval = dateTime.getTime() - serveQueue.getStartTime().getTime();
				Integer elapseTime = new Integer(timeInterval.intValue());
				elapseTime = serveQueue.getElapseTime() + elapseTime;
				
				serveQueue.setElapseTime(elapseTime);
				serveQueue.setEndTime(dateTime);
				serveQueue.setStep(BusinessCenterServeQueueStatus.SERVEQUEUE_STATUS_SEND.getId());
				
				serveQueueService.updateServeQueue(serveQueue);   //添加ServeQueue订单
				
				ModifyQueue modifyQueue = new ModifyQueue();
				modifyQueue.setStep(BusinessCenterModifyQueueStatus.MODIFYQUEUE_STATUS_MODIFYING.getId());
				modifyQueue.setOrderId(serveQueue.getOrderId());
				modifyQueue.setUserId(logUser.getId());
				
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
			logger.error("< OrderController.sendWorkShop() > 发送车间失败."
					+ e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
			logger.error("< OrderController.sendWorkShop() > 发送车间返回出错."
					+ e.getMessage());
			throw e;
		}
	}
	
}
