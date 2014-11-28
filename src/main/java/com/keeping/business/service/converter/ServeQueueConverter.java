package com.keeping.business.service.converter;

import com.keeping.business.dal.model.ServeQueueDo;
import com.keeping.business.web.controller.model.ServeQueue;

public class ServeQueueConverter {

	public static ServeQueue getServeQueue(ServeQueueDo serveQueueDo){
		
		if(serveQueueDo == null){
			return null;
		}

		ServeQueue serveQueue = new ServeQueue();
		
		serveQueue.setCreateTime(serveQueueDo.getCreateTime());
		serveQueue.setEndTime(serveQueueDo.getEndTime());
		serveQueue.setStartTime(serveQueueDo.getStartTime());
		serveQueue.setStep(serveQueueDo.getStep());
		serveQueue.setOrder_id(serveQueueDo.getOrder_id());
		serveQueue.setUser_id(serveQueueDo.getUser_id());
		
		return serveQueue;
	}
	
	public static ServeQueueDo getServeQueueDo(ServeQueue serveQueue){
		
		if(serveQueue == null){
			return null;
		}
		
		ServeQueueDo serveQueueDo = new ServeQueueDo();
		
		serveQueueDo.setEndTime(serveQueue.getEndTime());
		serveQueueDo.setOrder_id(serveQueue.getOrder_id());
		serveQueueDo.setStartTime(serveQueue.getStartTime());
		serveQueueDo.setStep(serveQueue.getStep());
		serveQueueDo.setUser_id(serveQueue.getUser_id());
		
		return serveQueueDo;
	}
}
