package com.keeping.business.service;

import java.util.List;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.web.controller.model.Counter;

public interface CounterService {
	
    public void addCounter(Counter counter) throws BusinessServiceException;
    
    public void modifyCounter(Counter counter) throws BusinessServiceException;
    
    public Counter queryByKey(String key) throws BusinessServiceException;
    
    public List<Counter> queryAll() throws BusinessServiceException;

}
