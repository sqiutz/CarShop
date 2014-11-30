package com.keeping.business.dal.dao;

import com.keeping.business.dal.model.PropertyDo;

public interface PropertyDao{
	
	public PropertyDo queryByKey(String key);
	
	public void addProperty(PropertyDo propertyDo);
	
	public void modifyProperty(PropertyDo propertyDo);

}
