package com.keeping.business.service.converter;

import com.keeping.business.dal.model.PropertyDo;
import com.keeping.business.dal.model.UserDo;
import com.keeping.business.web.controller.model.Property;
import com.keeping.business.web.controller.model.User;

public class PropertyConverter {

	/**
     * PropertyDo==>Property 
     */
    public static Property getProperty(PropertyDo propertyDo) {
        if (propertyDo == null) {
            return null;
        }
        Property property = new Property();
        property.setId(propertyDo.getId());
        property.setPkey(propertyDo.getPkey());
        property.setPvalue(propertyDo.getPvalue());
        
        return property;
    }
    
    public static PropertyDo getPropertyDo(Property property) {
    	if(null == property) {
    		return null;
    	}
    	PropertyDo propertyDo = new PropertyDo();
    	propertyDo.setId(property.getId());
    	propertyDo.setPkey(property.getPkey());
    	propertyDo.setPvalue(property.getPvalue());
    
    	return propertyDo;
    }
}
