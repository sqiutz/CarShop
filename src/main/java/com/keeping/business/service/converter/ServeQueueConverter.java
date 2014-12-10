package com.keeping.business.service.converter;

import com.keeping.business.dal.model.ServeQueueDo;
import com.keeping.business.web.controller.model.ServeQueue;

public class ServeQueueConverter {

	public static ServeQueue getServeQueue(ServeQueueDo serveQueueDo){
		
		if(serveQueueDo == null){
			return null;
		}

		ServeQueue serveQueue = new ServeQueue();
		
		serveQueue.setId(serveQueueDo.getId());
		serveQueue.setCreateTime(serveQueueDo.getCreateTime());
		serveQueue.setModifyTime(serveQueueDo.getModifyTime());
		serveQueue.setEndTime(serveQueueDo.getEndTime());
		serveQueue.setStartTime(serveQueueDo.getStartTime());
		serveQueue.setStep(serveQueueDo.getStep());
		serveQueue.setOrderId(serveQueueDo.getOrderId());
		serveQueue.setUserId(serveQueueDo.getUserId());
		
		return serveQueue;
	}
	
	public static ServeQueueDo getServeQueueDo(ServeQueue serveQueue){
		
		if(serveQueue == null){
			return null;
		}
		
		ServeQueueDo serveQueueDo = new ServeQueueDo();
		
		serveQueueDo.setId(serveQueue.getId());
		serveQueueDo.setEndTime(serveQueue.getEndTime());
		serveQueueDo.setOrderId(serveQueue.getOrderId());
		serveQueueDo.setStartTime(serveQueue.getStartTime());
		serveQueueDo.setCreateTime(serveQueue.getCreateTime());
		serveQueueDo.setStep(serveQueue.getStep());
		serveQueueDo.setUserId(serveQueue.getUserId());
		serveQueueDo.setModifyTime(serveQueue.getModifyTime());
		
		return serveQueueDo;
	}
}
