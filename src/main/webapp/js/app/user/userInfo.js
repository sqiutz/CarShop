define(["md5"],function(){
	var UserInfo = {
		//用户系统登录方法
		login : function(options) {
			$.common.ajax("userLogin", {
				data:options.data,
				success:function(data){					
				    options.success(data);					
				}
			});
		},
		logout : function(options) {
            $.common.ajax("logout", {
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
        disableCounter : function(options) {
            $.common.ajax("disableCounter", {
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
                success:function(data){                 
                    options.success(data);                  
                }
            });
		},
		// 获取用户对应的柜台
		checkCounter : function(options){
		    $.common.ajax("checkCounter", {
                data:options.data,
                success:function(data){                 
                    options.success(data);                  
                }
            });
		},
		// 获取配置信息
		getProperty : function(options){
            $.common.ajax("getProperty", {
                data:options.data,
                success:function(data){                 
                    options.success(data);                  
                }
            });
        },
		// 修改配置信息
        modifyProperty : function(options){
            $.common.ajax("modifyProperty", {
                type:"POST",
                data:options.data,
                success:function(data){                 
                    options.success(data);                  
                }
            });
        }
	};

	$.UserInfo = UserInfo;
	
	$('#logout').bind('click', function(){
	    $.UserInfo.logout({
	        success : function(data) {
	            if(data.code == '000000') {
	                location.href = 'index.html'
	            }
	        }
	    });
	});
	
	return UserInfo;
});
