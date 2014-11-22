package com.keeping.business.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.common.rescode.BusinessCenterResCode;
import com.keeping.business.service.OrderService;
import com.keeping.business.web.controller.converter.JsonConverter;
import com.keeping.business.web.controller.model.Order;
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
	
}
