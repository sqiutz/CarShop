(function($) {
    applyLang();
    $.UserInfo.getProperty({
        data : {
            name : 'LANGUAGE'
        },
        success : function(data) {
            if (data.code == '000000') {
                var langCode = data.obj.value;
                applyLang(langCode);
            }
        }
    });
    
    function applyLang(langCode) {
        if(undefined === langCode || null === langCode) {
            langCode = 'en_US';
        }
        loadLang('lang/' + langCode + '.js', function() {
            $('#title').text(SUZUKI_SIAGA_LEBIH_MENGERTI_KELUARGA);
            $('#usernameLabel').text(USERNAME);
            $('#passwordLabel').text(PASSWORD);
            $('#loginBtn').text(LOGIN).attr('title', LOGIN);
            $('#customerQueTitle').text(CUSTOMER_QUE_STATUS_BOARD);
            $('#customerQueDesc').text(CUSTOMER_QUE_STATUS_BOARD_DESC);
            $('#machanicTitle').text(MECHANIC_UPDATE_JOB_PROGRESS);
            $('#machanicDesc').text(MECHANIC_UPDATE_JOB_PROGRESS_DESC);
            $('#carWashTitle').text(CAR_WASH_QUE_BOARD);
            $('#carWashDesc').text(CAR_WASH_QUE_BOARD_DESC);
            $('#customerJobTitle').text(CUSTOMER_JOB_PROGRESS_BOARD);
            $('#customerJobDesc').text(CUSTOMER_JOB_PROGRESS_BOARD_DESC);
        });
    }
    
    // 账号 密码
    var userName=$('#username'), password=$('#password');
    // 公用的验证方法
    var user = {
        // 验证登录名称
        validateName : function() {
        	var name = userName.val();
        	var flag;
        	if(name.length == 0){
				$('#userNameErrMsg').html('Please input the username!').show('normal');
				userName.css('border', '1px solid #F00');
				flag = false;
			}
        	else {
        		$('#userNameErrMsg').html('').hide('normal');
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
				$('#pwdErrMsg').html('Please input the password!').show('normal');
				password.css('border', '1px solid #F00');
				flag = false;
			}
        	else {
        		$('#pwdErrMsg').html('').hide('normal');
				password.css('border', '1px solid #CCC');
				flag = true;
        	}
            return flag;
        }
    };
    // 登录事件
    $("#loginBtn").bind("click", function(){
        login();
    });
    
    function login() {
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
                        $('#errMsg').html('').hide('normal');
                        var userProfile = data.obj;
                        if(userProfile.isAdmin) {
                            location.href = 'administration.html';
                        }
                        else {
                            location.href = 'select_counter.html';
                        }
                    }else if(data.code == '010102'){
                        $('#errMsg').html('The password is not correct!').show('normal');
                    }else if(data.code == '010100'){
                        $('#errMsg').html('The username is not existed!').show('normal');
                    }else if(data.code == '010101'){
                        $('#errMsg').html('This is an inactive user!').show('normal');
                    }
                }
            });
        }
    }
    
    //用户名失去焦点进行验证
    userName.bind("blur", function() {
        user.validateName();
    });
    userName.bind("focus", function() {
        $('#errMsg').html('').hide('normal');
        $('#userNameErrMsg').html('').hide('normal');
        userName.css('border', '1px solid #CCC');
    });
    
    //密码失去焦点进行验证
    password.bind("blur", function(){
        user.validatePass();
    });
    password.bind("focus",function(){
        $('#errMsg').html('').hide('normal');
        $('#pwdErrMsg').html('').hide('normal');
        password.css('border', '1px solid #CCC');        
    });
})(jQuery);
