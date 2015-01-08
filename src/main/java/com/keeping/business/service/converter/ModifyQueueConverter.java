package com.keeping.business.service.converter;

import java.sql.Date;

import com.keeping.business.common.util.TimeUtil;
import com.keeping.business.dal.model.ModifyQueueDo;
import com.keeping.business.web.controller.model.ModifyQueue;

public class ModifyQueueConverter {

	public static ModifyQueue getModifyQueue(ModifyQueueDo modifyQueueDo){
		
		if(modifyQueueDo == null){
			return null;
		}

		ModifyQueue modifyQueue = new ModifyQueue();
		
		modifyQueue.setId(modifyQueueDo.getId());
		modifyQueue.setCreateTime(modifyQueueDo.getCreateTime());
		modifyQueue.setEndTime(modifyQueueDo.getEndTime());
		modifyQueue.setStartTime(modifyQueueDo.getStartTime());
		modifyQueue.setStep(modifyQueueDo.getStep());
		modifyQueue.setOrderId(modifyQueueDo.getOrderId());
		modifyQueue.setUserId(modifyQueueDo.getUserId());
		modifyQueue.setIsSubContract(modifyQueueDo.getIsSubContract());
		modifyQueue.setIsWarrant(modifyQueueDo.getIsWarrant());
		modifyQueue.setJobType(modifyQueueDo.getJobType());
		modifyQueue.setTechnician(modifyQueueDo.getTechnician());
		modifyQueue.setAssignDate(TimeUtil.transferFromSqlToUtilDate(modifyQueueDo.getAssignDate()));
		modifyQueue.setAdditionTime(modifyQueueDo.getAdditionTime());
		modifyQueue.setAssignTime(TimeUtil.transferFromSqlToUtil(modifyQueueDo.getAssignTime()));
		modifyQueue.setModifierId(modifyQueueDo.getModifierId());
		
		return modifyQueue;
	}
	
	public static ModifyQueueDo getModifyQueueDo(ModifyQueue modifyQueue){
		
		if(modifyQueue == null){
			return null;
		}
		
		ModifyQueueDo modifyQueueDo = new ModifyQueueDo();
		
		modifyQueueDo.setAssignTime(TimeUtil.transferFromUtilToSql(modifyQueue.getAssignTime()));
		
		modifyQueueDo.setEndTime(modifyQueue.getEndTime());
		modifyQueueDo.setOrderId(modifyQueue.getOrderId());
		modifyQueueDo.setStartTime(modifyQueue.getStartTime());
		modifyQueueDo.setStep(modifyQueue.getStep());
		modifyQueueDo.setUserId(modifyQueue.getUserId());
		modifyQueueDo.setId(modifyQueue.getId());
		modifyQueueDo.setIsSubContract(modifyQueue.getIsSubContract());
		modifyQueueDo.setIsWarrant(modifyQueue.getIsWarrant());
		modifyQueueDo.setJobType(modifyQueue.getJobType());
		modifyQueueDo.setTechnician(modifyQueue.getTechnician());
		modifyQueueDo.setAssignDate(TimeUtil.transferFromUtilToSqlDate(modifyQueue.getAssignDate()));
		modifyQueueDo.setAdditionTime(modifyQueue.getAdditionTime());
		modifyQueueDo.setModifierId(modifyQueue.getModifierId());
		
		return modifyQueueDo;
	}
}
