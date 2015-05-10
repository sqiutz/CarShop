package com.keeping.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.dal.dao.SettleQueueDao;
import com.keeping.business.dal.model.SettleQueueDo;
import com.keeping.business.service.SettleQueueService;
import com.keeping.business.service.converter.SettleQueueConverter;
import com.keeping.business.web.controller.UserController;
import com.keeping.business.web.controller.model.SettleQueue;

public class SettleQueueServiceImpl implements SettleQueueService{

	/**日志 */
//	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	/**服务列表DAO */
	private SettleQueueDao settleQueueDao;
	

//	public SettleQueue querySettleQueueByUserAndStep(Integer userId, Integer step) {
//		// TODO Auto-generated method stub
//		SettleQueue SettleQueue = null;
//		List<SettleQueueDo> SettleQueueDoes = SettleQueueDao.queryByUseridAndStep(userId, step);
//		
//		if(SettleQueueDoes == null){
//			return new SettleQueue();
//		}else if(SettleQueueDoes.size() > 1){
//			logger.error("处理的serve订单数超过>1！" + BusinessCenterResCode.ORDER_ILLEGAL.getMsg());
//    		throw new BusinessServiceException(BusinessCenterResCode.ORDER_ILLEGAL.getCode(),
//    				BusinessCenterResCode.ORDER_ILLEGAL.getMsg());
//		}else{
//			SettleQueue = SettleQueueConverter.getSettleQueue(SettleQueueDoes.get(0));
//		}
//
//		return SettleQueue;
//	}

	public void addSettleQueue(SettleQueue SettleQueue)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		SettleQueueDo SettleQueueDo = SettleQueueConverter.getSettleQueueDo(SettleQueue);
		
		settleQueueDao.addSettleQueue(SettleQueueDo);
	}
	

	public void updateSettleQueue(SettleQueue SettleQueue)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		if(SettleQueue == null){
			return;
		}
		
		settleQueueDao.updateSettleQueue(SettleQueueConverter.getSettleQueueDo(SettleQueue));
	}

	public List<SettleQueue> getSettleQueueByStep(Integer step)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
//		if (step == 0){
//			step = 2;
//		}
		List<SettleQueueDo> SettleQueueDoes = settleQueueDao.queryBySettleQueuestep(step);
		List<SettleQueue> SettleQueues = new ArrayList<SettleQueue>();
		
		if (SettleQueueDoes == null){
			return SettleQueues;
		}
		
		for (int i = 0; i < SettleQueueDoes.size(); i++){
			SettleQueues.add(SettleQueueConverter.getSettleQueue(SettleQueueDoes.get(i)));
		}
		
		return SettleQueues;
	}
	


	public SettleQueue getSettleQueueById(Integer id)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		SettleQueueDo SettleQueueDo = settleQueueDao.queryBySettleQueueid(id);
		SettleQueue SettleQueue = null;
		
		if(SettleQueueDo == null){
			return new SettleQueue();
		}
		
		SettleQueue = SettleQueueConverter.getSettleQueue(SettleQueueDo);
		
		return SettleQueue;
	}
	
	public SettleQueue getSettleQueueByOrderid(Integer id)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		SettleQueueDo SettleQueueDo = settleQueueDao.queryByOrderid(id);
		SettleQueue SettleQueue = null;
		
		if(SettleQueueDo == null){
			return new SettleQueue();
		}
		
		SettleQueue = SettleQueueConverter.getSettleQueue(SettleQueueDo);
		
		return SettleQueue;
	}
	

	public List<SettleQueue> getSettleQueueByStepAndUserId(SettleQueue SettleQueue) throws BusinessServiceException {
		// TODO Auto-generated method stub
		
		SettleQueueDo SettleQueueDo = SettleQueueConverter.getSettleQueueDo(SettleQueue);
		List<SettleQueueDo> SettleQueueDoes = settleQueueDao.queryByUseridAndStep(SettleQueueDo);
		List<SettleQueue> SettleQueues = new ArrayList<SettleQueue>();
		
		if (SettleQueueDoes == null){
			return SettleQueues;
		}
		
		for (int i = 0; i < SettleQueueDoes.size(); i++){
			SettleQueues.add(SettleQueueConverter.getSettleQueue(SettleQueueDoes.get(i)));
		}
		
		return SettleQueues;
	}

	public SettleQueueDao getSettleQueueDao() {
		return settleQueueDao;
	}

	public void setSettleQueueDao(SettleQueueDao settleQueueDao) {
		this.settleQueueDao = settleQueueDao;
	}



}
