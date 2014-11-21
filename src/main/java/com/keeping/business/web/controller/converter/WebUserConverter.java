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
        userPro.setUserId(user.getUserId());
        userPro.setUserType(user.getUserType());
        userPro.setIsAdmin(user.getIsAdmin());
        userPro.setProImgPath(user.getProImgPath());
        userPro.setPasswd(user.getPasswd());
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
        user.setUserId(userPro.getUserId());
        user.setUserName(userPro.getUserName());
        user.setUserType(userPro.getUserType());
        user.setIsAdmin(0);							//userPro.getIsAdmin());
        user.setProImgPath(userPro.getProImgPath());
        user.setPasswd(userPro.getPasswd());
     
        int dt = Integer.parseInt(DateUtil.getCurrentDateTime1("yyyyMMdd"));
        user.setRegisterDate(dt);
        return user;
    }

	public static User getUser(WebRequestHeadImg userImgs) {
		if (userImgs == null) return null;
		
		User user = new User();
		user.setUserId(userImgs.getUserId());
		user.setProImgPath(userImgs.getProImgPath());
        return user;
	}
	
	public static void updateSessionProfile(UserProfile userPro, UserProfile ssUser) {
		ssUser.setNickName(userPro.getNickName());
		ssUser.setUserName(userPro.getUserName());
		ssUser.setIsAdmin(userPro.getIsAdmin());
		ssUser.setProImgPath(userPro.getProImgPath());
	}

}
