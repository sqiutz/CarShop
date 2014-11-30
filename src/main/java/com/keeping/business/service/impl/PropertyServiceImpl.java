package com.keeping.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.common.rescode.BusinessCenterResCode;
import com.keeping.business.dal.dao.PropertyDao;
import com.keeping.business.dal.dao.UserDao;
import com.keeping.business.dal.model.PropertyDo;
import com.keeping.business.dal.model.UserDo;
import com.keeping.business.service.PropertyService;
import com.keeping.business.service.UserService;
import com.keeping.business.service.converter.PropertyConverter;
import com.keeping.business.service.converter.UserConverter;
import com.keeping.business.web.controller.UserController;
import com.keeping.business.web.controller.model.Property;
import com.keeping.business.web.controller.model.User;

public class PropertyServiceImpl implements PropertyService{
	
	/**日志 */
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	/**用户信息DAO */
    private PropertyDao propertyDao;
    
	public void addProperty(Property property) throws BusinessServiceException {
		// TODO Auto-generated method stub
		if(property == null){
			return;
		}
		
		propertyDao.addProperty(PropertyConverter.getPropertyDo(property));
	}

	public void modifyProperty(Property property)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		if(property == null){
			return;
		}
		
		propertyDao.modifyProperty(PropertyConverter.getPropertyDo(property));
	}

	public Property queryById(Integer id) throws BusinessServiceException {
		// TODO Auto-generated method stub
		PropertyDo propertyDo = propertyDao.queryById(id);
		if(null == propertyDo) {
			return null;
		}
		return PropertyConverter.getProperty(propertyDo);
	}

	public PropertyDao getPropertyDao() {
		return propertyDao;
	}

	public void setPropertyDao(PropertyDao propertyDao) {
		this.propertyDao = propertyDao;
	}

}
