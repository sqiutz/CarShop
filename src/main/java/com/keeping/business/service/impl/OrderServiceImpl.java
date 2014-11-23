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
		List<OrderDo> orders = orderDao.queryByOrderstatus(status);
		
		List<Order> orders_front = new ArrayList<Order>();
		
		for (int i = 0; i < orders.size(); i++){
			orders_front.get(i).setCreateTime(orders.get(i).getCreateTime());
			orders_front.get(i).setEndTime(orders.get(i).getEndTime());
			orders_front.get(i).setEstimationTime(orders.get(i).getEstimationTime());
			orders_front.get(i).setPromiseTime(orders.get(i).getPromiseTime());
			orders_front.get(i).setQueueNum(orders.get(i).getQueueNum());
			orders_front.get(i).setRegisterNum(orders.get(i).getRegisterNum());
			orders_front.get(i).setRoofNum(orders.get(i).getRoofNum());
			orders_front.get(i).setStartTime(orders.get(i).getStartTime());
			orders_front.get(i).setStatus(orders.get(i).getStatus());
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
