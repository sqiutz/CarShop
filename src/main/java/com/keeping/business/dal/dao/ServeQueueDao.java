package com.keeping.business.dal.dao;

import java.util.List;

import com.keeping.business.dal.model.ServeQueueDo;

public interface ServeQueueDao extends BaseDao<ServeQueueDo>{

	public List<ServeQueueDo> queryByServeQueuestep(Integer step);
	
	public ServeQueueDo queryByServeQueueid(Integer id);
	
	public ServeQueueDo queryByOrderid(Integer orderId);
	
	public void addServeQueue(ServeQueueDo serveQueueDo);
	
	public List<ServeQueueDo> queryByUseridAndStep(ServeQueueDo serveQueueDo);  //
	
	public void updateServeQueue(ServeQueueDo serveQueueDo);

}
