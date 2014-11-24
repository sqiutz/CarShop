package com.keeping.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.dal.dao.ServeQueueDao;
import com.keeping.business.dal.model.ServeQueueDo;
import com.keeping.business.service.ServeQueueService;
import com.keeping.business.web.controller.model.ServeQueue;

public class ServeQueueServiceImpl implements ServeQueueService{

	/**用户信息DAO */
	private ServeQueueDao serveQueueDao;

	public List<ServeQueue> getServeQueueByStep(Integer step)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		List<ServeQueueDo> serveQueues = serveQueueDao.queryByServeQueuestep(step);
		
		List<ServeQueue> serveQueues_front = new ArrayList<ServeQueue>();
		
		for (int i = 0; i < serveQueues.size(); i++){
			serveQueues_front.get(i).setCreateTime(serveQueues.get(i).getCreateTime());
			serveQueues_front.get(i).setEndTime(serveQueues.get(i).getEndTime());
			serveQueues_front.get(i).setStartTime(serveQueues.get(i).getStartTime());
			serveQueues_front.get(i).setStep(serveQueues.get(i).getStep());
			serveQueues_front.get(i).setOrder_id(serveQueues.get(i).getOrder_id());
			serveQueues_front.get(i).setUser_id(serveQueues.get(i).getUser_id());
		}
		
		return serveQueues_front;
	}

	public ServeQueueDao getServeQueueDao() {
		return serveQueueDao;
	}

	public void setServeQueueDao(ServeQueueDao serveQueueDao) {
		this.serveQueueDao = serveQueueDao;
	}

}
