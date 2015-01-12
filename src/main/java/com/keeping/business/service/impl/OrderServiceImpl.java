package com.keeping.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.common.util.TimeUtil;
import com.keeping.business.dal.dao.OrderDao;
import com.keeping.business.dal.model.OrderDo;
import com.keeping.business.dal.model.UserDo;
import com.keeping.business.service.OrderService;
import com.keeping.business.service.converter.OrderConverter;
import com.keeping.business.service.converter.UserConverter;
import com.keeping.business.web.controller.UserController;
import com.keeping.business.web.controller.model.Order;
import com.keeping.business.web.controller.model.OrderObject;
import com.keeping.business.web.controller.model.User;

public class OrderServiceImpl implements OrderService {

	/** 用户信息DAO */
	private OrderDao orderDao;

	/** 日志 */
//	private Logger logger = LoggerFactory.getLogger(UserController.class);

	public Order queryFirstForServeQueue(Integer isBook) throws BusinessServiceException {
		// TODO Auto-generated method stub
		Order order = null;
		OrderDo orderDo = orderDao.queryFirstForServeQueue(isBook);

		if (orderDo == null) {
			return new Order();
		} else {
			order = OrderConverter.getOrder(orderDo);
		}

		return order;
	}

	public List<Order> getOrdersByStatus(Integer status)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		List<Order> orders_front = new ArrayList<Order>();
		List<OrderDo> orderDoes = orderDao.queryByOrderstatus(status);

		if (orderDoes == null) {
			return orders_front;
		} else {
			for (int i = 0; i < orderDoes.size(); i++) {
				orders_front.add(OrderConverter.getOrder(orderDoes.get(i)));
			}
		}

		return orders_front;
	}

	public Integer getOrderCountByStatusAndBook(OrderObject orderObject) {
		// TODO Auto-generated method stub
		
		OrderDo orderDo = new OrderDo();
		orderDo.setStatus(orderObject.getStatus());
		orderDo.setAssignDate(TimeUtil.transferFromUtilToSqlDate(orderObject.getNow()));
		orderDo.setIsBook(orderObject.getIsBook());
		
		Integer count = orderDao.queryCountByStatusAndBook(orderDo);
		
		return count;
	}

	public List<Order> getOrdersByBook(Integer book)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		List<Order> orders_front = new ArrayList<Order>();
		List<OrderDo> orderDoes = orderDao.queryByOrderbook(book);

		if (orderDoes == null) {
			return orders_front;
		} else {
			for (int i = 0; i < orderDoes.size(); i++) {
				orders_front.add(OrderConverter.getOrder(orderDoes.get(i)));
			}
		}

		return orders_front;
	}



	public List<Order> getAllOrders(Integer startStatus) {
		// TODO Auto-generated method stub
		List<Order> orders_front = new ArrayList<Order>();
		List<OrderDo> orderDoes = orderDao.getAllOrders(startStatus);

		if (orderDoes == null) {
			return orders_front;
		} else {
			for (int i = 0; i < orderDoes.size(); i++) {
				orders_front.add(OrderConverter.getOrder(orderDoes.get(i)));
			}
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
		Order order = null;
		OrderDo orderDo = orderDao.queryOrderByBookNum(bookNum);

		if (orderDo == null){
			return new Order();
		}else{
			order = OrderConverter.getOrder(orderDo);
		}
		
		return order;
	}
	
	public Order getOrdersByQueueNum(String queueNumber) {
		// TODO Auto-generated method stub
		Order order = null;
		OrderDo orderDo = orderDao.queryOrderByQueueNum(queueNumber);

		if (orderDo == null){
			return new Order();
		}else{
			order = OrderConverter.getOrder(orderDo);
		}
		
		return order;
	}
	

	public Order getOrdersByRegNum(String registerNumber) {
		// TODO Auto-generated method stub
		Order order = null;
		OrderDo orderDo = orderDao.queryOrderByRegisterNum(registerNumber);

		if (orderDo == null){
			return new Order();
		}else{
			order = OrderConverter.getOrder(orderDo);
		}
		
		return order;
	}
	
	public Order queryOrderById(Integer id) throws BusinessServiceException {
		// TODO Auto-generated method stub
		Order order = null;
		OrderDo orderDo = orderDao.queryOrderById(id);

		if (orderDo == null){
			return new Order();
		}else{
			order = OrderConverter.getOrder(orderDo);
		}
		
		return order;
	}

	public List<Order> getByOrdersId(List<Integer> orderIdList)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		List<OrderDo> orderDoes = orderDao.queryByOrdersId(orderIdList);
		
		List<Order> orders = new ArrayList<Order>();
		
		for (int i=0; i<orderDoes.size(); i++){
			orders.add(OrderConverter.getOrder(orderDoes.get(i)));
		}
		
		return orders;
	}

	
	public OrderDao getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}



}
