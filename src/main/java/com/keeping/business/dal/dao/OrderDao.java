package com.keeping.business.dal.dao;

import java.util.List;

import com.keeping.business.dal.model.OrderDo;

public interface OrderDao extends BaseDao<OrderDo>{

	public List<OrderDo> queryByOrderstatus(Integer status);

}