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
            $('#logout').text(LOGOUT).attr('title', LOGOUT);
            $('#title').text(SUZUKI_SIAGA_LEBIH_MENGERTI_KELUARGA);
            $('#usernameLabel').text(USERNAME);
            $('#orgPasswordLabel').text(ORG_PASSWORD);
            $('#newPasswordLabel').text(NEW_PASSWORD);
            $('#saveBtn').text(SAVE).attr('title', SAVE);
            $('#cancelBtn').text(Cancel).attr('title', Cancel);
            $('#newPasswordConfLabel').text(PASSWORD_CONF);
            $('#legend').text(CHANGE_PASSW0RD);            
        });
    }
    
    var orgPassword = $('#orgPassword'), newPassword = $('#newPassword'), newPasswordConf = $('#newPasswordConf'),
        username = $('#username');
    var user = {
        // 验证登录密码
        validateOrgPass : function() {
            var pwd = orgPassword.val();
            var flag;
            if (pwd.length == 0) {
                $('#orgPwdErrMsg').html(MSG_INPUT_ORIGINAL_PASSWORD).show('normal');
                orgPassword.css('border', '1px solid #F00');
                flag = false;
            } else {
                $('#orgPwdErrMsg').html('').hide('normal');
                orgPassword.css('border', '1px solid #CCC');
                flag = true;
            }
            return flag;
        },
        validatePass : function() {
            var pwd = newPassword.val();
            var flag;
            if (pwd.length == 0) {
                $('#newPwdErrMsg').html(MSG_INPUT_NEW_PASSWORD).show('normal');
                newPassword.css('border', '1px solid #F00');
                flag = false;
            } else if (pwd.length < 6 || pwd.length > 22) {
                $('#newPwdErrMsg').html(MSG_LENGTH_OF_NEW_PASSWORD).show('normal');
                newPassword.css('border', '1px solid #F00');
                flag = false;
            } else {
                $('#newPwdErrMsg').html('').hide('normal');
                newPassword.css('border', '1px solid #CCC');
                flag = true;
            }
            return flag;
        },
        validatePassConf : function() {
            var pwd = newPasswordConf.val();
            var flag;
            if (pwd.length == 0) {
                $('#newPwdConfErrMsg').html(MSG_CONFIRM_NEW_PASSWORD).show('normal');
                newPasswordConf.css('border', '1px solid #F00');
                flag = false;
            } else if (pwd != newPassword.val()) {
                $('#newPwdConfErrMsg').html(MSG_PASSWORD_NOT_CONSISTENT).show('normal');
                newPasswordConf.css('border', '1px solid #F00');
                flag = false;
            } else {
                $('#newPwdConfErrMsg').html('').hide('normal');
                newPasswordConf.css('border', '1px solid #CCC');
                flag = true;
            }
            return flag;
        },
        validateUsername : function() {
            var flag = undefined !== username.val() && null !== username.val() && '' !== username.val();
            if(!flag) {
                $('#usernameErrMsg').html(MSG_SELECT_USERNAME).show('normal');
                username.css('border', '1px solid #F00');
                flag = false;
            }
            return flag;
        },
        //修改密码
        changePassword : function() {
            var valOrgPass = userProfile.isAdmin ? true : user.validateOrgPass();
            var valPass = user.validatePass();
            var valPassConf = user.validatePassConf();
            var valUsername = userProfile.isAdmin ? user.validateUsername() : true;
            if(valOrgPass && valPass && valPassConf && valUsername) {
                if(userProfile.isAdmin) {
                    modifyUser(users[username.val()]);
                }
                else {
                    $.UserInfo.checkLogin({
                        success : function(data) {
                            if(data.code == '000000') {
                                var u = data.obj;
                                if(u) {
                                    if(u.passwd !== orgPassword.val()) {
                                        $('#errMsg').attr('class', 'errMsg')
                                        .html(MSG_ORIGINAL_PASSWORD_NOT_CORRECT).show('normal');
                                        return;
                                    }
                                    modifyUser(u);
                                }                             
                            }
                        }
                    });
                }                
                
                function modifyUser(u) {
                    $.UserInfo.modifyUser({
                        data : {
                            id : u.id,
                            groupId : u.groupId,
                            userName : u.userName,
                            passwd : newPassword.val(),
                            isAdmin : u.isAdmin,
                            isValid : 1,
                            isBooker : u.isBooker
                        },
                        success : function(data) {
                            if(data.code == '000000') {
                                $('#errMsg').attr('class', 'succMsg')
                                    .html(MSG_NEW_PASSWORD_SAVED).show('normal');
                                orgPassword.val('');
                                newPassword.val('');
                                newPasswordConf.val('');
                            }
                        }
                    });
                }
            }
        }
    };
    
    //密码失去焦点进行验证
    orgPassword.bind("blur", function(){
        user.validateOrgPass();
    });
    orgPassword.bind("focus",function(){
        $('#errMsg').html('').hide('normal');
        $('#orgPwdErrMsg').html('').hide('normal');
        orgPassword.css('border', '1px solid #CCC');        
    });
    newPassword.bind("blur", function(){
        user.validatePass();
    });
    newPassword.bind("focus",function(){
        $('#errMsg').html('').hide('normal');
        $('#newPwdErrMsg').html('').hide('normal');
        newPassword.css('border', '1px solid #CCC');        
    });
    newPasswordConf.bind("blur", function(){
        user.validatePassConf();
    });
    newPasswordConf.bind("focus",function(){
        $('#errConfMsg').html('').hide('normal');
        $('#newPwdConfErrMsg').html('').hide('normal');
        newPasswordConf.css('border', '1px solid #CCC');        
    });
    
    //修改密码
    $("#saveBtn").bind("click",function() {
        user.changePassword();
    });
    
    $("#cancelBtn").bind("click",function() {
        history.go(-1);
    });
    
    var userProfile;
    $.UserInfo.checkLogin({
        success : function(data) {
            if(data.code == '000000') {
                userProfile = data.obj;
                $("#helloUserName").text(HELLO + ' ' + (userProfile ? userProfile.userName : ''));  
                if(userProfile.isAdmin) {
                    $('#orgPwdDiv').hide();
                    $('#usernameDiv').show();
                }
            }
        }
    });
    
    var users;
    $.UserInfo.getAllUsers({
        success : function(data) {
            users = data.resList;
            for(var i = 0; i < users.length; i++) {
                var u = users[i];
                $('#username').append("<option value ='" + i + "'>"
                        + u.userName + "</option>");
            }
        }
    });

})(jQuery);