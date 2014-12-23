package com.keeping.business.dal.dao;

import com.keeping.business.dal.model.JobTypeDo;

public interface JobTypeDao{
	
	public JobTypeDo queryByKey(String key);
	
	public void addJobType(JobTypeDo jobtypeDo);
	
	public void modifyJobType(JobTypeDo jobtypeDo);

}
