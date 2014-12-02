package com.keeping.business.web.controller.converter;

import com.keeping.business.common.util.DateUtil;
import com.keeping.business.web.controller.model.User;
import com.keeping.business.web.controller.model.UserProfile;
import com.keeping.business.web.controller.model.WebRequestHeadImg;

public class WebUserConverter {
	  /**
     * User==>UserProfile
     */
    public static UserProfile getUserProfile(User user) {
        if (user == null) {
            return null;
        }
        UserProfile userPro = new UserProfile();
        userPro.setIsAdmin(user.getIsAdmin());
        userPro.setProImgPath(user.getProImgPath());
        userPro.setPasswd(user.getPasswd());
        userPro.setGroupId(user.getGroupId());
        userPro.setId(user.getId());
        userPro.setIsValid(user.getIsValid());
        userPro.setUserName(user.getUserName());
        userPro.setCounter(user.getCounter());
        return userPro;
    }

    /**
     * UserProfile==>User
     */
    public static User getUser(UserProfile userPro) {
        if (userPro == null) {
            return null;
        }
        User user = new User();
        user.setId(userPro.getId());
        user.setUserName(userPro.getUserName());
        user.setIsAdmin(userPro.getIsAdmin());				//userPro.getIsAdmin());
        user.setProImgPath(userPro.getProImgPath());
        user.setPasswd(userPro.getPasswd());
        user.setGroupId(userPro.getGroupId());
        user.setIsValid(userPro.getIsValid());
        user.setCounter(userPro.getCounter());
     
//      int dt = Integer.parseInt(DateUtil.getCurrentDateTime1("yyyyMMdd"));

        return user;
    }

	public static User getUser(WebRequestHeadImg userImgs) {
		if (userImgs == null) return null;
		
		User user = new User();
		user.setProImgPath(userImgs.getProImgPath());
        return user;
	}
	
	public static void updateSessionProfile(UserProfile userPro, UserProfile ssUser) {
		ssUser.setUserName(userPro.getUserName());
		ssUser.setIsAdmin(userPro.getIsAdmin());
		ssUser.setProImgPath(userPro.getProImgPath());
	}
}
