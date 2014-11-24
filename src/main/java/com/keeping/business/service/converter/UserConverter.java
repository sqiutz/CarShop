package com.keeping.business.service.converter;

import com.keeping.business.dal.model.UserDo;
import com.keeping.business.web.controller.model.User;

public class UserConverter {

	/**
     * UserDO==>User 
     */
    public static User getUser(UserDo userDo) {
        if (userDo == null) {
            return null;
        }
        User user = new User();
        user.setId(userDo.getId());
        user.setUserName(userDo.getUserName());
        user.setIsAdmin(userDo.getIsAdmin());
        user.setProImgPath(userDo.getImgPath());
        user.setGroupId(userDo.getGroupId());
        user.setPasswd(userDo.getPasswd());
        user.setIsValid(userDo.getIsValid());
        user.setCreateTime(userDo.getCreateTime());
        user.setModifyTime(userDo.getModifyTime());
        
        return user;
    }
    
}
