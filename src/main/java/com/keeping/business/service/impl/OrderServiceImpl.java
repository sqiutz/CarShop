package com.keeping.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.dal.dao.OrderDao;
import com.keeping.business.dal.model.OrderDo;
import com.keeping.business.service.OrderService;
import com.keeping.business.service.converter.OrderConverter;
import com.keeping.business.web.controller.model.Order;

public class OrderServiceImpl implements OrderService{

	/**用户信息DAO */
	private OrderDao orderDao;
	

	public Order queryFirstForServeQueue() throws BusinessServiceException {
		// TODO Auto-generated method stub
		OrderDo orderDo = orderDao.queryFirstForServeQueue();
		
		Order order = OrderConverter.getOrder(orderDo);
		
		return order;
	}

	public List<Order> getOrdersByStatus(Integer status)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		List<Order> orders_front = new ArrayList<Order>();
		List<OrderDo> orderDoes = orderDao.queryByOrderstatus(status);
		
		if (orderDoes == null){
			return orders_front; 
		}
		
		for (int i = 0; i < orderDoes.size(); i++){
			orders_front.add(OrderConverter.getOrder(orderDoes.get(i)));
		}
		
		return orders_front;
	}
	
	public void addOrder(Order order) throws BusinessServiceException {
		// TODO Auto-generated method stub
		
		OrderDo orderDo = OrderConverter.getOrderDo(order);
		
		orderDao.addOrder(orderDo);
	}


	public void updateOrder(Order order) throws BusinessServiceException {
		// TODO Auto-generated method stub
		
		OrderDo orderDo = OrderConverter.getOrderDo(order);
		
		orderDao.updateOrder(orderDo);
	}
	

	public Order queryOrderByBookNum(String bookNum)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		OrderDo orderDo = orderDao.queryOrderByBookNum(bookNum);
		
		Order order = OrderConverter.getOrder(orderDo);
		
		return order;
	}
	
	public OrderDao getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	
}
