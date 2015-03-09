package com.keeping.business.dal.dao;

import java.util.List;

import com.keeping.business.dal.model.OrderDo;
import com.keeping.business.dal.model.UserDo;

public interface OrderDao extends BaseDao<OrderDo>{

	public List<OrderDo> queryByOrderstatus(Integer status);
	
	public void addOrder(OrderDo orderDo);
	
	public void updateOrder(OrderDo orderDo);
	
	public OrderDo queryOrderByBookNum(String bookNum);
	
	public OrderDo queryOrderById(Integer id);
	
	public OrderDo queryFirstForServeQueue(Integer isBook);
	
	public List<OrderDo> queryByOrdersId(List<Integer> orderIdList);
	
	public List<OrderDo> getAllOrders(Integer status);
	
	public List<OrderDo> getAllOrdersFReport(OrderDo orderDo);
	
	public List<OrderDo> queryByOrderbook(Integer isBook);
	
	public OrderDo queryOrderByQueueNum(String queueNumber);
	
	public OrderDo queryOrderByRegisterNum(String registerNumber);
	
	public Integer queryCountByStatusAndBook(OrderDo orderDo);
	
	public List<OrderDo> queryByStatusAndBook(OrderDo orderDo);

}
