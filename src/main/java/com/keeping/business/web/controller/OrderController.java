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
import com.keeping.business.common.util.PlatformPar;
import com.keeping.business.common.util.PlatfromConstants;
import com.keeping.business.common.util.StringUtil;
import com.keeping.business.service.CustomerService;
import com.keeping.business.service.OrderService;
import com.keeping.business.service.PropertyService;
import com.keeping.business.service.UserService;
import com.keeping.business.web.controller.converter.JsonConverter;
import com.keeping.business.web.controller.model.Customer;
import com.keeping.business.web.controller.model.Order;
import com.keeping.business.web.controller.model.OrderObject;
import com.keeping.business.web.controller.model.Property;
import com.keeping.business.web.controller.model.Report;
import com.keeping.business.web.controller.model.ReportObject;
import com.keeping.business.web.controller.model.StatusObject;
import com.keeping.business.web.controller.model.User;
import com.keeping.business.web.controller.model.UserProfile;
import com.keeping.business.web.controller.model.WebResult;
import com.keeping.business.web.controller.model.WebResultList;
import com.keeping.business.web.controller.model.WebResultObject;

@Controller
@RequestMapping("/order.do")
public class OrderController {

    /**日志 */
//    private Logger logger = LoggerFactory.getLogger(UserController.class);
    
	/**用户信息Service */
    @Resource
	private OrderService orderService;
    @Resource
   	private UserService userService;
    @Resource
   	private CustomerService customerService;
    @Resource
    private PropertyService propertyService;
    
    private static Integer nQueueNumber = 0;
    
    private static Integer bQueueNumber = 0;
    
    private StringUtil stringUitl = new StringUtil();
    
	/**
     * 获取订单列表
     * 
     * @param HttpServletRequest
     * @param HttpServletResponse
	 * @return 
     * @return N/A
     */
    
	@RequestMapping(params = "action=report")
	@ResponseBody
	public WebResultList<Report> getReport(HttpServletRequest request,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);
		
		UserProfile loginUser = (UserProfile) session.getAttribute(PlatfromConstants.STR_USER_PROFILE);

		List<Report> reports = new ArrayList<Report>();
		
		try {
			String jsonStr = request.getParameter("param");
			ReportObject reportObject = JsonConverter.getFromJsonString(jsonStr, ReportObject.class);
			if (reportObject == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< OrderController.getAllOrders() > 获取订单状态不正确." + status + " : " + BusinessCenterOrderStatus.ORDER_STATUS_WAIT.getStatus());
			}else if (null == session || null == loginUser || loginUser.getUserName().equals("admin") == false){
				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
//				logger.error("< OrderController.bookOrder() > session为空。" + jsonStr);
			} else {
			
				Report report = new Report();
				report.setName("");
				report.setValue("");
				
				reports.add(report);
			}
		}catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		}catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
//			logger.error("< OrderController.getAllOrders() > 获取订单列表失败." + e.getMessage());
		}

		return JsonConverter.getResultObject(code, msg, reports);
	}
	
	@RequestMapping(params = "action=alllist")
	@ResponseBody
	public WebResultList<Order> getAllOrders(HttpServletRequest request,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();

		List<Order> orderList = null;
		
		try {
			String jsonStr = request.getParameter("param");
			StatusObject status = JsonConverter.getFromJsonString(jsonStr, StatusObject.class);
			if (status == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< OrderController.getAllOrders() > 获取订单状态不正确." + status + " : " + BusinessCenterOrderStatus.ORDER_STATUS_WAIT.getStatus());
			} else {
				if(status.getStatus() != null){
					orderList = orderService.getOrdersByStatus(status.getStatus());
				}else{
					orderList = orderService.getAllOrders(status.getStartStatus());
				}
			}
		}catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		}catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
//			logger.error("< OrderController.getAllOrders() > 获取订单列表失败." + e.getMessage());
		}

		return JsonConverter.getResultObject(code, msg, orderList);
	}
	
	@RequestMapping(params = "action=allbooklist")
	@ResponseBody
	public WebResultList<Order> getAllBookOrders(HttpServletRequest request,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();

		List<Order> orderList = null;
		
		try {
			String jsonStr = request.getParameter("param");
			StatusObject status = JsonConverter.getFromJsonString(jsonStr, StatusObject.class);
			if (status == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< OrderController.getAllOrders() > 获取订单状态不正确." + status + " : " + BusinessCenterOrderStatus.ORDER_STATUS_WAIT.getStatus());
			} else {
				
				orderList = orderService.getOrdersByBook(status.getIsBook());
			}
		}catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		}catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
//			logger.error("< OrderController.getAllOrders() > 获取订单列表失败." + e.getMessage());
		}

		return JsonConverter.getResultObject(code, msg, orderList);
	}
	
	@RequestMapping(params = "action=getone")
	@ResponseBody
	public WebResultObject<Order> getOrder(HttpServletRequest request,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();

		Order order = new Order();
		
		try {
			String jsonStr = request.getParameter("param");
			OrderObject orderObject = JsonConverter.getFromJsonString(jsonStr, OrderObject.class);
			if (orderObject == null || orderObject.getRegisterNumber() == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< OrderController.getOrder() > 获取订单状态不正确." + orderObject.getQueueNumber() + " : " + BusinessCenterOrderStatus.ORDER_STATUS_WAIT.getStatus());
			} else {
				
				order = orderService.getOrdersByRegNum(orderObject.getRegisterNumber());

			}
		}catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		}catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
//			logger.error("< OrderController.getOrder() > 获取订单列表失败." + e.getMessage());
		}

		return JsonConverter.getResultObject(code, msg, order);
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
			Order orderObject = JsonConverter.getFromJsonString(jsonStr, Order.class);
			
			Date now = new Date();
			java.sql.Timestamp dateTime = new java.sql.Timestamp(now.getTime());
			 
			if (StringUtil.isNull(jsonStr) || null == orderObject) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< OrderController.bookOrder() > 订单预约请求信息不正确。" + jsonStr);
			}else if (null == session || null == loginUser || null == loginUser.getUserName()){
				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
//				logger.error("< OrderController.bookOrder() > session为空。" + jsonStr);
			} else {
				
				 Customer customer = customerService.getCustomerByPoliceNum(orderObject.getRegisterNum());
				 customer.setUserName(orderObject.getUserName());
				 customer.setMobilephone(orderObject.getMobilePhone());
				 
				 if (customer != null && customer.getUserName() == null){
					 customer.setPoliceNum(orderObject.getRegisterNum());
					 customerService.addCustomer(customer);
					 customer = customerService.getCustomerByPoliceNum(customer.getPoliceNum());
				 } else {
					 customerService.modifyCustomer(customer);
				 }
				
				 Order order = new Order();
				 String bookNumber = dateTime.toString();
				 order.setBookTime(now);
				 order.setBookNum(bookNumber);
				 order.setIsBook(1);
				 order.setAssignDate(now);
				 order.setRegisterNum(orderObject.getRegisterNum());
				 order.setCustomerId(customer.getId());
				 orderService.addOrder(order);
			}
		}catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		}catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
//			logger.error("< OrderController.bookOrder() > 订单预约失败." + e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
			session.removeAttribute(PlatfromConstants.STR_USER_PROFILE);
			session.invalidate();
//			logger.error("< OrderController.bookOrder() > 订单预约返回出错."
//					+ e.getMessage());
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
	public WebResultObject<Order> startOrder(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		Order order = new Order();
		
		try {
			
			Date now = new Date();
			java.sql.Timestamp dateTime = new java.sql.Timestamp(now.getTime());
			StringBuffer stringBuffer = new StringBuffer();
		
			String jsonStr = request.getParameter("param");
			OrderObject orderObject = JsonConverter.getFromJsonString(jsonStr, OrderObject.class);
			 
			if (StringUtil.isNull(jsonStr) || null == orderObject) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< OrderController.bookOrder() > 订单预约请求信息不正确。" + jsonStr);
			}else{
				
				order = orderService.getOrdersByRegNum(orderObject.getRegisterNumber());
				
				Integer totalBookedOrders = 0;
				Integer bookCounterNum = 0;
				Integer counterNum = 0;
				Integer baseTime = 0;
				Integer bufferTime = 0;
				
				Date today = new Date();
				orderObject.setNow(now);
				orderObject.setStatus(BusinessCenterOrderStatus.ORDER_STATUS_WAIT.getId());
				User user = new User();
				user.setGroupId(5);
				if (StringUtil.isNull(order.getBookNum()) && order.getId() == null) {
					orderObject.setIsBook(0);
					user.setIsBooker(0);
				}else{
					orderObject.setIsBook(1);
					user.setIsBooker(1);
				}
				totalBookedOrders = orderService.getOrderCountByStatusAndBook(orderObject);
				bookCounterNum = userService.queryUserCountByGroupAndBook(user);
				Property property = propertyService.queryByKey("COUNTER_NUM");
				counterNum = Integer.parseInt(property.getValue());
				property = propertyService.queryByKey("AVG_WAITING_TIME");
				baseTime = Integer.parseInt(property.getValue());
				property = propertyService.queryByKey("WAITING_TIME_BUFFER");
				bufferTime = Integer.parseInt(property.getValue());
				
				Integer estimationTime = ((totalBookedOrders / bookCounterNum) + 1) * baseTime + bufferTime;
			
				if (StringUtil.isNull(order.getBookNum()) && order.getId() == null) {
					order.setRegisterNum(orderObject.getRegisterNumber());
					order.setStartTime(dateTime);
					order.setStatus(BusinessCenterOrderStatus.ORDER_STATUS_WAIT.getId());    //1: start to wait for serve queue
					order.setIsBook(0);
					order.setAssignDate(today);
					order.setEstimationTime(estimationTime);
				
					stringBuffer.append("N-");
					stringBuffer.append(StringUtil.getNext(nQueueNumber));
					nQueueNumber ++;
				
					order.setQueueNum(stringBuffer.toString());
				
					orderService.addOrder(order);
				}else {
					String registerNum = order.getRegisterNum();
					order.setStartTime(dateTime);
					order.setRegisterNum(registerNum);
					order.setStatus(BusinessCenterOrderStatus.ORDER_STATUS_WAIT.getId());
					order.setEstimationTime(estimationTime);
				
					stringBuffer.append("B-");
					stringBuffer.append(StringUtil.getNext(bQueueNumber));
					bQueueNumber ++;
				
					order.setQueueNum(stringBuffer.toString());
				
					orderService.updateOrder(order);
				}
			}
		}catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		}catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
//			logger.error("< OrderController.startOrder() > 取号预约失败." + e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultObject(code, msg, order);
		} catch (Exception e) {
//			logger.error("< OrderController.startOrder() > 取号预约返回出错."
//					+ e.getMessage());
			throw e;
		}
	}
	
	@RequestMapping(params = "action=reset")
	@ResponseBody
	public WebResult resetOrderParameters(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		try {
			
			this.nQueueNumber = 0;
			this.bQueueNumber = 0;
			
		}catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
			throw e;
		}
	}
	
}
