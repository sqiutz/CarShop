(function($) {   
	$('#parameterDiv').hide();
	$('#suspendDiv').hide();
	
	var params;
	
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
            params = [
            {
                name : 'LANGUAGE',
                display : LANGUAGE
            },
            {
                name : 'COUNTER_NUM',
                display : COUNTER_NUMBER
            },
            {
                name : 'AVG_WAITING_TIME',
                display : AVG_WAITING_TIME
            },
            {
                name : 'WAITING_TIME_BUFFER',
                display : WAITING_TIME_BUFFER
            }];
            
            $('#changePwd').text(CHANGE_PASSW0RD);
            $('#logout').text(LOGOUT);
            
            $('#groupCol').text(GROUP);
            $('#usernameCol').text(USERNAME);
            $('#counterCol').text(COUNTER_NO);
            $('#isAdminCol').text(IS_ADMIN);
            $('#isActiveCol').text(IS_ACTIVE);

            $('#regNoCol').text(REG_NO);
            $('#queNoCol').text(QUE_NO);
            $('#saCol').text(SA);
            $('#startTimeCol').text(START_TIME);
            $('#delayTimeCol').text(DELAY_TIME);
            $('#endTimeCol').text(END_TIME);
        });
    }
    
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
			var td = $('<td></td>').appendTo(tr);
			if(u.counter) {
			    $('<a></a>').text(u.counter).attr('id', 'uId_' + u.id).attr('class', 'toggle')
                .attr('title', RELEASE_COUNTER).appendTo(td)
                .bind('click', function() {
                    var id = this.id.split('_')[1];
                    $.UserInfo.disableCounter({
                        data : {
                            id : id
                        },
                        success : function(data) {
                            if(data.code == '000000') {
                                user.getAllUsers();
                            }                            
                        }
                    });
                });
			}			
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
	
	$('#accountLink').bind('click', function() {
		if('unselected' === $('#accountTab').attr('class')) {			
			$('#parameterTab').attr('class', 'unselected');
			$('#suspendTab').attr('class', 'unselected');
			$('#accountTab').attr('class', '');	
			$('#parameterDiv').hide();
			$('#suspendDiv').hide();
			$('#accountDiv').show();			
		}		
	});

	$('#parameterLink').bind('click', function() {
		if('unselected' === $('#parameterTab').attr('class')) {
			$('#accountTab').attr('class', 'unselected');
			$('#suspendTab').attr('class', 'unselected');
			$('#parameterTab').attr('class', '');			
			$('#accountDiv').hide();
			$('#suspendDiv').hide();
			$('#parameterDiv').show();
			createParamsList();
		}		
	});
	
	$('#suspendLink').bind('click', function() {
        if('unselected' === $('#suspendTab').attr('class')) { 
            $('#suspendTab').attr('class', '');
            $('#parameterTab').attr('class', 'unselected');            
            $('#accountTab').attr('class', 'unselected'); 
            $('#suspendDiv').show();
            $('#parameterDiv').hide();            
            $('#accountDiv').hide();  
            getSuspendList();
        }       
    });
	
	var createParamsList = function() {
	    $('#parameterDiv div').remove();
	    for(var i = 0; i < params.length; i++) {
	        var param = params[i];
	        var div = $('<div></div>').attr('style', 'margin-bottom:8px').appendTo($('#parameterDiv'));
	        $('<label></label>').attr('style', 'width:200px').text(param.display).appendTo(div);
	        $('<input></input>').attr('type', 'text').attr('id', param.name).appendTo(div)
	            .keyup(function() {
	                var id = $(this).attr('id');
	                $('#' + id + '_btn').show('normal');
	            });
	        $('<button></button>').text(SAVE).attr('title', SAVE)
	            .attr('id', param.name + '_btn').attr('name', param.name).attr('class', 'blue-button')
	            .attr('style', 'width:71px;height:27px;vertical-align:middle;margin-top:-2px;margin-left:20px;display:none')
	            .appendTo(div)
	            .bind('click', function() {
	                var name = $(this).attr('name');
	                var val = $('#' + name).val();
	                var that = this;
	                $.UserInfo.modifyProperty({
	                    data : {
	                        name : name,
	                        value : val
	                    },
	                    success : function(data) {
	                        if (data.code == '000000') {
	                            $(that).hide('normal');
	                        }
	                    }
	                });
	            });
	        
	        $.UserInfo.getProperty({
	            data : {
	                name : param.name
	            },
	            success : function(data) {
	                if (data.code == '000000') {
	                    var name = data.obj.name;
	                    var val = data.obj.value;
	                    $('#' + name).val(val);
	                }
	            }
	        });
	    }
	};
	
	var getSuspendList = function() {
	    $.OrderInfo.getServeQueues({
            data : {
                step : 1
            },
            success : function(serves) {
                $('#suspendList tr.odd').remove();
                $('#suspendList tr.even').remove();
                var num = serves && serves.length > 0 ? serves.length : 5;
                for (var i = 0; i < num; i++) {
                    var serve = serves && i < serves.length ? serves[i] : null;
                    var tr = $('<tr></tr>').attr('class', i % 2 === 0 ? 'odd' : 'even')
                            .appendTo($('#suspendList'));
                    $('<td></td>').text(serve && serve.order ? serve.order.registerNum : '').appendTo(tr);
                    $('<td></td>').text(serve && serve.order ? serve.order.queueNum : '').appendTo(tr);
                    $('<td></td>').text(serve && serve.user ? serve.user.userName : '').appendTo(tr);
                    $('<td></td>').text(serve && serve.order ? getTimeStr(serve.order.delayTime) : '').appendTo(tr);
                    $('<td></td>').text(serve && serve.order ? getTimeStr(serve.order.startTime) : '').appendTo(tr);
                    $('<td></td>').text(serve && serve.order ? getTimeStr(serve.order.endTime) : '').appendTo(tr);
                    var td = $('<td></td>').appendTo(tr);
                    if(serve) {
                        $('<a></a>').text(CANCEL).attr('id', 'serveId_' + serve.id).attr('class', 'toggle')
                        .appendTo(td)
                        .bind('click', function() {
                            var id = this.id.split('_')[1];
                            $.OrderInfo.cancel({
                                data : {
                                    id : id
                                },
                                success : function(data) {
                                    if (data.code == '000000') {
                                        getSuspendList();
                                    }
                                }
                            });
                        }); 
                    }                                       
                }
            }
        });
	}
})(jQuery);