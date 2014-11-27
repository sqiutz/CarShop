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
	

	public void addServeQueue(ServeQueue serveQueue)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		ServeQueueDo serveQueueDo = new ServeQueueDo();
		
		serveQueueDo.setEndTime(serveQueue.getEndTime());
		serveQueueDo.setOrder_id(serveQueue.getOrder_id());
		serveQueueDo.setStartTime(serveQueue.getStartTime());
		serveQueueDo.setStep(serveQueue.getStep());
		serveQueueDo.setUser_id(serveQueue.getUser_id());
		
		serveQueueDao.addServeQueue(serveQueueDo);
	}

	public List<ServeQueue> getServeQueueByStep(Integer step)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		List<ServeQueueDo> serveQueueDoes = serveQueueDao.queryByServeQueuestep(step);
		
		List<ServeQueue> serveQueues = new ArrayList<ServeQueue>();
		
		for (int i = 0; i < serveQueues.size(); i++){
			ServeQueue serveQueue = new ServeQueue();
			serveQueue.setCreateTime(serveQueueDoes.get(i).getCreateTime());
			serveQueue.setEndTime(serveQueueDoes.get(i).getEndTime());
			serveQueue.setStartTime(serveQueueDoes.get(i).getStartTime());
			serveQueue.setStep(serveQueueDoes.get(i).getStep());
			serveQueue.setOrder_id(serveQueueDoes.get(i).getOrder_id());
			serveQueue.setUser_id(serveQueueDoes.get(i).getUser_id());
			serveQueues.add(serveQueue);
		}
		
		return serveQueues;
	}

	public ServeQueueDao getServeQueueDao() {
		return serveQueueDao;
	}

	public void setServeQueueDao(ServeQueueDao serveQueueDao) {
		this.serveQueueDao = serveQueueDao;
	}


}
