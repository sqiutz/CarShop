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
import com.keeping.business.service.ServeQueueService;
import com.keeping.business.web.controller.converter.JsonConverter;
import com.keeping.business.web.controller.model.ServeQueue;
import com.keeping.business.web.controller.model.WebResultList;

@Controller
@RequestMapping("/servequeue.do")
public class ServeQueueController {

	  /**日志 */
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    
	/**用户信息Service */
    @Resource
	private ServeQueueService serveQueueService;
    
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
	public WebResultList<ServeQueue> getAllServeQueues(Integer step, HttpServletRequest request,HttpServletResponse response) {
		String code = BusinessCenterResCode.SYS_SUCCESS.getCode();
		String msg = BusinessCenterResCode.SYS_SUCCESS.getMsg();

		List<ServeQueue> ServeQueueList = new ArrayList<ServeQueue>();
		try {
			ServeQueueList = serveQueueService.getServeQueueByStep(step);
		}catch (BusinessServiceException ex) {
			code = ex.getErrorCode();
			msg = ex.getErrorMessage();
		}catch (Exception e) {
			code = BusinessCenterResCode.SYS_ERROR.getCode();
			msg = BusinessCenterResCode.SYS_ERROR.getMsg();
			logger.error("< ServeQueueController.getAllServeQueues() > 获取排队列表失败." + e.getMessage());
		}

		return JsonConverter.getResultObject(code, msg, ServeQueueList);
	}
	
}
