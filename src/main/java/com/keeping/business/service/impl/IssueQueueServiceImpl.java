package com.keeping.business.service.impl;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.dal.dao.IssueQueueDao;
import com.keeping.business.dal.model.IssueQueueDo;
import com.keeping.business.service.IssueQueueService;
import com.keeping.business.service.converter.IssueQueueConverter;
import com.keeping.business.web.controller.model.IssueQueue;

public class IssueQueueServiceImpl implements IssueQueueService{

	/**日志 */
//	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	/**服务列表DAO */
	private IssueQueueDao issueQueueDao;

	public void addIssueQueue(IssueQueue issueQueue)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		IssueQueueDo issueQueueDo = IssueQueueConverter.getIssueQueueDo(issueQueue);
		
		issueQueueDao.addIssueQueue(issueQueueDo);
	}
	

	public void updateIssueQueue(IssueQueue issueQueue)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		if(issueQueue == null){
			return;
		}
		
		issueQueueDao.updateIssueQueue(IssueQueueConverter.getIssueQueueDo(issueQueue));
	}

	public IssueQueue getIssueQueueById(Integer id)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		IssueQueueDo issueQueueDo = issueQueueDao.queryByIssueQueueid(id);
		IssueQueue issueQueue = null;
		
		if(issueQueueDo == null){
			return new IssueQueue();
		}
		
		issueQueue = IssueQueueConverter.getIssueQueue(issueQueueDo);
		
		return issueQueue;
	}

	public IssueQueueDao getIssueQueueDao() {
		return issueQueueDao;
	}

	public void setIssueQueueDao(IssueQueueDao issueQueueDao) {
		this.issueQueueDao = issueQueueDao;
	}



}
