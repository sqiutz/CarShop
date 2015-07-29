package com.keeping.business.dal.dao;

import java.util.List;

import com.keeping.business.dal.model.CashQueueDo;

public interface CashQueueDao extends BaseDao<CashQueueDo>{

	public List<CashQueueDo> queryByCashQueuestep(Integer step);
	
	public CashQueueDo queryByCashQueueid(Integer id);
	
	public CashQueueDo queryByOrderid(Integer orderId);
	
	public void addCashQueue(CashQueueDo serveQueueDo);
	
	public List<CashQueueDo> queryByUseridAndStep(CashQueueDo serveQueueDo);  //
	
	public void updateCashQueue(CashQueueDo serveQueueDo);

}
