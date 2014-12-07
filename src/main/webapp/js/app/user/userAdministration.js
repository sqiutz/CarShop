(function($) {   
	var userGroup = {
		groupNameMapper : {
			0 : 'Admin',
			1 : 'Booker',
			2 : 'Servicer'
		},
		// 获取用户组列表
		getAllGroups : function() {
			var that = this;
			$.UserInfo.getAllGroups({
				success : function(data) {
					that._groups = data.resList;
					for (var i = 0; i < that._groups.length; i++) {
						var g = that._groups[i];
						if('0' === g.groupName) {
						    continue;
						}
						$('#group').append(
								"<option value ='" + g.id + "'>"
								+ that.groupNameMapper[g.groupName] + "</option>");
						$('#groupBy').append(
								"<option value ='" + g.id + "'>"
								+ that.groupNameMapper[g.groupName] + "</option>");
					}
				},
				complete : user.getAllUsers
			});
		},
		// 通过groupId获取groupName
		getGroupNameById : function(id) {
			var groupName = '';
			for (var i = 0; i < this._groups.length; i++) {
				var g = this._groups[i];
				if(g.id === id) {
					groupName = this.groupNameMapper[g.groupName];
					break;
				}
			}
			return groupName;
		}
	};

	var user = {
		// 获取用户列表
		getAllUsers : function() {
			$.UserInfo.getAllUsers({
				success : function(data) {
					user._users = data.resList;	
					showUserList($('#groupBy').val())
				}
			});
		},
		// 验证登录名称
        validateName : function() {
        	var name = userName.val();
        	var pattern = /^[a-zA-Z0-9_-]/;    
			var flag;
			if(name.length == 0){
				$('#userNameErrMsg').html('Please input the username!').show('normal');
				userName.css('border', '1px solid #F00');
				flag = false;
			}
			else if(!pattern.test(name)) {
				$('#userNameErrMsg').html('The username can only contain letters, numbers, dash and hyphen!').show('normal');
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
        	else if(pwd.length < 6 || pwd.length > 22) {
        	    $('#pwdErrMsg').html('The length of the password should be between 6 to 22!').show('normal');
                password.css('border', '1px solid #F00');
                flag = false;
        	}
        	else {
        		$('#pwdErrMsg').html('').hide('normal');
				password.css('border', '1px solid #CCC');
				flag = true;
        	}
            return flag;
        },
        validatePassConf : function() {
            var pwd = passwordConf.val();
            var flag;
            if(pwd.length == 0){
                $('#pwdConfErrMsg').html('Please confirm the password!').show('normal');
                passwordConf.css('border', '1px solid #F00');
                flag = false;
            }
            else if(pwd != password.val()) {
                $('#pwdConfErrMsg').html('The two passwords are not consistent!').show('normal');
                passwordConf.css('border', '1px solid #F00');
                flag = false;
            }
            else {
                $('#pwdConfErrMsg').html('').hide('normal');
                passwordConf.css('border', '1px solid #CCC');
                flag = true;
            }
            return flag;
        },
		// 添加用户
		addUser : function() {
		    var valName = user.validateName();
	        var valPass = user.validatePass();
	        var valPassConf = user.validatePassConf();
	        if(valName && valPass && valPassConf) {
	        	var that = this;
	            $.UserInfo.addUser({
	                data : {
	                    groupId : group.val(),
	                    userName : userName.val(),
	                    passwd : password.val()
	                },
	                success : function(data) {
	                    if(data.code == '000000') {
	                        $('#errMsg').attr('class', 'succMsg')
                                .html('The user has been saved.').show('normal');
	                        that.getAllUsers();                     
	                    }else if(data.code == '010301'){
	                        $('#errMsg').attr('class', 'errMsg')
	                            .html('The username has been registed!').show('normal');
	                    }
	                }
	            });
	        }
		}
	};
	
	function showUserList(groupId) {
		$('#usersTbl tr.odd').remove();
		$('#usersTbl tr.even').remove();
		var table = $('#usersTbl');
		for (var i = 0, j = 0; i < user._users.length; i++) {
			var u = user._users[i];
			if(u.groupId !== parseInt(groupId)) {
				continue;
			}
			var tr = $("<tr></tr>").attr('class',
					j++ % 2 === 0 ? 'odd' : 'even').appendTo(table);
			$("<td>" + userGroup.getGroupNameById(u.groupId) + "</td>").appendTo(tr);
			$("<td>" + u.userName + "</td>").appendTo(tr);
			$("<td>" + (u.isAdmin ? 'Yes' : 'No') + "</td>")
					.appendTo(tr);
			var td = $("<td></td>").appendTo(tr);
			$("<a class='toggle' id='userId_" + i + "'>" + (u.isValid ? 'Yes' : 'No') + "</a>")
					.appendTo(td)
					.bind('click', function(){
					    var index = this.id.split('_')[1];
					    var userSelected = user._users[index];
					    $.UserInfo.modifyUser({
                            data : {
                                id : userSelected.id,
                                groupId : userSelected.groupId,
                                userName : userSelected.userName,
                                passwd : userSelected.passwd,
                                isAdmin : userSelected.isAdmin,
                                isValid : userSelected.isValid ? 0 : 1,
                                counter : userSelected.counter,
                                isBooker : userSelected.isBooker
                            },
                            success : function(data) {
                                if(data.code == '000000') {
                                    userSelected.isValid = userSelected.isValid ? 0 : 1;
                                    $('#userId_'+index).text(
                                            userSelected.isValid ? 'Yes' : 'No');
                                }
                            }
                        });
					});
		}
	}
	
	//账号 密码
	var userName = $("#username"), password = $("#password"), passwordConf = $("#passwordConfirm");
	//用户组
    var group = $("#group");
	
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
    passwordConf.bind("blur", function(){
        user.validatePassConf();
    });
    passwordConf.bind("focus",function(){
        $('#errConfMsg').html('').hide('normal');
        $('#pwdConfErrMsg').html('').hide('normal');
        passwordConf.css('border', '1px solid #CCC');        
    });
    
    $('#groupBy').bind('click', function() {
    	showUserList($('#groupBy').val());
    });
    
    $("#logout").bind("click", function() {
    	
	});
        
	$("#saveBtn").bind("click", function() {
	    user.addUser();
	});	

	$.UserInfo.checkLogin({
	    success : function(data) {
	        if(data.code == '000000') {
	            $("#helloUserName").text('Hello ' + (data.obj ? data.obj.userName : ''));	            
	        }
	    }
	});
	
	userGroup.getAllGroups();
})(jQuery);