package com.keeping.business.web.controller;

import java.sql.Timestamp;
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
import com.keeping.business.common.rescode.BusinessCenterResCode;
import com.keeping.business.common.util.PlatformPar;
import com.keeping.business.common.util.PlatfromConstants;
import com.keeping.business.common.util.StringUtil;
import com.keeping.business.service.OrderService;
import com.keeping.business.web.controller.converter.JsonConverter;
import com.keeping.business.web.controller.model.Order;
import com.keeping.business.web.controller.model.UserProfile;
import com.keeping.business.web.controller.model.WebResult;
import com.keeping.business.web.controller.model.WebResultList;

@Controller
@RequestMapping("/order.do")
public class OrderController {

    /**日志 */
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    
	/**用户信息Service */
    @Resource
	private OrderService orderService;
    
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
	public WebResultList<Order> getAllOrders(Integer status, HttpServletRequest request,HttpServletResponse response) {
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();

		List<Order> orderList = new ArrayList<Order>();
		try {
			orderList = orderService.getOrdersByStatus(status);
		}catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		}catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
			logger.error("< OrderController.getAllOrders() > 获取订单列表失败." + e.getMessage());
		}

		return JsonConverter.getResultObject(code, msg, orderList);
	}
	
	/**
     * 预约订单
     * 
     * @param HttpServletRequest
     * @param HttpServletResponse
	 * @return 
     * @return N/A
	 * @throws Exception 
     */
	@RequestMapping(params = "action=book")
	@ResponseBody
	public WebResult bookOrder(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);

		response.setHeader("Access-Control-Allow-Origin", "*");
		
		try {
			
			UserProfile loginUser = (UserProfile) session.getAttribute(PlatfromConstants.STR_USER_PROFILE);
			
			String jsonStr = request.getParameter("param");
			Order order = JsonConverter.getFromJsonString(jsonStr, Order.class);
			 
			if (StringUtil.isNull(jsonStr) || null == order) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< OrderController.bookOrder() > 订单预约请求信息不正确。" + jsonStr);
			}else if (null == session || null == loginUser || null == loginUser.getUserName()){
				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
				logger.error("< OrderController.bookOrder() > session为空。" + jsonStr);
			} else {
				
				 String bookNumber = System.currentTimeMillis() + "";
				 order.setBookNum(bookNumber);
				 orderService.addOrder(order);
			}
		}catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		}catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
			logger.error("< OrderController.bookOrder() > 订单预约失败." + e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
			session.removeAttribute(PlatfromConstants.STR_USER_PROFILE);
			session.invalidate();
			logger.error("< OrderController.bookOrder() > 订单预约返回出错."
					+ e.getMessage());
			throw e;
		}
	}
	
	/**
     * 预约订单
     * 
     * @param HttpServletRequest
     * @param HttpServletResponse
	 * @return 
     * @return N/A
	 * @throws Exception 
     */
	@RequestMapping(params = "action=start")
	@ResponseBody
	public WebResult startOrder(String bookNum, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		try {
			Order order = new Order();
			
			Date now = new Date();
			java.sql.Timestamp dateTime = new java.sql.Timestamp(now.getTime());
			
			if (StringUtil.isNull(bookNum)) {
				order.setStartTime(dateTime);
				order.setStatus(1);    //1: start to wait for serve queue
				orderService.addOrder(order);
			}else {
				order = orderService.queryOrderByBookNum(bookNum);
				order.setStartTime(dateTime);
				order.setStatus(1);
				orderService.updateOrder(order);
			}
		}catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		}catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
			logger.error("< OrderController.startOrder() > 取号预约失败." + e.getMessage());
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
