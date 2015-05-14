package com.keeping.business.service.converter;

import java.sql.Date;

import com.keeping.business.common.util.TimeUtil;
import com.keeping.business.dal.model.IssueQueueDo;
import com.keeping.business.web.controller.model.IssueQueue;

public class IssueQueueConverter {

	public static IssueQueue getIssueQueue(IssueQueueDo issueQueueDo){
		
		if(issueQueueDo == null){
			return null;
		}

		IssueQueue issueQueue = new IssueQueue();
		
		issueQueue.setId(issueQueueDo.getId());
		issueQueue.setCreateTime(issueQueueDo.getCreateTime());
		issueQueue.setEndTime(TimeUtil.transferFromUtilToSql(issueQueueDo.getEndTime()));
		issueQueue.setStartTime(TimeUtil.transferFromUtilToSql(issueQueueDo.getStartTime()));
		issueQueue.setStep(issueQueueDo.getStep());
		issueQueue.setOrderId(issueQueueDo.getOrderId());
		issueQueue.setUserId(issueQueueDo.getUserId());
		issueQueue.setJobType(issueQueueDo.getJobType());
		issueQueue.setTechnician(issueQueueDo.getTechnician());
		issueQueue.setAssignDate(TimeUtil.transferFromSqlToUtilDate(issueQueueDo.getAssignDate()));
		issueQueue.setAssignTime(TimeUtil.transferFromSqlToUtil(issueQueueDo.getAssignTime()));
		issueQueue.setIssuerId(issueQueueDo.getIssuerId());
		issueQueue.setIsSubContract(issueQueueDo.getIsSubContract());
		issueQueue.setIsWarrant(issueQueueDo.getIsWarrant());
		issueQueue.setForId(issueQueueDo.getForId());
		
		return issueQueue;
	}
	
	public static IssueQueueDo getIssueQueueDo(IssueQueue issueQueue){
		
		if(issueQueue == null){
			return null;
		}
		
		IssueQueueDo issueQueueDo = new IssueQueueDo();
		
		issueQueueDo.setAssignTime(TimeUtil.transferFromUtilToSql(issueQueue.getAssignTime()));
		
		issueQueueDo.setEndTime(TimeUtil.transferFromUtilToSql(issueQueue.getEndTime()));
		issueQueueDo.setOrderId(issueQueue.getOrderId());
		issueQueueDo.setStartTime(TimeUtil.transferFromUtilToSql(issueQueue.getStartTime()));
		issueQueueDo.setStep(issueQueue.getStep());
		issueQueueDo.setUserId(issueQueue.getUserId());
		issueQueueDo.setId(issueQueue.getId());
		issueQueueDo.setJobType(issueQueue.getJobType());
		issueQueueDo.setTechnician(issueQueue.getTechnician());
		issueQueueDo.setAssignDate(TimeUtil.transferFromUtilToSqlDate(issueQueue.getAssignDate()));
		issueQueueDo.setIssuerId(issueQueue.getIssuerId());
		issueQueueDo.setIsSubContract(issueQueue.getIsSubContract());
		issueQueueDo.setIsWarrant(issueQueue.getIsWarrant());
		issueQueueDo.setForId(issueQueue.getForId());
		
		return issueQueueDo;
	}
}
