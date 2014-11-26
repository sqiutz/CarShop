package com.keeping.business.service;

import java.util.List;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.web.controller.model.Order;

public interface OrderService {

    public List<Order> getOrdersByStatus(Integer status) throws BusinessServiceException;
    
    public void addOrder(Order order) throws BusinessServiceException;
    
    public void updateOrder(Order order) throws BusinessServiceException;
    
}
