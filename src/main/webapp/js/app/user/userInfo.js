define(["md5"],function(){
	var UserInfo = {
		boolTime:0,
		//用户系统登录方法
		login : function(options) {
			var that=this;
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
		//检测用户是否已经登录
		checkLogin:function(){

		},
		//获取用户信息
		getUserInfo:function(options){
			
		},
	};

	$.UserInfo = UserInfo;
	return UserInfo;
});
