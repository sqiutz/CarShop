package com.keeping.business.dal.dao;

import java.util.List;

import com.keeping.business.dal.model.JobTypeDo;

public interface JobTypeDao{
	
	public JobTypeDo queryByKey(String key);
	
	public List<JobTypeDo> queryAll();
	
	public void addJobType(JobTypeDo jobtypeDo);
	
	public void deleteJobType(Integer id);
	
	public void modifyJobType(JobTypeDo jobtypeDo);

}
