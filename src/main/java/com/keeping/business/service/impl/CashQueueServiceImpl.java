package com.keeping.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.dal.dao.CashQueueDao;
import com.keeping.business.dal.model.CashQueueDo;
import com.keeping.business.service.CashQueueService;
import com.keeping.business.service.converter.CashQueueConverter;
import com.keeping.business.web.controller.UserController;
import com.keeping.business.web.controller.model.CashQueue;

public class CashQueueServiceImpl implements CashQueueService{

	/**日志 */
//	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	/**服务列表DAO */
	private CashQueueDao cashQueueDao;
	

//	public CashQueue queryCashQueueByUserAndStep(Integer userId, Integer step) {
//		// TODO Auto-generated method stub
//		CashQueue cashQueue = null;
//		List<CashQueueDo> cashQueueDoes = cashQueueDao.queryByUseridAndStep(userId, step);
//		
//		if(cashQueueDoes == null){
//			return new CashQueue();
//		}else if(cashQueueDoes.size() > 1){
//			logger.error("处理的serve订单数超过>1！" + BusinessCenterResCode.ORDER_ILLEGAL.getMsg());
//    		throw new BusinessServiceException(BusinessCenterResCode.ORDER_ILLEGAL.getCode(),
//    				BusinessCenterResCode.ORDER_ILLEGAL.getMsg());
//		}else{
//			cashQueue = CashQueueConverter.getCashQueue(cashQueueDoes.get(0));
//		}
//
//		return cashQueue;
//	}

	public void addCashQueue(CashQueue cashQueue)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		CashQueueDo cashQueueDo = CashQueueConverter.getCashQueueDo(cashQueue);
		
		cashQueueDao.addCashQueue(cashQueueDo);
	}
	

	public void updateCashQueue(CashQueue cashQueue)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		if(cashQueue == null){
			return;
		}
		
		cashQueueDao.updateCashQueue(CashQueueConverter.getCashQueueDo(cashQueue));
	}

	public List<CashQueue> getCashQueueByStep(Integer step)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
//		if (step == 0){
//			step = 2;
//		}
		List<CashQueueDo> cashQueueDoes = cashQueueDao.queryByCashQueuestep(step);
		List<CashQueue> cashQueues = new ArrayList<CashQueue>();
		
		if (cashQueueDoes == null){
			return cashQueues;
		}
		
		for (int i = 0; i < cashQueueDoes.size(); i++){
			cashQueues.add(CashQueueConverter.getCashQueue(cashQueueDoes.get(i)));
		}
		
		return cashQueues;
	}
	


	public CashQueue getCashQueueById(Integer id)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		CashQueueDo cashQueueDo = cashQueueDao.queryByCashQueueid(id);
		CashQueue cashQueue = null;
		
		if(cashQueueDo == null){
			return new CashQueue();
		}
		
		cashQueue = CashQueueConverter.getCashQueue(cashQueueDo);
		
		return cashQueue;
	}
	
	public CashQueue getCashQueueByOrderid(Integer id)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		CashQueueDo cashQueueDo = cashQueueDao.queryByOrderid(id);
		CashQueue cashQueue = null;
		
		if(cashQueueDo == null){
			return new CashQueue();
		}
		
		cashQueue = CashQueueConverter.getCashQueue(cashQueueDo);
		
		return cashQueue;
	}
	

	public List<CashQueue> getCashQueueByStepAndUserId(CashQueue cashQueue) throws BusinessServiceException {
		// TODO Auto-generated method stub
		
		CashQueueDo cashQueueDo = CashQueueConverter.getCashQueueDo(cashQueue);
		List<CashQueueDo> cashQueueDoes = cashQueueDao.queryByUseridAndStep(cashQueueDo);
		List<CashQueue> cashQueues = new ArrayList<CashQueue>();
		
		if (cashQueueDoes == null){
			return cashQueues;
		}
		
		for (int i = 0; i < cashQueueDoes.size(); i++){
			cashQueues.add(CashQueueConverter.getCashQueue(cashQueueDoes.get(i)));
		}
		
		return cashQueues;
	}

	public CashQueueDao getCashQueueDao() {
		return cashQueueDao;
	}

	public void setCashQueueDao(CashQueueDao cashQueueDao) {
		this.cashQueueDao = cashQueueDao;
	}



}
