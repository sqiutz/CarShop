(function($) {
	var userGroup = {
		groupNameMapper : {
			0 : 'Admin',
			2 : 'Manager',
			3 : 'Worker'
		},
		// 获取用户组列表
		getAllGroups : function() {
			var that = this;
			$.UserInfo.getAllGroups({
				success : function(data) {
					that._groups = data.resList;
					for (var i = 0; i < that._groups.length; i++) {
						var g = that._groups[i];
						$('#group').append(
								"<option value ='" + g.groupName + "'>"
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
			var group = userGroup;
			$.UserInfo.getAllUsers({
				success : function(data) {
					var table = $('#usersTbl');
					var users = data.resList;
					for (var i = 0; i < users.length; i++) {
						var u = users[i];
						var tr = $("<tr></tr>").attr('class',
								i % 2 === 0 ? 'odd' : 'even').appendTo(table);
						$("<td>" + group.getGroupNameById(u.groupId) + "</td>").appendTo(tr);
						$("<td>" + u.userName + "</td>").appendTo(tr);
						$("<td>" + (u.isAdmin ? 'Yes' : 'No') + "</td>")
								.appendTo(tr);
						$("<td>" + (u.isValid ? 'Yes' : 'No') + "</td>")
								.appendTo(tr);
					}
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
        	else {
        		$('#pwdErrMsg').html('').hide('normal');
				password.css('border', '1px solid #CCC');
				flag = true;
        	}
            return flag;
        },
		// 添加用户
		addUser : function() {
			
		}
	};
	
	//账号 密码
	var userName=$("#username"),userPass=$("#password");
	
	//用户名失去焦点进行验证
    userName.bind("blur", function() {
        user.validateName();
    });
    userName.bind("focus", function() {
        $('#errMsg').html('').hide('normal');
        $('#userNameErrMsg').html('').hide('normal');
        userName.css('border', '1px solid #CCC');
    });

	userGroup.getAllGroups();

})(jQuery);