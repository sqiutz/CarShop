package com.keeping.business.service.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.common.rescode.BusinessCenterResCode;
import com.keeping.business.dal.dao.ServeQueueDao;
import com.keeping.business.dal.model.ServeQueueDo;
import com.keeping.business.service.ServeQueueService;
import com.keeping.business.service.converter.ServeQueueConverter;
import com.keeping.business.web.controller.UserController;
import com.keeping.business.web.controller.model.EstimationTime;
import com.keeping.business.web.controller.model.ServeQueue;

public class ServeQueueServiceImpl implements ServeQueueService{

	/**日志 */
//	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	/**服务列表DAO */
	private ServeQueueDao serveQueueDao;
	

//	public ServeQueue queryServeQueueByUserAndStep(Integer userId, Integer step) {
//		// TODO Auto-generated method stub
//		ServeQueue serveQueue = null;
//		List<ServeQueueDo> serveQueueDoes = serveQueueDao.queryByUseridAndStep(userId, step);
//		
//		if(serveQueueDoes == null){
//			return new ServeQueue();
//		}else if(serveQueueDoes.size() > 1){
//			logger.error("处理的serve订单数超过>1！" + BusinessCenterResCode.ORDER_ILLEGAL.getMsg());
//    		throw new BusinessServiceException(BusinessCenterResCode.ORDER_ILLEGAL.getCode(),
//    				BusinessCenterResCode.ORDER_ILLEGAL.getMsg());
//		}else{
//			serveQueue = ServeQueueConverter.getServeQueue(serveQueueDoes.get(0));
//		}
//
//		return serveQueue;
//	}

	public void addServeQueue(ServeQueue serveQueue)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		ServeQueueDo serveQueueDo = ServeQueueConverter.getServeQueueDo(serveQueue);
		
		serveQueueDao.addServeQueue(serveQueueDo);
	}
	

	public void updateServeQueue(ServeQueue serveQueue)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		if(serveQueue == null){
			return;
		}
		
		serveQueueDao.updateServeQueue(ServeQueueConverter.getServeQueueDo(serveQueue));
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
	

	public List<EstimationTime> getElapseTimeByTime(Date startTime, Date endTime) {
		// TODO Auto-generated method stub
		ServeQueue serveQueue = new ServeQueue();
		Timestamp startTimestap =  new java.sql.Timestamp(startTime.getTime());
		Timestamp endTimestap =  new java.sql.Timestamp(endTime.getTime());
		serveQueue.setStartTime(startTimestap);
		serveQueue.setEndTime(endTimestap);
		
		List<EstimationTime> estimationTimes = new ArrayList<EstimationTime>();
		
		Integer start = startTime.getDay();
		Integer end = endTime.getDay();
		for(int i = start; i<=end; i++){
		}
		
		return estimationTimes;
		
	}
	
	public ServeQueue getServeQueueById(Integer id)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		ServeQueueDo serveQueueDo = serveQueueDao.queryByServeQueueid(id);
		ServeQueue serveQueue = new ServeQueue();
		
		if (serveQueueDo == null){
			return serveQueue;
		}
		
		serveQueue = ServeQueueConverter.getServeQueue(serveQueueDo);
		
		return serveQueue;
	}
	
	public ServeQueue getServeQueueByOrderid(Integer id)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		ServeQueueDo serveQueueDo = serveQueueDao.queryByOrderid(id);
		ServeQueue serveQueue = new ServeQueue();
		
		if (serveQueueDo == null){
			return serveQueue;
		}
		
		serveQueue = ServeQueueConverter.getServeQueue(serveQueueDo);
		
		return serveQueue;
	}

	public List<ServeQueue> getServeQueueByStepAndUserId(ServeQueue serveQueue) throws BusinessServiceException {
		// TODO Auto-generated method stub
		
		ServeQueueDo serveQueueDo = ServeQueueConverter.getServeQueueDo(serveQueue);
		List<ServeQueueDo> serveQueueDoes = serveQueueDao.queryByUseridAndStep(serveQueueDo);
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
