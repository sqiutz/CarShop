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
	public WebResultList<ServeQueue> getAllServeQueues(Integer step,
			HttpServletRequest request, HttpServletResponse response) {
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();

		List<ServeQueue> serveQueueList = new ArrayList<ServeQueue>();
		try {
			if (null == step) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< ServeQueueController.getAllServeQueues() > 获取服务订单列表请求信息不正确: " + step);
			} else {
				serveQueueList = serveQueueService.getServeQueueByStep(step);
				
				List<Integer> userIdList = new ArrayList<Integer>();
				List<Integer> orderIdList = new ArrayList<Integer>();
				for (int i=0; i<serveQueueList.size(); i++){
					userIdList.add(serveQueueList.get(i).getUser_id());
					orderIdList.add(serveQueueList.get(i).getOrder_id());
				}
				
				List<User> users = userService.getByUsersId(userIdList);
				List<Order> orders = orderService.getByOrdersId(orderIdList);
				
				ReorgQueue.reorgServeQueue(serveQueueList, users, orders);   //need to verify
				
			}
		} catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		} catch (Exception e) {
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
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);
		response.setHeader("Access-Control-Allow-Origin", "*");

		try {
			String jsonStr = request.getParameter("param");
			UserProfile loginUser = (UserProfile) session
					.getAttribute(PlatfromConstants.STR_USER_PROFILE);

			if (StringUtil.isNull(jsonStr) || loginUser == null ) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< ServeQueueController.call() > 注册用户信息为空或没有权限。"
						+ jsonStr);
			} else if (null == session || null == loginUser || null == loginUser.getUserName()){
				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
				logger.error("< ServeQueueController.call() > session is null。" + jsonStr);
			} else if (loginUser.getGroupId() != BusinessCenterUserGroup.SYS_SERVICER.getId()){
				code = BusinessCenterResCode.SYS_NO_ADMIN.getCode();
				msg = BusinessCenterResCode.SYS_NO_ADMIN.getMsg();
				logger.error("< ServeQueueController.call() > you are not role。" + jsonStr);
			}else{
				Order order = orderService.queryFirstForServeQueue();
				order.setStatus(BusinessCenterOrderStatus.ORDER_STATUS_SERVE.getId());
				orderService.updateOrder(order);              //修改订单状态
				
				Date now = new Date();
				java.sql.Timestamp dateTime = new java.sql.Timestamp(now.getTime());
				
				ServeQueue serveQueue = new ServeQueue();
				serveQueue.setStartTime(dateTime);
				serveQueue.setOrder_id(order.getId());
				serveQueue.setStep(0);
				serveQueue.setUser_id(loginUser.getId());
				
				serveQueueService.addServeQueue(serveQueue);   //添加ServeQueue订单
			}
		} catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		} catch (Exception e) {
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

}
