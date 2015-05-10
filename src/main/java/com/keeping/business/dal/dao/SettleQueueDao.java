package com.keeping.business.dal.dao;

import java.util.List;

import com.keeping.business.dal.model.SettleQueueDo;

public interface SettleQueueDao extends BaseDao<SettleQueueDo>{

	public List<SettleQueueDo> queryBySettleQueuestep(Integer step);
	
	public SettleQueueDo queryBySettleQueueid(Integer id);
	
	public SettleQueueDo queryByOrderid(Integer orderId);
	
	public void addSettleQueue(SettleQueueDo settleQueueDo);
	
	public List<SettleQueueDo> queryByUseridAndStep(SettleQueueDo settleQueueDo);  //
	
	public void updateSettleQueue(SettleQueueDo settleQueueDo);

}
