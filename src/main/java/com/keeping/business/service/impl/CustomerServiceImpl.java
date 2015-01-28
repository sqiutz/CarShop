package com.keeping.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.common.rescode.BusinessCenterResCode;
import com.keeping.business.dal.dao.CustomerDao;
import com.keeping.business.dal.dao.UserDao;
import com.keeping.business.dal.model.CustomerDo;
import com.keeping.business.dal.model.UserDo;
import com.keeping.business.service.CustomerService;
import com.keeping.business.service.UserService;
import com.keeping.business.service.converter.CustomerConverter;
import com.keeping.business.service.converter.UserConverter;
import com.keeping.business.web.controller.UserController;
import com.keeping.business.web.controller.model.Customer;
import com.keeping.business.web.controller.model.User;

public class CustomerServiceImpl implements CustomerService{
	
	/**日志 */
//	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	/**用户信息DAO */
    private CustomerDao customerDao;

	public void addCustomer(Customer customer) throws BusinessServiceException {
		// TODO Auto-generated method stub
		if (customer == null){
			return;
		}
		
		customerDao.addCustomer(CustomerConverter.getCustomerDo(customer));
	}

	public void modifyCustomer(Customer customer) throws BusinessServiceException {
		// TODO Auto-generated method stub
		if (customer == null){
			return;
		}
		
		customerDao.modifyCustomer(CustomerConverter.getCustomerDo(customer));
	}

	public Customer getCustomerByPoliceNum(String policeNum)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		CustomerDo customerDo = customerDao.queryByPoliceNum(policeNum);
		
		if(null == customerDo) {
			return new Customer();
		}
		
		return CustomerConverter.getCustomer(customerDo);
	}

	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

}
