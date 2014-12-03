(function($) {
    var orgPassword = $('#orgPassword'), newPassword = $('#newPassword'), newPasswordConf = $('#newPasswordConf'),
        username = $('#username'), adminPassword = $('#adminPassword');
    var user = {
        // 验证登录密码
        validateOrgPass : function() {
            var pwd = orgPassword.val();
            var flag;
            if (pwd.length == 0) {
                $('#orgPwdErrMsg').html('Please input the original password!').show('normal');
                newPassword.css('border', '1px solid #F00');
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
                $('#newPwdErrMsg').html('Please input the new password!').show('normal');
                newPassword.css('border', '1px solid #F00');
                flag = false;
            } else if (pwd.length < 6 || pwd.length > 22) {
                $('#newPwdErrMsg').html('The length of the new password should be between 6 to 22!').show('normal');
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
                $('#newPwdConfErrMsg').html('Please confirm the new password!').show('normal');
                newPasswordConf.css('border', '1px solid #F00');
                flag = false;
            } else if (pwd != newPassword.val()) {
                $('#newPwdConfErrMsg').html('The two passwords are not consistent!').show('normal');
                newPasswordConf.css('border', '1px solid #F00');
                flag = false;
            } else {
                $('#newPwdConfErrMsg').html('').hide('normal');
                newPasswordConf.css('border', '1px solid #CCC');
                flag = true;
            }
            return flag;
        },
        //修改密码
        changePassword : function() {
            var valOrgPass = user.validateOrgPass();
            var valPass = user.validatePass();
            var valPassConf = user.validatePassConf();
            if(valOrgPass && valPass && valPassConf) {
                $.UserInfo.checkLogin({
                    success : function(data) {
                        if(data.code == '000000') {
                            var userProfile = data.obj;
                            if(userProfile) {
                                if(userProfile.passwd !== orgPassword.val()) {
                                    $('#errMsg').attr('class', 'errMsg')
                                    .html('The original password is not correct!').show('normal');
                                    return;
                                }
                                $.UserInfo.modifyUser({
                                    data : {
                                        id : userProfile.id,
                                        groupId : userProfile.groupId,
                                        userName : userProfile.userName,
                                        passwd : newPassword.val(),
                                        isAdmin : userProfile.isAdmin,
                                        isValid : 1
                                    },
                                    success : function(data) {
                                        if(data.code == '000000') {
                                            $('#errMsg').attr('class', 'succMsg')
                                                .html('The new password has been saved.').show('normal');
                                            orgPassword.val('');
                                            newPassword.val('');
                                            newPasswordConf.val('');
                                        }
                                    }
                                });
                            }                             
                        }
                    }
                });
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
                $("#helloUserName").text('Hello ' + (userProfile ? userProfile.userName : ''));  
                if(userProfile.isAdmin) {
                    $('#orgPwdDiv').hide();
                    $('#adminPwdDiv').show();
                    $('#usernameDiv').show();
                }
            }
        }
    });

})(jQuery);