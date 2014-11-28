package com.keeping.business.dal.dao;

import java.util.List;

import com.keeping.business.dal.model.OrderDo;
import com.keeping.business.dal.model.UserDo;

public interface OrderDao extends BaseDao<OrderDo>{

	public List<OrderDo> queryByOrderstatus(Integer status);
	
	public void addOrder(OrderDo orderDo);
	
	public void updateOrder(OrderDo orderDo);
	
	public OrderDo queryOrderByBookNum(String bookNum);
	
	public OrderDo queryFirstForServeQueue();
	
	public List<OrderDo> queryByOrdersId(List<Integer> orderIdList);

}
