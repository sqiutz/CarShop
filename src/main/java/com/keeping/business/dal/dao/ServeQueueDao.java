package com.keeping.business.dal.dao;

import java.util.List;

import com.keeping.business.dal.model.ServeQueueDo;

public interface ServeQueueDao extends BaseDao<ServeQueueDo>{

	public List<ServeQueueDo> queryByServeQueuestep(Integer step);
	
	public void addServeQueue(ServeQueueDo serveQueueDo);
	
	public List<ServeQueueDo> queryByUseridAndStep(Integer userId, Integer step);
	
	public void updateServeQueue(ServeQueueDo serveQueueDo);

}
