package com.keeping.business.service;

import java.util.List;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.web.controller.model.CashQueue;

public interface CashQueueService {

    public List<CashQueue> getCashQueueByStep(Integer step) throws BusinessServiceException;
    
    public CashQueue getCashQueueById(Integer id) throws BusinessServiceException;
    
    public CashQueue getCashQueueByOrderid(Integer id) throws BusinessServiceException;
    
    public void addCashQueue(CashQueue modifyQueue) throws BusinessServiceException;
    
//    public CashQueue queryCashQueueByUserAndStep(Integer userId, Integer step) throws BusinessServiceException;
    
    public void updateCashQueue(CashQueue modifyQueue) throws BusinessServiceException;
    
    public List<CashQueue> getCashQueueByStepAndUserId(CashQueue modifyQueue) throws BusinessServiceException;
    
}
