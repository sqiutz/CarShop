package com.keeping.business.service.converter;

import com.keeping.business.dal.model.UserWorkloadDo;
import com.keeping.business.web.controller.model.UserWorkload;

public class UserWorkloadConverter {

	public static UserWorkload getUserWorkload(UserWorkloadDo userWorkloadDo){
		if (userWorkloadDo == null){
			return null;
		}
		
		UserWorkload userWorkload = new UserWorkload();
		userWorkload.setId(userWorkloadDo.getId());
		userWorkload.setCreateTime(userWorkloadDo.getCreateTime());
		userWorkload.setEndTime(userWorkloadDo.getEndTime());
		userWorkload.setStartTime(userWorkloadDo.getStartTime());
		userWorkload.setModifyTime(userWorkloadDo.getModifyTime());
		userWorkload.setAllocatedTime(userWorkloadDo.getAllocatedTime());
		userWorkload.setComment(userWorkloadDo.getComment());
		userWorkload.setAdditionalHours(userWorkloadDo.getAdditionalHours());
		userWorkload.setGeneralRepaire(userWorkloadDo.getGeneralRepaire());
		userWorkload.setHumanResource(userWorkloadDo.getHumanResource());
		

		
		return userWorkload;
	}
	
	public static UserWorkloadDo getUserWorkloadDo(UserWorkload userWorkload){
		if (userWorkload == null){
			return null;
		}
		
		UserWorkloadDo userWorkloadDo = new UserWorkloadDo();
		
		userWorkloadDo.setId(userWorkload.getId());
		userWorkloadDo.setCreateTime(userWorkload.getCreateTime());
		userWorkloadDo.setEndTime(userWorkload.getEndTime());
		userWorkloadDo.setStartTime(userWorkload.getStartTime());
		userWorkloadDo.setModifyTime(userWorkload.getModifyTime());
		userWorkloadDo.setAllocatedTime(userWorkload.getAllocatedTime());
		userWorkloadDo.setComment(userWorkload.getComment());
		userWorkloadDo.setAdditionalHours(userWorkload.getAdditionalHours());
		userWorkloadDo.setGeneralRepaire(userWorkload.getGeneralRepaire());
		userWorkloadDo.setHumanResource(userWorkload.getHumanResource());
		
		return userWorkloadDo;
	}
}
