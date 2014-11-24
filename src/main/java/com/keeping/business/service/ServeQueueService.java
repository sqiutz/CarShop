package com.keeping.business.service;

import java.util.List;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.web.controller.model.ServeQueue;

public interface ServeQueueService {

    public List<ServeQueue> getServeQueueByStep(Integer step) throws BusinessServiceException;
    
}
