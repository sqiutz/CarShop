(function($) {
    // 账号 密码
    var userName=$('#username'), password=$('#password');
    // 公用的验证方法
    var user = {
        // 验证登录名称
        validateName : function() {
        	var name = userName.val();
        	var flag;
        	if(name.length == 0){
				$('#userNameErrMsg').html('Please input the username!').show();
				userName.css('border', '1px solid #F00');
				flag = false;
			}
        	else {
        		$('#userNameErrMsg').html('').hide();
				userName.css('border', '1px solid #CCC');
				flag = true;
        	}
            return flag;
        },
        // 验证登录密码
        validatePass : function() {
        	var pwd = password.val();
        	var flag;
        	if(pwd.length == 0){
				$('#pwdErrMsg').html('Please input the password!').show();
				password.css('border', '1px solid #F00');
				flag = false;
			}
        	else {
        		$('#pwdErrMsg').html('').hide();
				password.css('border', '1px solid #CCC');
				flag = true;
        	}
            return flag;
        }
    };
    // 登录事件
    $("#loginBtn").bind("click",function(){
    	var valName = user.validateName();
		var valPass = user.validatePass();
		if(valName && valPass) {
			$.UserInfo.login({
	            data : {
	            	username : userName.val(),
	                passwd : password.val()
	            },
	            success : function(data) {
	                if(data.code == '000000') {
	                	$('#errMsg').html('').hide();
	                    location.href = 'mechanic_update.html';
	                }else if(data.code == '010102'){
	                	$('#errMsg').html('The password is not correct!').show();
	                }
	            }
	        });
		}        
    });   
})(jQuery);
