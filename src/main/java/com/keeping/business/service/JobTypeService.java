package com.keeping.business.service;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.web.controller.model.JobType;

public interface JobTypeService {
	
    public void addJobType(JobType jobtype) throws BusinessServiceException;
    
    public void modifyJobType(JobType jobtype) throws BusinessServiceException;
    
    public JobType queryByKey(String key) throws BusinessServiceException;

}
