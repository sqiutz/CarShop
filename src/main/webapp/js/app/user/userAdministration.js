(function($) {   
	$('#parameterDiv').hide();
	$('#suspendDiv').hide();
	$('#reportDiv').hide();
	$('#bookedOrderDiv').hide();
	$("#startDate").datepicker({
	    inline: true,
	    showMonthAfterYear: true,
	    changeMonth: true,
	    changeYear: true,
	    buttonImageOnly: true,
	    dateFormat: 'yy-mm-dd',
	});
	$("#endDate").datepicker({
        inline: true,
        showMonthAfterYear: true,
        changeMonth: true,
        changeYear: true,
        buttonImageOnly: true,
        dateFormat: 'yy-mm-dd',
    });
	$("#bStartDate").datepicker({
        inline: true,
        showMonthAfterYear: true,
        changeMonth: true,
        changeYear: true,
        buttonImageOnly: true,
        dateFormat: 'yy-mm-dd',
    });
    $("#bEndDate").datepicker({
        inline: true,
        showMonthAfterYear: true,
        changeMonth: true,
        changeYear: true,
        buttonImageOnly: true,
        dateFormat: 'yy-mm-dd',
    });
	
	var params;	
	
    $.UserInfo.getProperty({
        data : {
            name : 'LANGUAGE'
        },
        success : function(data) {
            if (data.code == '000000') {
                var langCode = data.obj.value;
                applyLang(langCode);
            }
            else {
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
            },
            {
                name : 'BOOKING_GROUP_NO',
                display : BOOKING_GROUP_NO
            },
            {
                name : 'LOAD_PERSON',
                display : LOAD_PERSON
            },
            {
                name : 'LOAD_PERCENTAGE',
                display : LOAD_PERCENTAGE
            }];
            
            $('#changePwd').text(CHANGE_PASSW0RD).attr('title', CHANGE_PASSW0RD);
            $('#logout').text(LOGOUT).attr('title', LOGOUT);
            $('#title').text(SUZUKI_SIAGA_LEBIH_MENGERTI_KELUARGA);
            $('#accountLink').text(ACCOUNT_MANAGEMENT);
            $('#parameterLink').text(SYSTEM_PARAMETER);
            $('#suspendLink').text(SUSPEND_LIST);
            $('#reportLink').text(REPORT);
            $('#bookedOrderLink').text(ORDER_LIST);
            $('#legend').text(ADD_USER);
            $('#parameterListTitle').text(PARAMETER_CONF);
            $('#jobTypeListTitle').text(REGULER_SERVICE_RATE_TIME);
            $('#expJobTypeListTitle').text(EXPRESS_MAINTENANCE_RATE_TIME);
            $('#groupByLabel').text(GROUP + ':');
            $('#groupCol').text(GROUP);
            $('#usernameCol').text(USERNAME);
            $('#counterCol').text(COUNTER_NO);
            $('#isAdminCol').text(IS_ADMIN);
            $('#isActiveCol').text(IS_ACTIVE);
            $('#groupLabel').text(GROUP);
            $('#usernameLabel').text(USERNAME);
            $('#pwdLabel').text(PASSWORD);
            $('#pwdConfLabel').text(PASSWORD_CONF);
            $('#saveBtn').text(SAVE).attr('title', SAVE);
            $('#regNoCol').text(REG_NO);
            $('#queNoCol').text(QUE_NO);
            $('#saCol').text(SA);
            $('#startTimeCol').text(START_TIME);
            $('#delayTimeCol').text(DELAY_TIME);
            $('#endTimeCol').text(END_TIME);
            $('#addBtn').text(ADD).attr('title', ADD);
            $('#expJobAddBtn').text(ADD).attr('title', ADD);
            $('#fromLabel').text(FROM + ':');
            $('#toLabel').text(TO + ':');
            $('#showBtn').text(SHOW).attr('title', SHOW);
            $('#bFromLabel').text(FROM + ':');
            $('#bToLabel').text(TO + ':');
            $('#bShowBtn').text(SHOW).attr('title', SHOW);
            $('#bRegNoCol').text(REG_NO);
            $('#bookedDateCol').text(BOOKED_DATE);
            $('#jobTypeCol').text(JOB_TYPE);
//            $('#contactCol').text(CONTACT);
            
            $.UserInfo.checkLogin({
                success : function(data) {
                    if(data.code == '000000') {
                        $("#helloUserName").text(HELLO + ' ' + (data.obj ? data.obj.userName : ''));                
                    }
                }
            });
        });
    }
    
	var userGroup = {
		groupNameMapper : {
			0 : 'Admin',
			1 : 'Manager',
			2 : 'Servicer',
			3 : 'Technician'
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
				$('#userNameErrMsg').html(MSG_INPUT_USERNAME).show('normal');
				userName.css('border', '1px solid #F00');
				flag = false;
			}
			else if(!pattern.test(name)) {
				$('#userNameErrMsg').html(MSG_USERNAME_FORMAT).show('normal');
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
				$('#pwdErrMsg').html(MSG_PASSWORD_NOT_CORRECT).show('normal');
				password.css('border', '1px solid #F00');
				flag = false;
			}
        	else if(pwd.length < 6 || pwd.length > 22) {
        	    $('#pwdErrMsg').html(MSG_LENGTH_OF_NEW_PASSWORD).show('normal');
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
                $('#pwdConfErrMsg').html(MSG_CONFIRM_NEW_PASSWORD).show('normal');
                passwordConf.css('border', '1px solid #F00');
                flag = false;
            }
            else if(pwd != password.val()) {
                $('#pwdConfErrMsg').html(MSG_PASSWORD_NOT_CONSISTENT).show('normal');
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
                                .html(MSG_USERNAME_SAVED).show('normal');
	                        that.getAllUsers();                     
	                    }else if(data.code == '010301'){
	                        $('#errMsg').attr('class', 'errMsg')
	                            .html(MSG_USERNAME_REGISTED).show('normal');
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
	
	userGroup.getAllGroups();
	
	$('#accountLink').bind('click', function() {
		if('unselected' === $('#accountTab').attr('class')) {			
			$('#parameterTab').attr('class', 'unselected');
			$('#suspendTab').attr('class', 'unselected');
			$('#reportTab').attr('class', 'unselected');
			$('#bookedOrderTab').attr('class', 'unselected');
			$('#accountTab').attr('class', '');	
			$('#parameterDiv').hide();
			$('#bookedOrderDiv').hide();
			$('#suspendDiv').hide();
			$('#reportDiv').hide();
			$('#accountDiv').show();			
		}		
	});

	$('#parameterLink').bind('click', function() {
		if('unselected' === $('#parameterTab').attr('class')) {
			$('#accountTab').attr('class', 'unselected');
			$('#suspendTab').attr('class', 'unselected');
			$('#reportTab').attr('class', 'unselected');
			$('#bookedOrderTab').attr('class', 'unselected');
			$('#parameterTab').attr('class', '');			
			$('#accountDiv').hide();
			$('#suspendDiv').hide();
			$('#reportDiv').hide();
			$('#bookedOrderDiv').hide();
			$('#parameterDiv').show();
			createParamsList();
			getJobTypeList();
		}		
	});
	
	$('#suspendLink').bind('click', function() {
        if('unselected' === $('#suspendTab').attr('class')) { 
            $('#suspendTab').attr('class', '');
            $('#parameterTab').attr('class', 'unselected');            
            $('#accountTab').attr('class', 'unselected'); 
            $('#reportTab').attr('class', 'unselected');
            $('#bookedOrderTab').attr('class', 'unselected');
            $('#suspendDiv').show();
            $('#parameterDiv').hide();            
            $('#accountDiv').hide();  
            $('#reportDiv').hide();
            $('#bookedOrderDiv').hide();
            getSuspendList();
        }       
    });
	
	$('#reportLink').bind('click', function() {
        if('unselected' === $('#reportTab').attr('class')) { 
            $('#suspendTab').attr('class', 'unselected');
            $('#parameterTab').attr('class', 'unselected');            
            $('#accountTab').attr('class', 'unselected'); 
            $('#bookedOrderTab').attr('class', 'unselected');
            $('#reportTab').attr('class', '');
            $('#reportDiv').show();
            $('#suspendDiv').hide();
            $('#parameterDiv').hide();            
            $('#accountDiv').hide(); 
            $('#bookedOrderDiv').hide();
        }       
    });
	
	$('#bookedOrderLink').bind('click', function() {
        if('unselected' === $('#bookedOrderTab').attr('class')) { 
            $('#suspendTab').attr('class', 'unselected');
            $('#parameterTab').attr('class', 'unselected');            
            $('#accountTab').attr('class', 'unselected'); 
            $('#bookedOrderTab').attr('class', '');
            $('#reportTab').attr('class', 'unselected');
            $('#reportDiv').hide();
            $('#suspendDiv').hide();
            $('#parameterDiv').hide();            
            $('#accountDiv').hide(); 
            $('#bookedOrderDiv').show();
        }       
    });
	
	var createParamsList = function() {
	    $('#parameterListDiv div.list').remove();
	    for(var i = 0; i < params.length; i++) {
	        var param = params[i];
	        var div = $('<div></div>').attr('class', 'list')
	            .appendTo($('#parameterListDiv'));
	        $('<label></label>').attr('style', 'width:200px').text(param.display).appendTo(div);
	        $('<input></input>').attr('type', 'text').attr('id', param.name).appendTo(div)
	            .keyup(function() {
	                var id = $(this).attr('id');
	                if($(this).val()) {	                    
	                    $('#' + id + '_btn').attr('disabled', false);
	                }
	                else {
	                    $('#' + id + '_btn').attr('disabled', 'disabled');
	                }
	            });
	        $('<button></button>').text(SAVE).attr('title', SAVE)
	            .attr('id', param.name + '_btn').attr('name', param.name).attr('class', 'blue-button')
	            .attr('style', 'width:71px;height:27px;vertical-align:middle;margin-top:-2px;margin-left:25px;')
	            .attr('disabled', 'disabled').appendTo(div)
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
	                            $(that).attr('disabled', 'disabled')
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
	
	var getJobTypeList = function() {
	    $.OrderInfo.getJobTypes({
	        success : function(jobTypes) {
	            $('#jobTypeListDiv div.list').remove();
	            $('#expJobTypeListDiv div.list').remove();
	            var regJobTyps = [], expJobTyps = [];
                for(var i = 0; i < jobTypes.length; i++) {
                    if(jobTypes[i].name.indexOf(CONST_EXPRESS) < 0) {
                        regJobTyps.push(jobTypes[i]);
                    }
                    else {
                        jobTypes[i].name = jobTypes[i].name.substring(CONST_EXPRESS.length);
                        expJobTyps.push(jobTypes[i]);
                    }
                }
	            createJobTypeList(regJobTyps, $('#jobTypeListDiv'));
	            createJobTypeList(expJobTyps, $('#expJobTypeListDiv'), true);
	        },
	        error : function() {
	            $('#jobTypeListDiv div.list').remove();
	            $('#expJobTypeListDiv div.list').remove();
	        }
	    });
	}
	
	var createJobTypeList = function(jobTypes, parent, isExp) {	    
	    for(var i = 0; i < jobTypes.length; i++) {
	        var jobType = jobTypes[i];
	        var div = $('<div></div>').attr('class', 'list')
                .appendTo(parent);
	        $('<label></label>').attr('style', 'width:200px').attr('id', 'labelId_' + jobType.id)
	            .text(jobType.name).appendTo(div);
	        var sel = $('<select></select>').attr('id', 'selId_' + jobType.id)
	            .attr('style', 'width:77px').appendTo(div)
	            .bind('change', function() {
	                var id = this.id.split('_')[1];
	                $('#btnSaveId_' + id).attr('disabled', false);
	            });
	        var val = 0;
	        for(var j = 1; j < 21; j++) {
	            $('<option></option>').val(j * 0.5).text(j * 0.5).appendTo(sel);
	            if(jobType.value == j * 0.5) {
	                val = j * 0.5;
	            }
	        }
	        sel.val(val);
	        $('<button></button>').text(SAVE).attr('title', SAVE)
                .attr('id', 'btnSaveId_' + jobType.id).attr('class', 'blue-button')
                .attr('style', 'width:71px;height:27px;vertical-align:middle;margin-top:-2px;margin-left:25px;')
                .attr('disabled', 'disabled').appendTo(div)
                .bind('click', function() {
                    var id = this.id.split('_')[1];
                    var name = $('#labelId_' + id).text();
                    if(isExp) {
                        name = CONST_EXPRESS + name
                    }
                    var value = $('#selId_' + id).val();
                    $.OrderInfo.modifyJobType({
                        data : {
                            id : id, 
                            name : name,
                            value : value
                        },
                        success : function(data) {
                            if (data.code == '000000') {
                                getJobTypeList();                               
                            }
                        }
                    });
                });
	        $('<button></button>').text(DELETE).attr('title', DELETE)
                .attr('id', 'btnDelId_' + jobType.id).attr('class', 'blue-button')
                .attr('style', 'width:71px;height:27px;vertical-align:middle;margin-top:-2px;margin-left:4px;')
                .appendTo(div)
                .bind('click', function() {
                    var id = this.id.split('_')[1];
                    $.OrderInfo.deleteJobType({
                        data : {
                            id : id
                        },
                        success : function(data) {
                            if (data.code == '000000') {
                                getJobTypeList();
                            }
                        }
                    });
                });
	    }
	};
	
	$('#addBtn').bind('click', function() {
	    onAddClick.call(this, $('#jobTypeListDiv'), 'reg');
	});
	
	$('#expJobAddBtn').bind('click', function() {
        onAddClick.call(this, $('#expJobTypeListDiv'), 'exp', true);
    });
	
	function onAddClick(parent, service, isExp) {
	    $(this).attr('disabled', 'disabled');
        var div = $('<div></div>').attr('class', 'list').attr('id', 'jobTypeDiv' + service)
            .appendTo(parent);
        $('<input></input>').attr('type', 'text').attr('id', 'jobTypeName' + service).appendTo(div)
            .keyup(function() {
                if($(this).val()) {
                    $('#jobTypeSaveBtn' + service).attr('disabled', false);
                } 
                else {
                    $('#jobTypeSaveBtn' + service).attr('disabled', 'disabled');
                }
            });
        var sel = $('<select></select>').attr('id', 'jobTypeVal' + service)
            .attr('style', 'width:77px;margin-left:48px').appendTo(div);
        for(var j = 1; j < 21; j++) {
            $('<option></option>').val(j * 0.5).text(j * 0.5).appendTo(sel);
        }
        var that = this;
        $('<button></button>').text(SAVE).attr('title', SAVE)
            .attr('id', 'jobTypeSaveBtn' + service).attr('class', 'blue-button')
            .attr('style', 'width:71px;height:27px;vertical-align:middle;margin-top:-2px;margin-left:25px;')
            .attr('disabled', 'disabled').appendTo(div)
            .bind('click', function() {
                var name = $('#jobTypeName' + service).val();
                if(isExp) {
                    name = CONST_EXPRESS + name
                }
                var value = $('#jobTypeVal' + service).val();
                $.OrderInfo.addJobType({
                    data : {
                        name : name,
                        value : value
                    },
                    success : function(data) {
                        if (data.code == '000000') {
                            $(that).attr('disabled', false);
                            $('#jobTypeDiv' + service).remove();
                            getJobTypeList();
                        }
                    }
                });
            });
        $('<button></button>').text(DELETE).attr('title', DELETE)
            .attr('id', 'jobTypeDelBtn'  + service).attr('class', 'blue-button')
            .attr('style', 'width:71px;height:27px;vertical-align:middle;margin-top:-2px;margin-left:4px;')
            .appendTo(div)
            .bind('click', function() {
                $(that).attr('disabled', false);
                $('#jobTypeDiv'  + service).remove();
            });
	}
	
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
                    $('<td></td>').text(serve ? 
                            getMins(serve.delayTime) + "'" + getSecs(serve.delayTime) + "''" : '').appendTo(tr);
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
	};
	
	$('#showBtn').bind('click', function() {
	    var startDate = $('#startDate').datepicker('getDate');
	    var endDate = $('#endDate').datepicker('getDate');
	    if(startDate && endDate && endDate >= startDate) {
	        $.OrderInfo.getReport({
	            data : {
	                startDate : getDateString(startDate),
	                endDate : getDateString(endDate)
	            },
	            success : function(report) {
	                $('#reportListDiv div.list').remove();
	                for(var i = 0; i < report.length; i++) {
	                    var item = report[i];
	                    var div = $('<div></div>').attr('class', 'list').css('width', '680px')
	                        .appendTo($('#reportListDiv'));
	                    $('<label></label>').attr('style', 'width:250px;font-weight:bold').text(item.name).appendTo(div);
	                    $('<label></label>').attr('style', 'width:350px;').text(item.value).appendTo(div);
	                }
	            }
	        });
	    }
	});
	
	$('#bShowBtn').bind('click', function() {
        var startDate = $('#bStartDate').datepicker('getDate');
        var endDate = $('#bEndDate').datepicker('getDate');
        if(startDate && endDate && endDate >= startDate) {
            $.OrderInfo.getBookedOrdersByDate({
                data : {
                    startDate : getDateString(startDate),
                    endDate : getDateString(endDate)
                },
                success : function(orders) {
                    $('#bookedOrderList tr.odd').remove();
                    $('#bookedOrderList tr.even').remove();
                    var num = orders.length;
                    for (var i = 0; i < num; i++) {
                        var order = orders[i];
                        var tr = $('<tr></tr>').attr('class', i % 2 === 0 ? 'odd' : 'even')
                                .appendTo($('#bookedOrderList'));
                        $('<td></td>').text(order.registerNum).appendTo(tr);
                        var bookStartTime = new Date(order.bookStartTime)
                        $('<td></td>').text(getDateString(bookStartTime) + ' ' + getTimeStr(bookStartTime)).appendTo(tr);
                        $('<td></td>').text(jobTypeMapping(order.jobType) + ' - ' + order.jobtypeTime + ' hour(s)').appendTo(tr);                                                          
                    }
                }
            });
        }
    });
	
	function getMins(diff) {
        return Math.floor(diff / 60000);
    }
    
    function getSecs(diff) {
        var mins = getMins(diff);
        return Math.floor((diff - mins * 60000) / 1000);
    }
    
    function getDateString(date) {
        return '' + date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
    }
})(jQuery);