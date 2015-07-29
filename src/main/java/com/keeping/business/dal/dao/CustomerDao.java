package com.keeping.business.dal.dao;

import java.util.List;

import com.keeping.business.dal.model.CustomerDo;
import com.keeping.business.dal.model.UserDo;

public interface CustomerDao extends BaseDao<UserDo>{
	
	public void addCustomer(CustomerDo customerDo);
	
	public void modifyCustomer(CustomerDo customerDo);
	
	public CustomerDo queryByPoliceNum(String policeNum);
}
