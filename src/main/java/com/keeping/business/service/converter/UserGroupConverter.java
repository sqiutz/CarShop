package com.keeping.business.service.converter;

import com.keeping.business.dal.model.UserDo;
import com.keeping.business.dal.model.UserGroupDo;
import com.keeping.business.web.controller.model.User;
import com.keeping.business.web.controller.model.UserGroup;

public class UserGroupConverter {

	/**
     * UserGroupDO==>UserGroup 
     */
    public static UserGroup getUserGroup(UserGroupDo userGroupDo) {
        if (userGroupDo == null) {
            return null;
        }
        UserGroup userGroup = new UserGroup();
        userGroup.setId(userGroupDo.getId());
        userGroup.setGroupName(userGroupDo.getGroupName());
        userGroup.setComment(userGroupDo.getComment());
        
        return userGroup;
    }
    
    public static UserGroupDo getUserGroupDo(UserGroup userGroup) {
    	if(null == userGroup) {
    		return null;
    	}
    	UserGroupDo userGroupDo = new UserGroupDo();
    	userGroupDo.setId(userGroup.getId());
    	userGroupDo.setGroupName(userGroup.getGroupName());
    	userGroupDo.setComment(userGroup.getComment());
    	
    	return userGroupDo;
    }
}
