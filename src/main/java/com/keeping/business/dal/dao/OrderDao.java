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
	
	public List<OrderDo> getAllOrders(OrderDo orderDo);
	
	public List<OrderDo> getAllOrdersFReport(OrderDo orderDo);
	
	public List<OrderDo> getAllOrdersFBook(OrderDo orderDo);
	
	public List<OrderDo> queryByOrderbook(Integer isBook);
	
	public OrderDo queryOrderByQueueNum(String queueNumber);
	
	public OrderDo queryOrderByRegisterNum(OrderDo orderDo);
	
	public OrderDo queryOrderByRegNumInAnyStatus(OrderDo orderDo);
	
	public Integer queryCountByStatusAndBook(OrderDo orderDo);
	
	public List<OrderDo> queryByStatusAndBook(OrderDo orderDo);
	
	public List<OrderDo> queryByBookAndExpress(OrderDo orderDo);
	
	public List<OrderDo> queryByBook(OrderDo orderDo);

}
