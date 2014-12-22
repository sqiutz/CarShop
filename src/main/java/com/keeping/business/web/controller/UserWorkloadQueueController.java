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
import com.keeping.business.service.UserWorkloadService;
import com.keeping.business.web.controller.converter.JsonConverter;
import com.keeping.business.web.controller.converter.ReorgQueue;
import com.keeping.business.web.controller.converter.WebUserConverter;
import com.keeping.business.web.controller.model.DateObject;
import com.keeping.business.web.controller.model.EstimationTime;
import com.keeping.business.web.controller.model.LoginReq;
import com.keeping.business.web.controller.model.ModifyQueue;
import com.keeping.business.web.controller.model.Order;
import com.keeping.business.web.controller.model.ServeQueue;
import com.keeping.business.web.controller.model.StepObject;
import com.keeping.business.web.controller.model.User;
import com.keeping.business.web.controller.model.UserProfile;
import com.keeping.business.web.controller.model.UserWorkload;
import com.keeping.business.web.controller.model.UserWorkloadList;
import com.keeping.business.web.controller.model.WebResult;
import com.keeping.business.web.controller.model.WebResultList;
import com.keeping.business.web.controller.model.WebResultObject;

@Controller
@RequestMapping("/userworkload.do")
public class UserWorkloadQueueController {

	/** 日志 */
	private Logger logger = LoggerFactory.getLogger(UserController.class);

	/** 用户信息Service */
	@Resource
	private UserWorkloadService userWorkloadService;
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
	public WebResultList<UserWorkloadList> getAllUserWorkloads(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();

		List<UserWorkloadList> userWorkloadLists = new ArrayList<UserWorkloadList>();
		List<UserWorkload> userWorkloads = new ArrayList<UserWorkload>();
		List<Integer> userIds = new ArrayList<Integer>();
		User user = new User();
		try {
			String jsonStr = request.getParameter("param");
			System.out.println("jsonStr: " + jsonStr);
			DateObject date = JsonConverter.getFromJsonString(jsonStr,
					DateObject.class);
			if (null == date) {
				System.out.println("date is null");
				date = new DateObject();
				date.setToday(new Date());
				System.out.println(date.getToday().toString());
				//code = BusinessCenterResCode.SYS_REQ_ERROR.getCode();
				//msg = BusinessCenterResCode.SYS_REQ_ERROR.getMsg();
				logger.error("< UserWorkloadQueueController.getAllUserWorkloads() > 获取服务订单列表请求信息不正确: " + date);
			} else {
				userIds = userWorkloadService.queryAllUsers(date.getToday());
				
				UserWorkloadList userWorkloadList = new UserWorkloadList();
				for (int i=0; i<userIds.size(); i++){
					
					userWorkloads = userWorkloadService.queryByUserWorkloadUserid(userIds.get(i));
					user = userService.getByUserId(userIds.get(i));
					
					userWorkloadList.setUserName(user.getUserName());
					userWorkloadList.setUserWorkloadList(userWorkloads);
					
					userWorkloadLists.add(userWorkloadList);
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
			logger.error("< UserWorkloadQueueController.getAllUserWorkloads() > 获取排队列表失败."
					+ e.getMessage());
		}

		return JsonConverter.getResultObject(code, msg, userWorkloadLists);
	}

}
