package com.keeping.business.service;

import java.util.List;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.web.controller.model.IssueQueue;
import com.keeping.business.web.controller.model.StepObject;

public interface IssueQueueService {
    
    public IssueQueue getIssueQueueById(Integer id) throws BusinessServiceException;
    
    public void addIssueQueue(IssueQueue issueQueue) throws BusinessServiceException;
    
    public void updateIssueQueue(IssueQueue issueQueue) throws BusinessServiceException;
    
    public IssueQueue getIssueQueueByOrderId(Integer id) throws BusinessServiceException;
    
}
