package com.keeping.business.service;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.web.controller.model.Customer;

public interface CustomerService {
	
    public void addCustomer(Customer user) throws BusinessServiceException;
  
    public void modifyCustomer(Customer user) throws BusinessServiceException;
    
    public Customer getCustomerByPoliceNum(String policeNum) throws BusinessServiceException;


}
