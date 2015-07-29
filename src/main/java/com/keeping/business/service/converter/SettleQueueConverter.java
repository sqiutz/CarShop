package com.keeping.business.service.converter;

import com.keeping.business.common.util.TimeUtil;
import com.keeping.business.dal.model.SettleQueueDo;
import com.keeping.business.web.controller.model.SettleQueue;

public class SettleQueueConverter {

	public static SettleQueue getSettleQueue(SettleQueueDo SettleQueueDo){
		
		if(SettleQueueDo == null){
			return null;
		}

		SettleQueue SettleQueue = new SettleQueue();
		
		SettleQueue.setId(SettleQueueDo.getId());
		SettleQueue.setCreateTime(SettleQueueDo.getCreateTime());
		SettleQueue.setEndTime(TimeUtil.transferFromSqlToUtil(SettleQueueDo.getEndTime()));
		SettleQueue.setStartTime(TimeUtil.transferFromSqlToUtil(SettleQueueDo.getStartTime()));
		SettleQueue.setStep(SettleQueueDo.getStep());
		SettleQueue.setOrderId(SettleQueueDo.getOrderId());
		SettleQueue.setUserId(SettleQueueDo.getUserId());
		
		return SettleQueue;
	}
	
	public static SettleQueueDo getSettleQueueDo(SettleQueue SettleQueue){
		
		if(SettleQueue == null){
			return null;
		}
		
		SettleQueueDo SettleQueueDo = new SettleQueueDo();
		
		SettleQueueDo.setId(SettleQueue.getId());
		SettleQueueDo.setEndTime(TimeUtil.transferFromUtilToSql(SettleQueue.getEndTime()));
		SettleQueueDo.setOrderId(SettleQueue.getOrderId());
		SettleQueueDo.setStartTime(TimeUtil.transferFromUtilToSql(SettleQueue.getStartTime()));
		SettleQueueDo.setStep(SettleQueue.getStep());
		SettleQueueDo.setUserId(SettleQueue.getUserId());
		
		return SettleQueueDo;
	}
}
