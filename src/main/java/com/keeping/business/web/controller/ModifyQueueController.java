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
import com.keeping.business.common.rescode.BusinessCenterModifyQueueStatus;
import com.keeping.business.common.rescode.BusinessCenterOrderStatus;
import com.keeping.business.common.rescode.BusinessCenterResCode;
import com.keeping.business.common.rescode.BusinessCenterServeQueueStatus;
import com.keeping.business.common.rescode.BusinessCenterUserGroup;
import com.keeping.business.common.util.PlatformPar;
import com.keeping.business.common.util.PlatfromConstants;
import com.keeping.business.common.util.Printer;
import com.keeping.business.common.util.StringUtil;
import com.keeping.business.service.CashQueueService;
import com.keeping.business.service.IssueQueueService;
import com.keeping.business.service.JobTypeService;
import com.keeping.business.service.ModifyQueueService;
import com.keeping.business.service.OrderService;
import com.keeping.business.service.ServeQueueService;
import com.keeping.business.service.UserService;
import com.keeping.business.service.UserWorkloadService;
import com.keeping.business.web.controller.converter.JsonConverter;
import com.keeping.business.web.controller.converter.ReorgQueue;
import com.keeping.business.web.controller.converter.WebUserConverter;
import com.keeping.business.web.controller.model.CashQueue;
import com.keeping.business.web.controller.model.IdObject;
import com.keeping.business.web.controller.model.IssueQueue;
import com.keeping.business.web.controller.model.JobType;
import com.keeping.business.web.controller.model.LoginReq;
import com.keeping.business.web.controller.model.ModifyQueue;
import com.keeping.business.web.controller.model.Order;
import com.keeping.business.web.controller.model.ServeQueue;
import com.keeping.business.web.controller.model.StepObject;
import com.keeping.business.web.controller.model.TodayModifyQueue;
import com.keeping.business.web.controller.model.User;
import com.keeping.business.web.controller.model.UserProfile;
import com.keeping.business.web.controller.model.UserWorkload;
import com.keeping.business.web.controller.model.WebResult;
import com.keeping.business.web.controller.model.WebResultList;
import com.keeping.business.web.controller.model.WebResultObject;

@Controller
@RequestMapping("/modifyqueue.do")
public class ModifyQueueController {

	/** 日志 */
//	private Logger logger = LoggerFactory.getLogger(UserController.class);

	/** 用户信息Service */
	@Resource
	private ModifyQueueService modifyQueueService;
	@Resource
	private OrderService orderService;
	@Resource
	private UserService userService;
	@Resource
	private JobTypeService jobtypeService;
	@Resource
	private UserWorkloadService userWorkloadService;
	@Resource
	private CashQueueService cashQueueService;
	@Resource
	private IssueQueueService issueQueueService;
	
	@RequestMapping(params = "action=gettoday")
	@ResponseBody
	public WebResultList<TodayModifyQueue> getTodayModifyQueue(HttpServletRequest request, HttpServletResponse response) {
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		response.setHeader("Access-Control-Allow-Origin", "*");
		List<TodayModifyQueue> todayModifyQueues = new ArrayList<TodayModifyQueue>();
		
		try {
		
			Date now = new Date();
			List<Integer> userIds = modifyQueueService.getAllTodayWorkers(now);
			ModifyQueue modifyQueue = new ModifyQueue();
			modifyQueue.setAssignDate(now);

			for(int i=0; i<userIds.size(); i++){
				
				TodayModifyQueue todayModifyQueue = new TodayModifyQueue();
				
				User user = userService.getByUserId(userIds.get(i));
				modifyQueue.setModifierId(user.getId());
				List<ModifyQueue> modifyQueues = modifyQueueService.getModifyQueueByModifierId(modifyQueue);
				
				for (int j=0; j<modifyQueues.size(); j++){
					
					Float load = Float.parseFloat(jobtypeService.queryByKey(modifyQueues.get(j).getJobType()).getValue());
					load = load + modifyQueues.get(j).getAdditionTime();
					
					modifyQueues.get(j).setLoad(load);
					
					Integer orderId = modifyQueues.get(j).getOrderId();
					Order order = orderService.queryOrderById(orderId);
					modifyQueues.get(j).setRegisterNum(order.getRegisterNum());
				}
				
				todayModifyQueue.setUsername(user.getUserName());
				todayModifyQueue.setModifyQueues(modifyQueues);
				
				todayModifyQueues.add(todayModifyQueue);
				
			}
				
		} catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
//			logger.error("< ModifyQueueController.getModifyQueue() > 获取维修列表失败."
//					+ e.getMessage());
		}
		
		return JsonConverter.getResultObject(code, msg, todayModifyQueues);
	}
	
	@RequestMapping(params = "action=alllist")
	@ResponseBody
	public WebResultList<ModifyQueue> getAllModifyQueues(HttpServletRequest request, HttpServletResponse response) {
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();
		response.setHeader("Access-Control-Allow-Origin", "*");
		List<ModifyQueue> modifyQueueList = new ArrayList<ModifyQueue>();
		try {
			String jsonStr = request.getParameter("param");
			StepObject step = JsonConverter.getFromJsonString(jsonStr,
					StepObject.class);
			
			if (null == step) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< ServeQueueController.getAllServeQueues() > 获取服务订单列表请求信息不正确: " + step);
			} else {
				Date now = new Date();
				step.setToday(now);
				modifyQueueList = modifyQueueService.getModifyQueueByStep(step);
				
				List<Integer> userIdList = new ArrayList<Integer>();
				List<Integer> orderIdList = new ArrayList<Integer>();
				List<User> users = new ArrayList<User>();
				for (int i=0; i<modifyQueueList.size(); i++){
					
					userIdList.add(modifyQueueList.get(i).getUserId());
					
					orderIdList.add(modifyQueueList.get(i).getOrderId());
					
					User user = userService.getByUserId(userIdList.get(i));
					users.add(user);
	
					if (modifyQueueList.get(i).getJobType() != null){
						modifyQueueList.get(i).setJobtypeTime(Float.parseFloat(jobtypeService.queryByKey(modifyQueueList.get(i).getJobType()).getValue()));
					}
					
				}
				
				if(modifyQueueList.size() > 0){
					
					List<Order> orders = orderService.getByOrdersId(orderIdList);
					
					if(step.getIsBook() != null && 1 == step.getIsBook()){
						ReorgQueue.reorgModifyQueue(modifyQueueList, users, orders);
					}else if (step.getIsBook() != null && step.getIsBook() == 0){
						ReorgQueue.reorgModifyQueue(modifyQueueList, users, orders);   
					}else{
						ReorgQueue.reorgModifyQueue(modifyQueueList, users, orders);
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
//			logger.error("< ServeQueueController.getAllServeQueues() > 获取排队列表失败."
//					+ e.getMessage());
		}

		return JsonConverter.getResultObject(code, msg, modifyQueueList);
	}
	
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
//				logger.error("< ModifyQueueController.getModifyQueue() > 获取维修订单请求信息不正确: " + idObject.getId());
			} else {
				
				modifyQueue = modifyQueueService.getModifyQueueByForId(idObject.getId());
				
				if (modifyQueue != null && modifyQueue.getId() != null){
					Integer userId = modifyQueue.getUserId();
					Integer orderId = modifyQueue.getOrderId();
					Integer modifierId = modifyQueue.getModifierId();
					
					User user = userService.getByUserId(userId);
					Order order = orderService.queryOrderById(orderId);
					User modifier = userService.getByUserId(modifierId);

					modifyQueue.setUser(user);
					modifyQueue.setOrder(order);
					modifyQueue.setModifier(modifier);
					
					JobType jobType = jobtypeService.queryByKey(modifyQueue.getJobType());
					modifyQueue.setJobtypeTime(Float.parseFloat(jobType.getValue()));
					
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
//			logger.error("< ModifyQueueController.getModifyQueue() > 获取维修列表失败."
//					+ e.getMessage());
		}
		
		return JsonConverter.getResultObject(code, msg, modifyQueue);
	}
	
	@RequestMapping(params = "action=allocate")
	@ResponseBody
	public WebResultObject<ModifyQueue> allocateModifyQueue(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();

		ModifyQueue modifyQueue = new ModifyQueue();
		
		try {
			
			String jsonStr = request.getParameter("param");
			ModifyQueue modifyQueueObject = JsonConverter.getFromJsonString(jsonStr,ModifyQueue.class);
			
			if (null == modifyQueueObject || modifyQueueObject.getId() == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< ModifyQueueController.getModifyQueue() > 获取维修订单请求信息不正确: " + idObject.getId());
			} else {
				
				modifyQueue = modifyQueueService.getModifyQueueById(modifyQueueObject.getId());
				
				if (modifyQueue != null && modifyQueue.getId() != null){
					
					Integer forId = modifyQueueObject.getForId();
					List<Integer> forids = modifyQueueService.getForids(forId);
					
					if (forids != null && forids.size() == 0){
						
						Float generalRepaire = null;
						JobType jobType = new JobType();
						if(modifyQueueObject.getJobType() != null){
							jobType = jobtypeService.queryByKey(modifyQueueObject.getJobType());
						} else {
							jobType = jobtypeService.queryByKey(modifyQueue.getJobType());;
						}
						if(jobType != null){
							generalRepaire = Float.parseFloat(jobType.getValue());
						}
						
						UserWorkload userWorkload = new UserWorkload();
						
						if (modifyQueueObject.getModifierId() == null){
							userWorkload.setUserId(modifyQueue.getModifierId());
						} else {
							userWorkload.setUserId(modifyQueueObject.getModifierId());
						}
						
						Float load = Float.parseFloat(jobType.getValue());
						load = load + modifyQueue.getAdditionTime();
						
						modifyQueue.setLoad(load);
						
						if(modifyQueueObject.getUserId() != null){
							modifyQueue.setUserId(modifyQueueObject.getUserId());
						}
						
						if(modifyQueueObject.getIsSubContract() != null){
							modifyQueue.setIsWarrant(modifyQueueObject.getIsWarrant());
						}
						
						if(modifyQueueObject.getIsWarrant() != null){
							modifyQueue.setIsSubContract(modifyQueueObject.getIsSubContract());
						}
						
						modifyQueue.setModifierId(modifyQueueObject.getModifierId());
						modifyQueue.setJobType(modifyQueueObject.getJobType());
						modifyQueue.setAdditionTime(modifyQueue.getAdditionTime());
						modifyQueue.setTechnician(modifyQueueObject.getTechnician());
						modifyQueue.setPromistTime(modifyQueueObject.getPromistTime());
						modifyQueue.setStep(BusinessCenterModifyQueueStatus.MODIFYQUEUE_STATUS_READY.getId());
						modifyQueue.setForId(modifyQueueObject.getForId());
						modifyQueueService.updateModifyQueue(modifyQueue);
						
						IssueQueue issueQueue = new IssueQueue();
						Date now = new Date();
//						issueQueue.setCreateTime(now);
//						issueQueue.setStep(BusinessCenterIssueQueueStatus.MODIFYQUEUE_STATUS_ISSUE.getId());
//						issueQueue.setOrderId(modifyQueueObject.getOrderId());
//						issueQueueService.addIssueQueue(issueQueue);
						
						userWorkload.setAdditionalHours(modifyQueue.getAdditionTime());
						userWorkload.setModifyqueueId(modifyQueue.getId());
						userWorkload.setGeneralRepaire(generalRepaire);
						userWorkload.setAllocatedTime(modifyQueue.getAssignTime());
						userWorkload.setHumanResource(modifyQueue.getLoad());

						userWorkload.setCreateTime(now);
						userWorkload.setAssignDate(now);
						userWorkload.setSaId(modifyQueue.getUserId());
						userWorkload.setIsSubContract(modifyQueue.getIsSubContract());
						userWorkload.setIsWarrant(modifyQueue.getIsWarrant());
						userWorkloadService.addUserWorkload(userWorkload);
					} else {
					
						code = BusinessCenterResCode.ID_EXIST.getCode();
						msg = BusinessCenterResCode.ID_EXIST.getMsg();
					}
					
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
//			logger.error("< ModifyQueueController.getModifyQueue() > 获取维修列表失败."
//					+ e.getMessage());
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
//				logger.error("< ModifyQueueController.start() > 订单维修订单信息为空或没有权限。"
//						+ jsonStr);
			} else{
				
				modifyQueue = modifyQueueService.getModifyQueueById(modifyQueue.getId());
				
				UserWorkload userWorkload = userWorkloadService.queryByUserWorkloadQueueid(modifyQueue.getId());
				
				if (modifyQueue != null && userWorkload != null && modifyQueue.getStep() > 0){
					
					Integer step = modifyQueue.getStep();
					
					if (step < BusinessCenterModifyQueueStatus.MODIFYQUEUE_STATUS_FINISH.getId()){
						Date now = new Date();
						modifyQueue.setStep(BusinessCenterModifyQueueStatus.MODIFYQUEUE_STATUS_START.getId());
						modifyQueue.setStartTime(now);
						modifyQueueService.updateModifyQueue(modifyQueue);
						
						userWorkload.setStartTime(now);
						userWorkloadService.updateUserWorkload(userWorkload);
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
//			logger.error("< ModifyQueueController.start() > 挂起任务失败."
//					+ e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
//			logger.error("< ModifyQueueController.start() > 挂起任务返回出错."
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
			
			ModifyQueue modifyQueue = JsonConverter.getFromJsonString(jsonStr,
					ModifyQueue.class);
	
			if (StringUtil.isNull(jsonStr) || modifyQueue == null) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< ModifyQueueController.hold() > 订单维修订单信息为空或没有权限。"
//						+ jsonStr);
			} else{
				
				modifyQueue = modifyQueueService.getModifyQueueById(modifyQueue.getId());
				
				if (modifyQueue != null && modifyQueue.getStep() > 0){
					
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
//			logger.error("< ModifyQueueController.hold() > 挂起任务失败."
//					+ e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
//			logger.error("< ModifyQueueController.hold() > 挂起任务返回出错."
//					+ e.getMessage());
			throw e;
		}
	}

	@RequestMapping(params = "action=preapproval")
	@ResponseBody
	public WebResult preapproval(HttpServletRequest request,
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
//				logger.error("< ModifyQueueController.hold() > 订单维修订单信息为空或没有权限。"
//						+ jsonStr);
			} else{
				
				modifyQueue = modifyQueueService.getModifyQueueById(modifyQueue.getId());
				
				if (modifyQueue != null && modifyQueue.getStep() > 0){
					
					modifyQueue.setStep(BusinessCenterModifyQueueStatus.MODIFYQUEUE_STATUS_PREAPPROVAL.getId());
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
//			logger.error("< ModifyQueueController.hold() > 挂起任务失败."
//					+ e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
//			logger.error("< ModifyQueueController.hold() > 挂起任务返回出错."
//					+ e.getMessage());
			throw e;
		}
	}
	
	@RequestMapping(params = "action=reject")
	@ResponseBody
	public WebResult reject(HttpServletRequest request,
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
//				logger.error("< ModifyQueueController.hold() > 订单维修订单信息为空或没有权限。"
//						+ jsonStr);
			} else{
				
				modifyQueue = modifyQueueService.getModifyQueueById(modifyQueue.getId());
				
				if (modifyQueue != null && modifyQueue.getStep() > 0){
					
					modifyQueue.setStep(BusinessCenterModifyQueueStatus.MODIFYQUEUE_STATUS_REJECT.getId());
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
//			logger.error("< ModifyQueueController.hold() > 挂起任务失败."
//					+ e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
//			logger.error("< ModifyQueueController.hold() > 挂起任务返回出错."
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
			
			ModifyQueue modifyQueue = JsonConverter.getFromJsonString(jsonStr,
					ModifyQueue.class);

			if (StringUtil.isNull(jsonStr) || modifyQueue == null ) {
				code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
//				logger.error("< ModifyQueueController.finish() > 维修订单信息为空或没有权限。"
//						+ jsonStr);
			}else{
				modifyQueue = modifyQueueService.getModifyQueueById(modifyQueue.getId());
				
				if (modifyQueue != null && modifyQueue.getStep() > 0){
					
					Order order = orderService.queryOrderById(modifyQueue.getOrderId());
					order.setStatus(BusinessCenterOrderStatus.ORDER_STATUS_WASH.getId());
					orderService.updateOrder(order);              //修改订单状态
					
					Date now = new Date();
					
					modifyQueue.setEndTime(now);
					modifyQueue.setStep(BusinessCenterModifyQueueStatus.MODIFYQUEUE_STATUS_FINISH.getId());
					
					modifyQueueService.updateModifyQueue(modifyQueue);   //更新modifyQueue订单
					
					UserWorkload userWorkload = userWorkloadService.queryByUserWorkloadQueueid(modifyQueue.getId());
					now = new Date();
					userWorkload.setEndTime(now);
					userWorkloadService.updateUserWorkload(userWorkload);
					
					CashQueue cashQueue = new CashQueue();
					cashQueue.setStep(BusinessCenterCashQueueStatus.CASHQUEUE_STATUS_MODIFYING.getId());
					cashQueue.setOrderId(modifyQueue.getOrderId());
					cashQueue.setUserId(modifyQueue.getUserId());
					
					cashQueueService.addCashQueue(cashQueue);
//					modifyQueueService.addModifyQueue(modifyQueue);
				}else{
				
					code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
					msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
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
//			logger.error("< ModifyQueueController.finish() > 维修订单发送清洗车间失败."
//					+ e.getMessage());
		}

		// 返回结果
		try {
			return JsonConverter.getResultSignal(code, msg);
		} catch (Exception e) {
//			logger.error("< ModifyQueueController.finish() > 发送清洗车间返回出错."
//					+ e.getMessage());
			throw e;
		}
	}
	
}
