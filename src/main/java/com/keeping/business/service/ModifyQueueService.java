package com.keeping.business.service;

import java.util.List;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.web.controller.model.ModifyQueue;

public interface ModifyQueueService {

    public List<ModifyQueue> getModifyQueueByStep(Integer step) throws BusinessServiceException;
    
    public void addModifyQueue(ModifyQueue modifyQueue) throws BusinessServiceException;
    
//    public ModifyQueue queryModifyQueueByUserAndStep(Integer userId, Integer step) throws BusinessServiceException;
    
    public void updateModifyQueue(ModifyQueue modifyQueue) throws BusinessServiceException;
    
    public List<ModifyQueue> getModifyQueueByStepAndUserId(ModifyQueue modifyQueue) throws BusinessServiceException;
    
}
