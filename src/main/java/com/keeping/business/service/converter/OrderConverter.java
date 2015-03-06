package com.keeping.business.service.converter;

import com.keeping.business.common.rescode.BusinessCenterOrderStatus;
import com.keeping.business.common.util.TimeUtil;
import com.keeping.business.dal.model.OrderDo;
import com.keeping.business.web.controller.model.Order;

public class OrderConverter {

	public static Order getOrder(OrderDo orderDo){
		if (orderDo == null){
			return null;
		}
		
		Order order = new Order();
		order.setId(orderDo.getId());
		order.setCreateTime(orderDo.getCreateTime());
		order.setEndTime(orderDo.getEndTime());
		order.setEstimationTime(orderDo.getEstimationTime());
		order.setPromiseTime(orderDo.getPromiseTime());
		order.setQueueNum(orderDo.getQueueNum());
		order.setRegisterNum(orderDo.getRegisterNum());
		order.setRoofNum(orderDo.getRoofNum());
		order.setStartTime(orderDo.getStartTime());
		order.setStatus(orderDo.getStatus());
		order.setBookNum(orderDo.getBookNum());
		order.setIsBook(orderDo.getIsBook());
		order.setCustomerId(orderDo.getCustomerId());
		order.setBookTime(TimeUtil.transferFromSqlToUtil(orderDo.getBookTime()));
		order.setAssignDate(TimeUtil.transferFromSqlToUtilDate(orderDo.getAssignDate()));
		order.setBookStartTime(TimeUtil.transferFromSqlToUtil(orderDo.getBookStartTime()));
		order.setBookEndTime(TimeUtil.transferFromSqlToUtil(orderDo.getBookEndTime()));
		order.setJobType(orderDo.getJobType());
		order.setBeginDate(TimeUtil.transferFromSqlToUtilDate(orderDo.getBeginDate()));
		order.setEndDate(TimeUtil.transferFromSqlToUtilDate(orderDo.getEndDate()));
		
		order.setStatusValue((BusinessCenterOrderStatus.getById(orderDo.getStatus())).getStatus());
		
		return order;
	}
	
	public static OrderDo getOrderDo(Order order){
		if (order == null){
			return null;
		}
		
		OrderDo orderDo = new OrderDo();
		orderDo.setBookTime(TimeUtil.transferFromUtilToSql(order.getBookTime()));
		orderDo.setId(order.getId());
		orderDo.setStatus(order.getStatus());
		orderDo.setStartTime(order.getStartTime());
		orderDo.setRoofNum(order.getRoofNum());
		orderDo.setRegisterNum(order.getRegisterNum());
		orderDo.setQueueNum(order.getQueueNum());
		orderDo.setPromiseTime(TimeUtil.transferFromUtilToSql(order.getPromiseTime()));
		orderDo.setEstimationTime(order.getEstimationTime());
		orderDo.setEndTime(order.getEndTime());
		orderDo.setBookNum(order.getBookNum());
		orderDo.setIsBook(order.getIsBook());
		orderDo.setCustomerId(order.getCustomerId());
		orderDo.setBookTime(TimeUtil.transferFromUtilToSql(order.getBookTime()));
		orderDo.setAssignDate(TimeUtil.transferFromUtilToSqlDate(order.getAssignDate()));
		orderDo.setBookStartTime(TimeUtil.transferFromUtilToSql(order.getBookStartTime()));
		orderDo.setBookEndTime(TimeUtil.transferFromUtilToSql(order.getBookEndTime()));
		orderDo.setJobType(order.getJobType());
		orderDo.setBeginDate(TimeUtil.transferFromUtilToSqlDate(order.getBeginDate()));
		orderDo.setEndDate(TimeUtil.transferFromUtilToSqlDate(order.getEndDate()));
		
		return orderDo;
	}
}
