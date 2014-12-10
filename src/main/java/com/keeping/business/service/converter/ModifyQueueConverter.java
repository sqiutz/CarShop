package com.keeping.business.service.converter;

import com.keeping.business.dal.model.ModifyQueueDo;
import com.keeping.business.web.controller.model.ModifyQueue;

public class ModifyQueueConverter {

	public static ModifyQueue getModifyQueue(ModifyQueueDo modifyQueueDo){
		
		if(modifyQueueDo == null){
			return null;
		}

		ModifyQueue modifyQueue = new ModifyQueue();
		
		modifyQueue.setCreateTime(modifyQueueDo.getCreateTime());
		modifyQueue.setEndTime(modifyQueueDo.getEndTime());
		modifyQueue.setStartTime(modifyQueueDo.getStartTime());
		modifyQueue.setStep(modifyQueueDo.getStep());
		modifyQueue.setOrderId(modifyQueueDo.getOrderId());
		modifyQueue.setUserId(modifyQueueDo.getUserId());
		
		return modifyQueue;
	}
	
	public static ModifyQueueDo getModifyQueueDo(ModifyQueue modifyQueue){
		
		if(modifyQueue == null){
			return null;
		}
		
		ModifyQueueDo modifyQueueDo = new ModifyQueueDo();
		
		modifyQueueDo.setEndTime(modifyQueue.getEndTime());
		modifyQueueDo.setOrderId(modifyQueue.getOrderId());
		modifyQueueDo.setStartTime(modifyQueue.getStartTime());
		modifyQueueDo.setStep(modifyQueue.getStep());
		modifyQueueDo.setUserId(modifyQueue.getUserId());
		
		return modifyQueueDo;
	}
}
