(function($) {
    $.UserInfo.getProperty({
        data : {
            name : 'LANGUAGE'
        },
        success : function(data) {
            if (data.code == '000000') {
                var langCode = data.obj.value;
                applyLang(langCode);
            }else {
                applyLang();
            }
        },
        error : function() {
            applyLang();
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
//            $('#appointmentBtn').text(APPOINTMENT).attr('title', APPOINTMENT);
            $('#customerQueTitle').text(CUSTOMER_QUE_STATUS_BOARD);
            $('#customerQueDesc').text(CUSTOMER_QUE_STATUS_BOARD_DESC);
            $('#machanicTitle').html(MECHANIC_UPDATE_JOB_PROGRESS_HOME);
            $('#machanicDesc').text(MECHANIC_UPDATE_JOB_PROGRESS_DESC);
            $('#carWashTitle').text(CAR_WASH_QUE_BOARD);
            $('#carWashDesc').text(CAR_WASH_QUE_BOARD_DESC);
            $('#customerJobTitle').text(CUSTOMER_JOB_PROGRESS_BOARD);
            $('#customerJobDesc').text(CUSTOMER_JOB_PROGRESS_BOARD_DESC);
//            $('#greetCustomerTitle').text(GREET_CUSTOMER);
//            $('#greetCustomerDesc').text(GREET_CUSTOMER_DESC);
            //$('#finalInspecTitle').text(FINAL_INSPECTION);
            //$('#finalInspecDesc').text(FINAL_INSPECTION_DESC);
            $('#cashQueTitle').text(CASH_QUE_CALLING_BOARD);
            $('#cashQueDesc').text(CASH_QUE_CALLING_BOARD_DESC);
            $('#issueTitle').html(ISSUE_PARTS_HOME);
            $('#issueDesc').text(ISSUE_PARTS_DESC);
//            $('#appointmentTitle').text(APPOINTMENT);
//            $('#appointmentDesc').text(APPOINTMENT_DESC);
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
				$('#userNameErrMsg').html(MSG_INPUT_USERNAME).show('normal');
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
				$('#pwdErrMsg').html(MSG_INPUT_PASSWORD).show('normal');
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
                        else if(userProfile.groupName == 1){
                            location.href = 'foreman_job.html';
                        }
                        else if(userProfile.groupName == 2) {
                            location.href = 'select_counter.html';
                        }
                        else if(userProfile.groupName == 4) {
                            location.href = 'cash_que.html';
                        }
                    }else if(data.code == '010102'){
                        $('#errMsg').html(MSG_PASSWORD_NOT_CORRECT).show('normal');
                    }else if(data.code == '010100'){
                        $('#errMsg').html(MSG_USERNAME_NOT_EXISTED).show('normal');
                    }else if(data.code == '010101'){
                        $('#errMsg').html(MSG_INACTIVE_USER).show('normal');
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
    userName.keyup(function(event) {
        var myEvent = event || window.event;
        var keyCode = myEvent.keyCode;
        if(keyCode == 13){ //Enter
            var dom = password.get(0);
            dom.select();
        }
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
    password.keyup(function(event) {
        var myEvent = event || window.event;
        var keyCode = myEvent.keyCode;
        if(keyCode == 13){ //Enter
            login();
        }
    });
    
//    $('#appointmentBtn').bind("click", function(){
//        location.href = 'appointment_index.html';
//    });
})(jQuery);
