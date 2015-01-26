package com.keeping.business.service.converter;

import com.keeping.business.common.util.TimeUtil;
import com.keeping.business.dal.model.CustomerDo;
import com.keeping.business.web.controller.model.Customer;

public class CustomerConverter {

	/**
     * CustomerDO==>Customer 
     */
    public static Customer getCustomer(CustomerDo customerDo) {
        if (customerDo == null) {
            return null;
        }
        
        Customer customer = new Customer();
        customer.setId(customerDo.getId());
        customer.setMobilephone(customerDo.getMobilephone());
        customer.setPasswd(customerDo.getPasswd());
        customer.setPoliceNum(customerDo.getPoliceNum());
        customer.setUserName(customerDo.getUserName());
        customer.setCreateTime(TimeUtil.transferFromSqlToUtil(customerDo.getCreateTime()));
        customer.setModifyTime(TimeUtil.transferFromUtilToSql(customer.getModifyTime()));
        
        return customer;
    }
    
    public static CustomerDo getCustomerDo(Customer customer) {
    	if(null == customer) {
    		return null;
    	}
    	
    	CustomerDo customerDo = new CustomerDo();
    	customerDo.setId(customer.getId());
    	customerDo.setMobilephone(customerDo.getMobilephone());
    	customerDo.setPasswd(customerDo.getPasswd());
    	customerDo.setPoliceNum(customerDo.getPoliceNum());
    	customerDo.setUserName(customerDo.getUserName());
    	customerDo.setCreateTime(TimeUtil.transferFromUtilToSql(customer.getCreateTime()));
    	customerDo.setModifyTime(TimeUtil.transferFromUtilToSql(customer.getModifyTime()));
    	
    	return customerDo;
    }
}
