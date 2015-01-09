package com.keeping.business.service.converter;

import com.keeping.business.common.util.TimeUtil;
import com.keeping.business.dal.model.CashQueueDo;
import com.keeping.business.web.controller.model.CashQueue;

public class CashQueueConverter {

	public static CashQueue getCashQueue(CashQueueDo cashQueueDo){
		
		if(cashQueueDo == null){
			return null;
		}

		CashQueue cashQueue = new CashQueue();
		
		cashQueue.setId(cashQueueDo.getId());
		cashQueue.setCreateTime(cashQueueDo.getCreateTime());
		cashQueue.setEndTime(TimeUtil.transferFromSqlToUtil(cashQueueDo.getEndTime()));
		cashQueue.setStartTime(TimeUtil.transferFromSqlToUtil(cashQueueDo.getStartTime()));
		cashQueue.setStep(cashQueueDo.getStep());
		cashQueue.setOrderId(cashQueueDo.getOrderId());
		cashQueue.setUserId(cashQueueDo.getUserId());
		
		return cashQueue;
	}
	
	public static CashQueueDo getCashQueueDo(CashQueue cashQueue){
		
		if(cashQueue == null){
			return null;
		}
		
		CashQueueDo cashQueueDo = new CashQueueDo();
		
		cashQueueDo.setId(cashQueue.getId());
		cashQueueDo.setEndTime(TimeUtil.transferFromUtilToSql(cashQueue.getEndTime()));
		cashQueueDo.setOrderId(cashQueue.getOrderId());
		cashQueueDo.setStartTime(TimeUtil.transferFromUtilToSql(cashQueue.getStartTime()));
		cashQueueDo.setStep(cashQueue.getStep());
		cashQueueDo.setUserId(cashQueue.getUserId());
		
		return cashQueueDo;
	}
}
