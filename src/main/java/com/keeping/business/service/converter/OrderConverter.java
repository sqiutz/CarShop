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
		order.setBookTime(TimeUtil.transferFromSqlToUtil(orderDo.getBookTime()));
		order.setAssignDate(TimeUtil.transferFromSqlToUtilDate(orderDo.getAssignDate()));
		
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
		orderDo.setBookTime(TimeUtil.transferFromUtilToSql(order.getBookTime()));
		orderDo.setAssignDate(TimeUtil.transferFromUtilToSqlDate(order.getAssignDate()));
		
		return orderDo;
	}
}
