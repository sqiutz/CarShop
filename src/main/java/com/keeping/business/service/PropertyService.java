package com.keeping.business.service;

import com.keeping.business.common.exception.BusinessServiceException;
import com.keeping.business.web.controller.model.Property;

public interface PropertyService {
	
    public void addProperty(Property property) throws BusinessServiceException;
    
    public void modifyProperty(Property property) throws BusinessServiceException;
    
    public Property queryById(Integer id) throws BusinessServiceException;

}
