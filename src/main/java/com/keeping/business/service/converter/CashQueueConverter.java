package com.keeping.business.service.converter;

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
		cashQueue.setEndTime(cashQueueDo.getEndTime());
		cashQueue.setStartTime(cashQueueDo.getStartTime());
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
		cashQueueDo.setEndTime(cashQueue.getEndTime());
		cashQueueDo.setOrderId(cashQueue.getOrderId());
		cashQueueDo.setStartTime(cashQueue.getStartTime());
		cashQueueDo.setStep(cashQueue.getStep());
		cashQueueDo.setUserId(cashQueue.getUserId());
		
		return cashQueueDo;
	}
}
