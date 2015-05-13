package com.keeping.business.service;

import java.util.List;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.web.controller.model.ModifyQueue;
import com.keeping.business.web.controller.model.StepObject;

public interface ModifyQueueService {

    public List<ModifyQueue> getModifyQueueByStep(StepObject step) throws BusinessServiceException;
    
    public ModifyQueue getModifyQueueById(Integer id) throws BusinessServiceException;
    
    public ModifyQueue getModifyQueueByForId(Integer id) throws BusinessServiceException;
    
    public ModifyQueue getModifyQueueByOrderid(Integer id) throws BusinessServiceException;
    
    public void addModifyQueue(ModifyQueue modifyQueue) throws BusinessServiceException;
    
//    public ModifyQueue queryModifyQueueByUserAndStep(Integer userId, Integer step) throws BusinessServiceException;
    
    public void updateModifyQueue(ModifyQueue modifyQueue) throws BusinessServiceException;
    
    public List<ModifyQueue> getModifyQueueByStepAndUserId(ModifyQueue modifyQueue) throws BusinessServiceException;
    
    public List<Integer> getAllTodayWorkers(java.util.Date now);
    
    public List<ModifyQueue> getModifyQueueByUserId(ModifyQueue modifyQueue);
    
    public List<ModifyQueue> getModifyQueueByModifierId(ModifyQueue modifyQueue);
    
}
