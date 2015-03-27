package com.keeping.business.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.keeping.business.common.util.TimeUtil;
import com.keeping.business.service.CashQueueService;
import com.keeping.business.service.CustomerService;
import com.keeping.business.service.JobTypeService;
import com.keeping.business.service.ModifyQueueService;
import com.keeping.business.service.OrderService;
import com.keeping.business.service.PropertyService;
import com.keeping.business.service.ServeQueueService;
import com.keeping.business.service.UserGroupService;
import com.keeping.business.service.UserService;
import com.keeping.business.web.controller.converter.JsonConverter;
import com.keeping.business.web.controller.model.CashQueue;
import com.keeping.business.web.controller.model.Customer;
import com.keeping.business.web.controller.model.ModifyQueue;
import com.keeping.business.web.controller.model.Order;
import com.keeping.business.web.controller.model.OrderObject;
import com.keeping.business.web.controller.model.OrderPerDay;
import com.keeping.business.web.controller.model.OrderPerDayDetail;
import com.keeping.business.web.controller.model.OrderPerPerson;
import com.keeping.business.web.controller.model.Property;
import com.keeping.business.web.controller.model.Report;
import com.keeping.business.web.controller.model.ReportObject;
import com.keeping.business.web.controller.model.ServeQueue;
import com.keeping.business.web.controller.model.StatusObject;
import com.keeping.business.web.controller.model.User;
import com.keeping.business.web.controller.model.UserGroup;
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
	@Resource
	private JobTypeService jobtypeService;
	@Resource
	private ServeQueueService serveQueueService;
	@Resource
	private ModifyQueueService modifyQueueService;
	@Resource
	private CashQueueService cashQueueService;
	@Resource
	private UserGroupService userGroupService;
    
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
			ReportObject reportObject = JsonConverter.getFromJsonString(jsonStr, ReportObject.class, "yyyy-MM-dd");
			if (reportObject == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< OrderController.getAllOrders() > 获取订单状态不正确." + status + " : " + BusinessCenterOrderStatus.ORDER_STATUS_WAIT.getStatus());
			}else if (null == session || null == loginUser || loginUser.getUserName().equals("admin") == false){
				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
//				logger.error("< OrderController.bookOrder() > session为空。" + jsonStr);
			} else {
	
				List<Order> orders = orderService.getAllOrdersFReport(reportObject);				
				List<ServeQueue> serveQueues = new ArrayList<ServeQueue>();
				List<ModifyQueue> modifyQueues = new ArrayList<ModifyQueue>();
				List<CashQueue> cashQueues = new ArrayList<CashQueue>();
				
				List<Long> serveQueueTime = new ArrayList<Long>();
				List<Long> modifyQueueTime = new ArrayList<Long>();
				List<Long> cashQueueTime = new ArrayList<Long>();
				
				Integer total = orders.size();
				Integer totalServeQueue = 0;
				Integer totalModifyQueue = 0;
				Integer totalCashQueue = 0;
				Long totalServeQueueTime = new Long(0);
				Long totalModifyQueueTime = new Long(0);
				Long totalCashQueueTime = new Long(0);
				
				for (int i = 0; i < total; i++){
					
					ServeQueue serveQueue = new ServeQueue();
					serveQueue = serveQueueService.getServeQueueByOrderid(orders.get(i).getId());
					if (serveQueue != null && serveQueue.getId() != null){
						serveQueues.add(serveQueue);
						Long interval = (serveQueue.getEndTime().getTime() - serveQueue.getStartTime().getTime())/ 1000 % 60;
						serveQueueTime.add(interval);
						totalServeQueueTime = totalServeQueueTime + interval;
						totalServeQueue ++;
					}
					
					ModifyQueue modifyQueue = new ModifyQueue();
					modifyQueue = modifyQueueService.getModifyQueueByOrderid(orders.get(i).getId());
					if (modifyQueue != null && modifyQueue.getId() != null){
						modifyQueues.add(modifyQueue);
						Long interval = (modifyQueue.getEndTime().getTime() - modifyQueue.getStartTime().getTime())/ 1000 % 60;
						modifyQueueTime.add(interval);
						totalModifyQueueTime = totalModifyQueueTime + interval;
						totalModifyQueue ++;
					}
					
					CashQueue cashQueue = new CashQueue();
					cashQueue = cashQueueService.getCashQueueByOrderid(orders.get(i).getId());
					if(cashQueue != null && cashQueue.getId() != null){
						cashQueues.add(cashQueue);
						Long interval = (cashQueue.getEndTime().getTime() - cashQueue.getStartTime().getTime())/ 1000 % 60;
						cashQueueTime.add(interval);
						totalCashQueueTime = totalCashQueueTime + interval;
						totalCashQueue ++;
					}
				}
				
				Report report = new Report();
				report.setName("Average serve time");
				report.setValue((totalServeQueueTime / total) + "");
				reports.add(report);
				
				report = new Report();
				report.setName("Average modify time");
				report.setValue((totalModifyQueueTime / total) + "");
				reports.add(report);
				
				report = new Report();
				report.setName("Average cash time");
				report.setValue((totalCashQueueTime / total) + "");
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
	
	/**
     * 获取所有预约的 OrderStatus为1（已经取号）的订单
     * 
     * @param HttpServletRequest
     * @param HttpServletResponse
	 * @return 
     * @return N/A
     */
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
	
	/**
     * 获取今天所有预约的订单
     * 
     * @param HttpServletRequest
     * @param HttpServletResponse
	 * @return 
     * @return N/A
     */
	@RequestMapping(params = "action=alltodaybooklist")
	@ResponseBody
	public WebResultList<Order> getAllTodayBookOrders(HttpServletRequest request,HttpServletResponse response) {
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();

		List<Order> orderList = null;
		
		try {
			String jsonStr = request.getParameter("param");
			OrderObject orderObject = JsonConverter.getFromJsonString(jsonStr, OrderObject.class, "yyyy-MM-dd");
			
			if (orderObject == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< OrderController.getAllOrders() > 获取订单状态不正确." + status + " : " + BusinessCenterOrderStatus.ORDER_STATUS_WAIT.getStatus());
			} else {
				
				if (orderObject.getExpress() == null){
					orderList = orderService.getOrderByStatusAndBook(orderObject); //orderObject 包含AssignDate以及isBook为1
				} else {
					orderList = orderService.getOrderByBookAndExpress(orderObject);
				}
				
				for (int j=0; j<orderList.size(); j++){
					
					Customer customer = customerService.getCustomerByPoliceNum(orderList.get(j).getRegisterNum());
					
					orderList.get(j).setCustomer(customer);
					
					if (orderObject.getExpress() == null){
						Float load = Float.parseFloat(jobtypeService.queryByKey(orderList.get(j).getJobType()).getValue());
						orderList.get(j).setLoad(load);
					} else {
						Float load = Float.parseFloat(jobtypeService.queryByKey("Express" + orderList.get(j).getExpress()).getValue());
						orderList.get(j).setLoad(load);
					}
					
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
	
	/**
     * 获取今天预约的订单
     * 
     * @param HttpServletRequest
     * @param HttpServletResponse
	 * @return 
     * @return N/A
     */
	@RequestMapping(params = "action=allcurdaybooklist")
	@ResponseBody
	public WebResultList<OrderPerPerson> getCurdayBookOrders(HttpServletRequest request,HttpServletResponse response) {
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();

		List<Order> orderList = null;
	
		List<OrderPerPerson> orderPerPersonList = new ArrayList<OrderPerPerson>();
		
		try {
			String jsonStr = request.getParameter("param");
			OrderObject orderObject = JsonConverter.getFromJsonString(jsonStr, OrderObject.class, "yyyy-MM-dd");
			
			if (orderObject == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< OrderController.getAllOrders() > 获取订单状态不正确." + status + " : " + BusinessCenterOrderStatus.ORDER_STATUS_WAIT.getStatus());
			} else {
				
				Property booking_group_no = propertyService.queryByKey("BOOKING_GROUP_NO");
				Integer booking_group_value = Integer.parseInt(booking_group_no.getValue());
				Property load_person = propertyService.queryByKey("LOAD_PERSON");
				Property load_percentage = propertyService.queryByKey("LOAD_PERCENTAGE");
				Float load_person_value = Float.parseFloat(load_person.getValue());
				Float load_perc_value = new Float(load_percentage.getValue().substring(0,load_percentage.getValue().indexOf("%")))/100;
				
				for (int i = 0; i < booking_group_value; i++){
					
					OrderPerPerson orderPerPerson = new OrderPerPerson();
					
					orderObject.setGroupid(i+1);
					orderList = new ArrayList();
					orderList = orderService.getOrderByStatusAndBook(orderObject); //orderObject 包含AssignDate以及isBook为1
					
					Float totalLoad = (float) 0;
					
					for (int j=0; j<orderList.size(); j++){
						
						Customer customer = customerService.getCustomerByPoliceNum(orderList.get(j).getRegisterNum());
						
						orderList.get(j).setCustomer(customer);
						
						if (orderObject.getExpress() == null){
							Float load = Float.parseFloat(jobtypeService.queryByKey(orderList.get(j).getJobType()).getValue());
							totalLoad = totalLoad + load;
						} else {
							Float load = Float.parseFloat(jobtypeService.queryByKey("Express" + orderList.get(j).getExpress()).getValue());
							totalLoad = totalLoad + load;
						}

					}
				
					totalLoad = totalLoad / (load_perc_value * load_person_value); 
					totalLoad =  (float)(Math.round(totalLoad*100))/100;
					
					orderPerPerson.setId(i+1);
					orderPerPerson.setLoad(totalLoad);
					orderPerPerson.setOrderList(orderList);
					
					orderPerPersonList.add(orderPerPerson);
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

		return JsonConverter.getResultObject(code, msg, orderPerPersonList);
	}
	
	/**
     * 获取当周预约的订单
     * 
     * @param HttpServletRequest
     * @param HttpServletResponse
	 * @return 
     * @return N/A
     */
	@RequestMapping(params = "action=allcurweekbooklist")
	@ResponseBody
	public WebResultList<OrderPerDayDetail> getCurweekBookOrders(HttpServletRequest request,HttpServletResponse response) {
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();

		List<Order> orderList = null;
	
		List<OrderPerDayDetail> orderPerDayDetails = new ArrayList<OrderPerDayDetail>();
		
		try {
			String jsonStr = request.getParameter("param");
			OrderObject orderObject = JsonConverter.getFromJsonString(jsonStr, OrderObject.class, "yyyy-MM-dd");
			
			if (orderObject == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< OrderController.getAllOrders() > 获取订单状态不正确." + status + " : " + BusinessCenterOrderStatus.ORDER_STATUS_WAIT.getStatus());
			} else {
				
				Property booking_group_no = propertyService.queryByKey("BOOKING_GROUP_NO");
				Integer booking_group_value = Integer.parseInt(booking_group_no.getValue());
				Property load_person = propertyService.queryByKey("LOAD_PERSON");
				Property load_percentage = propertyService.queryByKey("LOAD_PERCENTAGE");
				Float load_person_value = Float.parseFloat(load_person.getValue());
				Float load_perc_value = new Float(load_percentage.getValue().substring(0,load_percentage.getValue().indexOf("%")))/100;
				
				Calendar beginCal=Calendar.getInstance();
				beginCal.setTime(orderObject.getBeginDate());
				Calendar curCal = Calendar.getInstance();
				curCal.setTime(orderObject.getBeginDate());
				for (int k = 0; k < 7; k++, curCal.add(Calendar.DAY_OF_MONTH, 1)){					
					Date condDate = curCal.getTime();
					orderObject.setAssignDate(condDate);
					
					OrderPerDayDetail orderPerDayDetail = new OrderPerDayDetail();
					List<OrderPerPerson> orderPerPersonList = new ArrayList<OrderPerPerson>();					
					orderPerDayDetail.setDate(condDate);
				
				for (int i = 0; i < booking_group_value; i++){
					
					OrderPerPerson orderPerPerson = new OrderPerPerson();
					
					orderObject.setGroupid(i+1);
					orderList = new ArrayList();
					orderList = orderService.getOrderByStatusAndBook(orderObject); //orderObject 包含AssignDate以及isBook为1
					
					Float totalLoad = (float) 0;
					
					for (int j=0; j<orderList.size(); j++){
						
						Customer customer = customerService.getCustomerByPoliceNum(orderList.get(j).getRegisterNum());
						
						orderList.get(j).setCustomer(customer);
						
						if (orderObject.getExpress() == null){
							Float load = Float.parseFloat(jobtypeService.queryByKey(orderList.get(j).getJobType()).getValue());
							totalLoad = totalLoad + load;
						} else {
							Float load = Float.parseFloat(jobtypeService.queryByKey("Express" + orderList.get(j).getExpress()).getValue());
							totalLoad = totalLoad + load;
						}

					}
				
					totalLoad = totalLoad / (load_perc_value * load_person_value);
					totalLoad =  (float)(Math.round(totalLoad*100))/100;
					
					orderPerPerson.setId(i+1);
					orderPerPerson.setLoad(totalLoad);
					orderPerPerson.setOrderList(orderList);
					
					orderPerPersonList.add(orderPerPerson);
				}
				
				orderPerDayDetail.setOrderPerPersons(orderPerPersonList);
				
				orderPerDayDetails.add(orderPerDayDetail);
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

		return JsonConverter.getResultObject(code, msg, orderPerDayDetails);
	}
	
	/**
     * 获取当月预约的订单
     * 
     * @param HttpServletRequest
     * @param HttpServletResponse
	 * @return 
     * @return N/A
     */
	@RequestMapping(params = "action=allcurmonthbooklist")
	@ResponseBody
	public WebResultList<OrderPerDay> getCurmonthBookOrders(HttpServletRequest request,HttpServletResponse response) {
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();

		List<Order> orderList = null;
		
		HashMap<String, List> orderPerDays = new HashMap<String, List>();
		
		List<OrderPerDay> orderPerDayList = new ArrayList<OrderPerDay>();
		
		try {
			String jsonStr = request.getParameter("param");
			OrderObject orderObject = JsonConverter.getFromJsonString(jsonStr, OrderObject.class, "yyyy-MM-dd");
			
			if (orderObject == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< OrderController.getAllOrders() > 获取订单状态不正确." + status + " : " + BusinessCenterOrderStatus.ORDER_STATUS_WAIT.getStatus());
			} else {
				
				Property booking_group_no = propertyService.queryByKey("BOOKING_GROUP_NO");
				Integer booking_group_value = Integer.parseInt(booking_group_no.getValue());
				Property load_person = propertyService.queryByKey("LOAD_PERSON");
				Property load_percentage = propertyService.queryByKey("LOAD_PERCENTAGE");
				Float load_person_value = Float.parseFloat(load_person.getValue());
				Float load_perc_value = new Float(load_percentage.getValue().substring(0,load_percentage.getValue().indexOf("%")))/100;
				
				orderList = orderService.getOrdersByBook(orderObject);
				
				for (int i = 0 ; i < orderList.size(); i++){
					
					Date date = orderList.get(i).getAssignDate();
					
					if (orderPerDays.containsKey(date.toString())){
						
						orderPerDays.get(date.toString()).add(orderList.get(i));
					} else {
						
						List<Order> tempOrderList = new ArrayList<Order>();
						orderPerDays.put(date.toString(), tempOrderList);
					}
				}
				
				Iterator iter = orderPerDays.entrySet().iterator();
				
				List<Order> orders = new ArrayList<Order>();
				
				while (iter.hasNext()){
					
					Map.Entry entry = (Map.Entry) iter.next();
					
					OrderPerDay orderPerDay = new OrderPerDay();
					orderPerDay.setDate(entry.getKey().toString());
					
					orders = (List<Order>) entry.getValue();
					Float totalLoad = (float) 0;
					for (int j = 0; j < orders.size(); j++) {
						
						Float load = Float.parseFloat(jobtypeService.queryByKey(orders.get(j).getJobType()).getValue());
						totalLoad = totalLoad + load;
					}
					
					totalLoad = totalLoad / (load_perc_value * load_person_value);
					totalLoad =  (float)(Math.round(totalLoad*100))/100;
					
					orderPerDay.setLoad(totalLoad);
					
					orderPerDayList.add(orderPerDay);
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

		return JsonConverter.getResultObject(code, msg, orderPerDayList);
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
			if (orderObject == null || orderObject.getRegisterNum() == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< OrderController.getOrder() > 获取订单状态不正确." + orderObject.getQueueNumber() + " : " + BusinessCenterOrderStatus.ORDER_STATUS_WAIT.getStatus());
			} else {
				
				order = orderService.getOrdersByRegNum(orderObject);

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
		
		try { 
			UserProfile loginUser = (UserProfile) session.getAttribute(PlatfromConstants.STR_USER_PROFILE);

			String jsonStr = request.getParameter("param");
			OrderObject orderObject = JsonConverter.getFromJsonString(jsonStr, OrderObject.class, "yyyy-MM-dd HH:mm:ss");
			
			Date now = new Date();
			java.sql.Timestamp dateTime = new java.sql.Timestamp(now.getTime());
			 
			if (StringUtil.isNull(jsonStr) || null == orderObject) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< OrderController.bookOrder() > 订单预约请求信息不正确。" + jsonStr);
//			} else if (null == session || null == loginUser || null == loginUser.getUserName()){
//				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
//				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
////				logger.error("< OrderController.bookOrder() > session为空。" + jsonStr);
			} 
			else {
				 Order order = orderService.getOrdersByRegNum(orderObject);
				 
				 if (order != null && order.getId() == null){
				
					 Customer customer = customerService.getCustomerByPoliceNum(orderObject.getRegisterNum());
					 
					 if (customer != null && customer.getUserName() == null){
						 customer.setPoliceNum(orderObject.getRegisterNum());
						 customer.setUserName(orderObject.getUserName());
						 customer.setMobilephone(orderObject.getMobilePhone());
						 customerService.addCustomer(customer);
						 customer = customerService.getCustomerByPoliceNum(customer.getPoliceNum());
					 } else {
						 customer.setUserName(orderObject.getUserName());
						 customer.setMobilephone(orderObject.getMobilePhone());
						 customerService.modifyCustomer(customer);
					 }
					
					 String bookNumber = dateTime.toString();
					 order.setBookTime(now);
					 order.setBookNum(bookNumber);
					 order.setIsBook(1);
					 order.setRegisterNum(orderObject.getRegisterNum());
					 order.setCustomerId(customer.getId());
					 order.setAssignDate(orderObject.getBookStartTime());
					 order.setJobType(orderObject.getJobType());
					 order.setUserName(orderObject.getUserName());
					 order.setBookStartTime(orderObject.getBookStartTime());
					 order.setComment(orderObject.getComment());
					 order.setGroupid(orderObject.getGroupid());
					 order.setExpress(orderObject.getExpress());
					 orderService.addOrder(order);
				} else {
					 code = BusinessCenterResCode.ORDER_EXIST.getCode();
					 msg = BusinessCenterResCode.ORDER_EXIST.getMsg();
				}
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
	
	@RequestMapping(params = "action=update")
	@ResponseBody
	public WebResult updateOrder(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(PlatformPar.sessionTimeout);
		
		try { 
			UserProfile loginUser = (UserProfile) session.getAttribute(PlatfromConstants.STR_USER_PROFILE);

			String jsonStr = request.getParameter("param");
			OrderObject orderObject = JsonConverter.getFromJsonString(jsonStr, OrderObject.class, "yyyy-MM-dd HH:mm:ss");
			
			Date now = new Date();
			java.sql.Timestamp dateTime = new java.sql.Timestamp(now.getTime());
			 
			if (StringUtil.isNull(jsonStr) || null == orderObject) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< OrderController.bookOrder() > 订单预约请求信息不正确。" + jsonStr);
//			} else if (null == session || null == loginUser || null == loginUser.getUserName()){
//				code = BusinessCenterResCode.SYS_INVILID_REQ.getCode();
//				msg = BusinessCenterResCode.SYS_INVILID_REQ.getMsg();
////				logger.error("< OrderController.bookOrder() > session为空。" + jsonStr);
			} 
			else {
				 Order order = orderService.getOrdersByRegNum(orderObject);
				 
				 if (order != null && order.getId() != null){
				
					 Customer customer = customerService.getCustomerByPoliceNum(orderObject.getRegisterNum());
					 
					 if (customer != null && customer.getUserName() == null){
						 customer.setPoliceNum(orderObject.getRegisterNum());
						 customer.setUserName(orderObject.getUserName());
						 customer.setMobilephone(orderObject.getMobilePhone());
						 customerService.addCustomer(customer);
						 customer = customerService.getCustomerByPoliceNum(customer.getPoliceNum());
					 }
					
					 String bookNumber = dateTime.toString();
//					 order.setBookTime(now);
//					 order.setBookNum(bookNumber);
//					 order.setIsBook(1);
//					 order.setRegisterNum(orderObject.getRegisterNum());
//					 order.setCustomerId(customer.getId());
					 order.setAssignDate(orderObject.getBookStartTime());
					 order.setJobType(orderObject.getJobType());
					 order.setUserName(orderObject.getUserName());
					 order.setBookStartTime(orderObject.getBookStartTime());
					 order.setGroupid(orderObject.getGroupid());
					 order.setComment(orderObject.getComment());
					 order.setExpress(orderObject.getExpress());
					 orderService.updateOrder(order);
				} else {
					 code = BusinessCenterResCode.ORDER_EXIST.getCode();
					 msg = BusinessCenterResCode.ORDER_EXIST.getMsg();
				}
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
     * 客户现场取号后，开始一个订单
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
		
		Order order = new Order();
		
		try {
			
			Date now = new Date();
			java.sql.Timestamp dateTime = new java.sql.Timestamp(now.getTime());
			StringBuffer stringBuffer = new StringBuffer();
		
			String jsonStr = request.getParameter("param");
			OrderObject orderObject = JsonConverter.getFromJsonString(jsonStr, OrderObject.class, "yyyy-MM-dd");
			 
			if (StringUtil.isNull(jsonStr) || null == orderObject) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< OrderController.bookOrder() > 订单预约请求信息不正确。" + jsonStr);
			}else{
				
				order = orderService.getOrdersByRegNum(orderObject);
				
				Integer totalBookedOrders = 0;
				Integer bookCounterNum = 0;
				Integer counterNum = 0;
				Integer baseTime = 0;
				Integer bufferTime = 0;
				
				Date today = new Date();
				orderObject.setNow(now);
				orderObject.setStatus(BusinessCenterOrderStatus.ORDER_STATUS_WAIT.getId());
				User user = new User();
				UserGroup userGroup = userGroupService.queryByName("2");  //SA所在的组
				user.setGroupId(userGroup.getId());   
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
				
				if(bookCounterNum == 0){
					bookCounterNum = 1;
				}
				Integer estimationTime = ((totalBookedOrders / bookCounterNum) + 1) * baseTime + bufferTime;
			
				if (StringUtil.isNull(order.getBookNum()) && order.getId() == null) {
					Customer customer = customerService.getCustomerByPoliceNum(orderObject.getRegisterNum());
					order.setCustomerId(customer.getId());
					order.setRegisterNum(orderObject.getRegisterNum());
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
