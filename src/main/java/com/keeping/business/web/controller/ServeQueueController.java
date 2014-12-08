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
import com.keeping.business.common.rescode.BusinessCenterOrderStatus;
import com.keeping.business.common.rescode.BusinessCenterResCode;
import com.keeping.business.common.rescode.BusinessCenterUserGroup;
import com.keeping.business.common.util.PlatformPar;
import com.keeping.business.common.util.PlatfromConstants;
import com.keeping.business.common.util.StringUtil;
import com.keeping.business.service.OrderService;
import com.keeping.business.service.ServeQueueService;
import com.keeping.business.service.UserService;
import com.keeping.business.web.controller.converter.JsonConverter;
import com.keeping.business.web.controller.converter.ReorgQueue;
import com.keeping.business.web.controller.converter.WebUserConverter;
import com.keeping.business.web.controller.model.LoginReq;
import com.keeping.business.web.controller.model.Order;
import com.keeping.business.web.controller.model.ServeQueue;
import com.keeping.business.web.controller.model.StepObject;
import com.keeping.business.web.controller.model.User;
import com.keeping.business.web.controller.model.UserProfile;
import com.keeping.business.web.controller.model.WebResult;
import com.keeping.business.web.controller.model.WebResultList;

@Controller
@RequestMapping("/servequeue.do")
public class ServeQueueController {

	/** 日志 */
	private Logger logger = LoggerFactory.getLogger(UserController.class);

	/** 用户信息Service */
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
				for (int i=0; i<serveQueueList.size(); i++){
					System.out.println("serveQueueList.get(i).getUserId() " + serveQueueList.get(i).getUserId());
					userIdList.add(serveQueueList.get(i).getUserId());
					System.out.println("serveQueueList.get(i).getOrderId() " + serveQueueList.get(i).getOrderId());
					orderIdList.add(serveQueueList.get(i).getOrderId());

				}
				
				if(serveQueueList.size() > 0){
					
					List<User> users = userService.getByUsersId(userIdList);
					System.out.println("retrun from userService " + users.size());
					List<Order> orders = orderService.getByOrdersId(orderIdList);
					System.out.println("retrun from orderService " + orders.size());
					
					if(null == step.getIsBook() || step.getIsBook() == 0){
						ReorgQueue.reorgNoBookServeQueue(serveQueueList, users, orders);   
					}else{
						ReorgQueue.reorgBookServeQueue(serveQueueList, users, orders);
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
				for (int i=0; i<serveQueueList.size(); i++){
					System.out.println("serveQueueList.get(i).getUserId() " + serveQueueList.get(i).getUserId());
					userIdList.add(serveQueueList.get(i).getUserId());
					System.out.println("serveQueueList.get(i).getOrderId() " + serveQueueList.get(i).getOrderId());
					orderIdList.add(serveQueueList.get(i).getOrderId());
				
				}
				
				if(serveQueueList.size() > 0){
					
					List<User> users = userService.getByUsersId(userIdList);
					System.out.println("retrun from userService " + users.size());
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
					serveQueue.setOrderId(order.getId());
					serveQueue.setStep(0);
					serveQueue.setUserId(loginUser.getId());
					
					serveQueueService.addServeQueue(serveQueue);   //添加ServeQueue订单
				}else{
					code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
					msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
				}
				}else{
					code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
					msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
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
//			String jsonStr = request.getParameter("param");
			UserProfile logUser = (UserProfile) session
					.getAttribute(PlatfromConstants.STR_USER_PROFILE);

//			if (StringUtil.isNull(jsonStr) || loginUser == null ) {
//				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
//				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< ServeQueueController.sendWorkShop() > 发送车间信息为空或没有权限。"
//						+ jsonStr);
//			} else 
			if (null == session || null == logUser || null == logUser.getUserName()){
				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
				logger.error("< ServeQueueController.sendWorkShop() > session is null。");
			} else if (logUser.getGroupId() != BusinessCenterUserGroup.SYS_SERVICER.getId()){
				code = BusinessCenterResCode.SYS_NO_ADMIN.getCode();
				msg = BusinessCenterResCode.SYS_NO_ADMIN.getMsg();
				logger.error("< ServeQueueController.sendWorkShop() > you are not role。");
			}else{
				
				ServeQueue serveQueue = new ServeQueue();
				serveQueue.setStep(0);
				serveQueue.setUserId(logUser.getId());
				List<ServeQueue> serveQueues = serveQueueService.getServeQueueByStepAndUserId(serveQueue);
				
				serveQueue = serveQueues.get(0);
				
				Order order = orderService.queryOrderById(serveQueue.getOrderId());
				order.setStatus(BusinessCenterOrderStatus.ORDER_STATUS_MODIFY.getId());
				orderService.updateOrder(order);              //修改订单状态
				
				Date now = new Date();
				java.sql.Timestamp dateTime = new java.sql.Timestamp(now.getTime());
				
				serveQueue.setEndTime(dateTime);
				serveQueue.setStep(3);
				
				serveQueueService.updateServeQueue(serveQueue);   //添加ServeQueue订单
				
				//modifyQueueService.addModifyQueue()
			}
		} catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		} catch (Exception e) {
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
