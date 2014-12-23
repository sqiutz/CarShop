package com.keeping.business.service.converter;

import com.keeping.business.dal.model.JobTypeDo;
import com.keeping.business.web.controller.model.JobType;

public class JobTypeConverter {

	/**
     * JobTypeDo==>JobType 
     */
    public static JobType getJobType(JobTypeDo jobtypeDo) {
        if (jobtypeDo == null) {
            return null;
        }
        JobType jobtype = new JobType();
        jobtype.setId(jobtypeDo.getId());
        jobtype.setName(jobtypeDo.getName());
        jobtype.setValue(jobtypeDo.getValue());
        
        return jobtype;
    }
    
    public static JobTypeDo getJobTypeDo(JobType jobtype) {
    	if(null == jobtype) {
    		return null;
    	}
    	JobTypeDo jobtypeDo = new JobTypeDo();
    	jobtypeDo.setId(jobtype.getId());
    	jobtypeDo.setName(jobtype.getName());
    	jobtypeDo.setValue(jobtype.getValue());
    
    	return jobtypeDo;
    }
}
