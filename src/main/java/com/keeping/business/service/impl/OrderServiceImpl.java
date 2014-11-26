package com.keeping.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.dal.dao.OrderDao;
import com.keeping.business.dal.model.OrderDo;
import com.keeping.business.service.OrderService;
import com.keeping.business.web.controller.model.Order;

public class OrderServiceImpl implements OrderService{

	/**用户信息DAO */
	private OrderDao orderDao;

	public List<Order> getOrdersByStatus(Integer status)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		List<OrderDo> orderDoes = orderDao.queryByOrderstatus(status);
		
		List<Order> orders_front = new ArrayList<Order>();
		
		for (int i = 0; i < orderDoes.size(); i++){
			Order order = new Order();
			order.setCreateTime(orderDoes.get(i).getCreateTime());
			order.setEndTime(orderDoes.get(i).getEndTime());
			order.setEstimationTime(orderDoes.get(i).getEstimationTime());
			order.setPromiseTime(orderDoes.get(i).getPromiseTime());
			order.setQueueNum(orderDoes.get(i).getQueueNum());
			order.setRegisterNum(orderDoes.get(i).getRegisterNum());
			order.setRoofNum(orderDoes.get(i).getRoofNum());
			order.setStartTime(orderDoes.get(i).getStartTime());
			order.setStatus(orderDoes.get(i).getStatus());
			orders_front.add(order);
		}
		
		return orders_front;
	}

	public OrderDao getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	
}
