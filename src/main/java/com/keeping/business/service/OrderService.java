package com.keeping.business.service;

import java.util.List;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.web.controller.model.Order;
import com.keeping.business.web.controller.model.OrderObject;
import com.keeping.business.web.controller.model.User;

public interface OrderService {

    public List<Order> getOrdersByStatus(Integer status) throws BusinessServiceException;
    
    public List<Order> getOrdersByBook(Integer book) throws BusinessServiceException;
    
    public void addOrder(Order order) throws BusinessServiceException;
    
    public void updateOrder(Order order) throws BusinessServiceException;
    
    public Order queryOrderByBookNum(String bookNum) throws BusinessServiceException;
    
    public Order queryOrderById(Integer id) throws BusinessServiceException;
 
    public Order queryFirstForServeQueue(Integer isBook) throws BusinessServiceException;
    
	public List<Order> getByOrdersId(List<Integer> orderIdList) throws BusinessServiceException;
	
	public List<Order> getAllOrders(Integer startStatus);
	
	public Order getOrdersByQueueNum(String queueNumber);
	
	public Order getOrdersByRegNum(String registerNumber);
	
	public Integer getOrderCountByStatusAndBook(OrderObject orderObject);
	
	public List<Order> getOrderByStatusAndBook(OrderObject orderObject);

}
