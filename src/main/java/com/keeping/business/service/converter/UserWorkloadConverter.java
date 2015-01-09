package com.keeping.business.service.converter;

import com.keeping.business.common.util.TimeUtil;
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
		userWorkload.setEndTime(TimeUtil.transferFromSqlToUtil(userWorkloadDo.getEndTime()));
		userWorkload.setStartTime(TimeUtil.transferFromSqlToUtil(userWorkloadDo.getStartTime()));
		userWorkload.setModifyTime(userWorkloadDo.getModifyTime());
		userWorkload.setAllocatedTime(TimeUtil.transferFromSqlToUtil(userWorkloadDo.getAllocatedTime()));
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
		userWorkloadDo.setEndTime(TimeUtil.transferFromUtilToSql(userWorkload.getEndTime()));
		userWorkloadDo.setStartTime(TimeUtil.transferFromUtilToSql(userWorkload.getStartTime()));
		userWorkloadDo.setModifyTime(userWorkload.getModifyTime());
		userWorkloadDo.setAllocatedTime(TimeUtil.transferFromUtilToSql(userWorkload.getAllocatedTime()));
		userWorkloadDo.setComment(userWorkload.getComment());
		userWorkloadDo.setAdditionalHours(userWorkload.getAdditionalHours());
		userWorkloadDo.setGeneralRepaire(userWorkload.getGeneralRepaire());
		userWorkloadDo.setHumanResource(userWorkload.getHumanResource());
		
		return userWorkloadDo;
	}
}
