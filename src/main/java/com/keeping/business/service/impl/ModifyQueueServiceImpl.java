package com.keeping.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.dal.dao.ModifyQueueDao;
import com.keeping.business.dal.model.ModifyQueueDo;
import com.keeping.business.service.ModifyQueueService;
import com.keeping.business.service.converter.ModifyQueueConverter;
import com.keeping.business.web.controller.UserController;
import com.keeping.business.web.controller.model.ModifyQueue;

public class ModifyQueueServiceImpl implements ModifyQueueService{

	/**日志 */
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	/**服务列表DAO */
	private ModifyQueueDao modifyQueueDao;
	

//	public ModifyQueue queryModifyQueueByUserAndStep(Integer userId, Integer step) {
//		// TODO Auto-generated method stub
//		ModifyQueue modifyQueue = null;
//		List<ModifyQueueDo> modifyQueueDoes = modifyQueueDao.queryByUseridAndStep(userId, step);
//		
//		if(modifyQueueDoes == null){
//			return new ModifyQueue();
//		}else if(modifyQueueDoes.size() > 1){
//			logger.error("处理的serve订单数超过>1！" + BusinessCenterResCode.ORDER_ILLEGAL.getMsg());
//    		throw new BusinessServiceException(BusinessCenterResCode.ORDER_ILLEGAL.getCode(),
//    				BusinessCenterResCode.ORDER_ILLEGAL.getMsg());
//		}else{
//			modifyQueue = ModifyQueueConverter.getModifyQueue(modifyQueueDoes.get(0));
//		}
//
//		return modifyQueue;
//	}

	public void addModifyQueue(ModifyQueue modifyQueue)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		ModifyQueueDo modifyQueueDo = ModifyQueueConverter.getModifyQueueDo(modifyQueue);
		
		modifyQueueDao.addModifyQueue(modifyQueueDo);
	}
	

	public void updateModifyQueue(ModifyQueue modifyQueue)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		if(modifyQueue == null){
			return;
		}
		
		modifyQueueDao.updateModifyQueue(ModifyQueueConverter.getModifyQueueDo(modifyQueue));
	}

	public List<ModifyQueue> getModifyQueueByStep(Integer step)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		List<ModifyQueueDo> modifyQueueDoes = modifyQueueDao.queryByModifyQueuestep(step);
		List<ModifyQueue> modifyQueues = new ArrayList<ModifyQueue>();
		
		if (modifyQueueDoes == null){
			return modifyQueues;
		}
		
		for (int i = 0; i < modifyQueueDoes.size(); i++){
			modifyQueues.add(ModifyQueueConverter.getModifyQueue(modifyQueueDoes.get(i)));
		}
		
		return modifyQueues;
	}
	

	public List<ModifyQueue> getModifyQueueByStepAndUserId(ModifyQueue modifyQueue) throws BusinessServiceException {
		// TODO Auto-generated method stub
		
		ModifyQueueDo modifyQueueDo = ModifyQueueConverter.getModifyQueueDo(modifyQueue);
		List<ModifyQueueDo> modifyQueueDoes = modifyQueueDao.queryByUseridAndStep(modifyQueueDo);
		List<ModifyQueue> modifyQueues = new ArrayList<ModifyQueue>();
		
		if (modifyQueueDoes == null){
			return modifyQueues;
		}
		
		for (int i = 0; i < modifyQueueDoes.size(); i++){
			modifyQueues.add(ModifyQueueConverter.getModifyQueue(modifyQueueDoes.get(i)));
		}
		
		return modifyQueues;
	}

	public ModifyQueueDao getModifyQueueDao() {
		return modifyQueueDao;
	}

	public void setModifyQueueDao(ModifyQueueDao modifyQueueDao) {
		this.modifyQueueDao = modifyQueueDao;
	}


}
