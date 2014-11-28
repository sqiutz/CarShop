package com.keeping.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.dal.dao.ServeQueueDao;
import com.keeping.business.dal.model.ServeQueueDo;
import com.keeping.business.service.ServeQueueService;
import com.keeping.business.service.converter.ServeQueueConverter;
import com.keeping.business.web.controller.model.ServeQueue;

public class ServeQueueServiceImpl implements ServeQueueService{

	/**用户信息DAO */
	private ServeQueueDao serveQueueDao;
	

	public void addServeQueue(ServeQueue serveQueue)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		ServeQueueDo serveQueueDo = ServeQueueConverter.getServeQueueDo(serveQueue);
		
		serveQueueDao.addServeQueue(serveQueueDo);
	}

	public List<ServeQueue> getServeQueueByStep(Integer step)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		List<ServeQueueDo> serveQueueDoes = serveQueueDao.queryByServeQueuestep(step);
		List<ServeQueue> serveQueues = new ArrayList<ServeQueue>();
		
		if (serveQueueDoes == null){
			return serveQueues;
		}
		
		for (int i = 0; i < serveQueueDoes.size(); i++){
			serveQueues.add(ServeQueueConverter.getServeQueue(serveQueueDoes.get(i)));
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
