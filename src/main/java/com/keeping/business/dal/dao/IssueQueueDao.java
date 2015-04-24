package com.keeping.business.dal.dao;

import java.util.List;

import com.keeping.business.dal.model.IssueQueueDo;

public interface IssueQueueDao extends BaseDao<IssueQueueDo>{

	public void addIssueQueue(IssueQueueDo issueQueueDo);
	
	public void updateIssueQueue(IssueQueueDo issueQueueDo);
	
	public IssueQueueDo queryByIssueQueueid(Integer id);
	
	public IssueQueueDo queryByIssueQueueOrderId(Integer orderId);
	
}
