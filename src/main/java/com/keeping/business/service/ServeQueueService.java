package com.keeping.business.service;

import java.sql.Date;
import java.util.List;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.web.controller.model.EstimationTime;
import com.keeping.business.web.controller.model.ServeQueue;

public interface ServeQueueService {

    public List<ServeQueue> getServeQueueByStep(Integer step) throws BusinessServiceException;
    
    public ServeQueue getServeQueueById(Integer id) throws BusinessServiceException;
    
    public ServeQueue getServeQueueByOrderid(Integer id) throws BusinessServiceException;
    
    public void addServeQueue(ServeQueue serveQueue) throws BusinessServiceException;
    
//    public ServeQueue queryServeQueueByUserAndStep(Integer userId, Integer step) throws BusinessServiceException;
    
    public void updateServeQueue(ServeQueue serveQueue) throws BusinessServiceException;
    
    public List<ServeQueue> getServeQueueByStepAndUserId(ServeQueue serveQueue) throws BusinessServiceException;
    
    public List<EstimationTime> getElapseTimeByTime(Date startTime, Date endTime);
    
}
