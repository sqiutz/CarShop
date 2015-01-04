package com.keeping.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.common.rescode.BusinessCenterResCode;
import com.keeping.business.dal.dao.JobTypeDao;
import com.keeping.business.dal.dao.UserDao;
import com.keeping.business.dal.model.JobTypeDo;
import com.keeping.business.dal.model.UserDo;
import com.keeping.business.service.JobTypeService;
import com.keeping.business.service.UserService;
import com.keeping.business.service.converter.JobTypeConverter;
import com.keeping.business.service.converter.UserConverter;
import com.keeping.business.web.controller.UserController;
import com.keeping.business.web.controller.model.JobType;
import com.keeping.business.web.controller.model.User;

public class JobTypeServiceImpl implements JobTypeService{
	
	/**日志 */
//	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	/**用户信息DAO */
    private JobTypeDao jobtypeDao;
    
	public void addJobType(JobType jobtype) throws BusinessServiceException {
		// TODO Auto-generated method stub
		if(jobtype == null){
			return;
		}
		
		jobtypeDao.addJobType(JobTypeConverter.getJobTypeDo(jobtype));
	}

	public void modifyJobType(JobType jobtype)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		if(jobtype == null){
			return;
		}
		
		jobtypeDao.modifyJobType(JobTypeConverter.getJobTypeDo(jobtype));
	}

	public JobType queryByKey(String key) throws BusinessServiceException {
		// TODO Auto-generated method stub
		JobTypeDo jobtypeDo = jobtypeDao.queryByKey(key);
		if(null == jobtypeDo) {
			return null;
		}
		return JobTypeConverter.getJobType(jobtypeDo);
	}

	public List<JobType> queryAll() throws BusinessServiceException {
		// TODO Auto-generated method stub
		List<JobTypeDo> jobtypeDoes = jobtypeDao.queryAll();
		List<JobType> jobtypes = new ArrayList<JobType>();
		
		if(null == jobtypeDoes) {
			return jobtypes;
		}
		
		for(int i=0; i<jobtypeDoes.size(); i++){
			jobtypes.add(JobTypeConverter.getJobType(jobtypeDoes.get(i)));
		}
		
		return jobtypes;
	}
	
	public void deleteJobType(Integer id) throws BusinessServiceException {
		// TODO Auto-generated method stub
		jobtypeDao.deleteJobType(id);
	}
	
	public JobTypeDao getJobTypeDao() {
		return jobtypeDao;
	}

	public void setJobTypeDao(JobTypeDao jobtypeDao) {
		this.jobtypeDao = jobtypeDao;
	}
	
}
