package com.keeping.business.service.converter;

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
		
		return order;
	}
	
	public static OrderDo getOrderDo(Order order){
		if (order == null){
			return null;
		}
		
		OrderDo orderDo = new OrderDo();
		
		orderDo.setStatus(order.getStatus());
		orderDo.setStartTime(order.getStartTime());
		orderDo.setRoofNum(order.getRoofNum());
		orderDo.setRegisterNum(order.getRegisterNum());
		orderDo.setQueueNum(order.getQueueNum());
		orderDo.setPromiseTime(order.getPromiseTime());
		orderDo.setEstimationTime(order.getEstimationTime());
		orderDo.setEndTime(order.getEndTime());
		orderDo.setBookNum(order.getBookNum());
		orderDo.setIsBook(order.getIsBook());
		
		return orderDo;
	}
}
