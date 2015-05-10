package com.keeping.business.service;

import java.util.List;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.web.controller.model.SettleQueue;

public interface SettleQueueService {

    public List<SettleQueue> getSettleQueueByStep(Integer step) throws BusinessServiceException;
    
    public SettleQueue getSettleQueueById(Integer id) throws BusinessServiceException;
    
    public SettleQueue getSettleQueueByOrderid(Integer id) throws BusinessServiceException;
    
    public void addSettleQueue(SettleQueue settleQueue) throws BusinessServiceException;
    
//    public SettleQueue querySettleQueueByUserAndStep(Integer userId, Integer step) throws BusinessServiceException;
    
    public void updateSettleQueue(SettleQueue settleQueue) throws BusinessServiceException;
    
    public List<SettleQueue> getSettleQueueByStepAndUserId(SettleQueue settleQueue) throws BusinessServiceException;
    
}
