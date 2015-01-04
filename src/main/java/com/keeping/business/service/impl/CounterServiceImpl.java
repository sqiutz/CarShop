package com.keeping.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.common.rescode.BusinessCenterResCode;
import com.keeping.business.dal.dao.CounterDao;
import com.keeping.business.dal.dao.UserDao;
import com.keeping.business.dal.model.CounterDo;
import com.keeping.business.dal.model.UserDo;
import com.keeping.business.service.CounterService;
import com.keeping.business.service.UserService;
import com.keeping.business.service.converter.CounterConverter;
import com.keeping.business.service.converter.UserConverter;
import com.keeping.business.web.controller.UserController;
import com.keeping.business.web.controller.model.Counter;
import com.keeping.business.web.controller.model.User;

public class CounterServiceImpl implements CounterService{
	
	/**日志 */
//	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	/**用户信息DAO */
    private CounterDao counterDao;
    
	public void addCounter(Counter counter) throws BusinessServiceException {
		// TODO Auto-generated method stub
		if(counter == null){
			return;
		}
		
		counterDao.addCounter(CounterConverter.getCounterDo(counter));
	}

	public void modifyCounter(Counter counter)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		if(counter == null){
			return;
		}
		
		counterDao.modifyCounter(CounterConverter.getCounterDo(counter));
	}

	public Counter queryByKey(String key) throws BusinessServiceException {
		// TODO Auto-generated method stub
		CounterDo counterDo = counterDao.queryByKey(key);
		if(null == counterDo) {
			return null;
		}
		return CounterConverter.getCounter(counterDo);
	}

	public List<Counter> queryAll() throws BusinessServiceException {
		// TODO Auto-generated method stub
		List<CounterDo> counterDoes = counterDao.queryAll();
		List<Counter> counters = new ArrayList<Counter>();
		
		if(null == counterDoes) {
			return counters;
		}
		
		for(int i=0; i<counterDoes.size(); i++){
			counters.add(CounterConverter.getCounter(counterDoes.get(i)));
		}
		
		return counters;
	}
	
	public CounterDao getCounterDao() {
		return counterDao;
	}

	public void setCounterDao(CounterDao counterDao) {
		this.counterDao = counterDao;
	}


}
