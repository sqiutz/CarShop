package com.keeping.business.service;

import java.util.List;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.web.controller.model.ServeQueue;

public interface ServeQueueService {

    public List<ServeQueue> getServeQueueByStep(Integer step) throws BusinessServiceException;
    
    public void addServeQueue(ServeQueue serveQueue) throws BusinessServiceException;
    
    public ServeQueue queryServeQueueByUserAndStep(Integer userId, Integer step) throws BusinessServiceException;;
    
    public void updateServeQueue(ServeQueue serveQueue) throws BusinessServiceException;
    
}
