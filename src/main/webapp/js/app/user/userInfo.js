define(["md5"],function(){
	var UserInfo = {
		//用户系统登录方法
		login : function(options) {
			if(!options.hasMd5){
				//options.data.passwd=hex_md5(options.data.passwd);
			}
			$.common.ajax("userLogin", {
				type:"POST",
				data:options.data,
				success:function(data){					
				    options.success(data);					
				}
			});
		},
		//获取用户组列表
		getAllGroups : function(options) {
			$.common.ajax("getAllGroups", {
                success:function(data){                 
                    options.success(data);                  
                },
				complete:function(data){                 
                    options.complete(data);                  
                }
            });
		},
		//获取用户列表
		getAllUsers : function(options) {
		    $.common.ajax("getAllUsers", {
                success:function(data){                 
                    options.success(data);                  
                }
            });
		},
		//添加用户
		addUser : function(options) {
            $.common.ajax("addUser", {
                type:"POST",
                data:options.data,
                success:function(data){                 
                    options.success(data);                  
                }
            });
        },
        //修改用户信息
        modifyUser : function(options) {
            $.common.ajax("modifyUser", {
                type:"POST",
                data:options.data,
                success:function(data){                 
                    options.success(data);                  
                }
            });
        },
        //获取用户信息
        getUserByName : function(options) {
            $.common.ajax("modifyUser", {
                type:"POST",
                data:options.data,
                success:function(data){                 
                    options.success(data);                  
                }
            });
        },
		//检测用户是否已经登录
		checkLogin : function(options){
		    $.common.ajax("checkLogin", {
                type:"POST",
                success:function(data){                 
                    options.success(data);                  
                }
            });
		},
		// 获取用户对应的柜台
		checkCounter : function(options){
		    $.common.ajax("checkCounter", {
                type:"POST",
                success:function(data){                 
                    options.success(data);                  
                }
            });
		},
		// 获取配置信息
		getProperty : function(options){
            $.common.ajax("getProperty", {
                type:"POST",
                data:options.data,
                success:function(data){                 
                    options.success(data);                  
                }
            });
        }
	};

	$.UserInfo = UserInfo;
	return UserInfo;
});
