(function($){
	//获取用户组列表
	$.UserInfo.getAllGroups({
		success : function(data) {
			var selection = $('#group');
			var groups = data.resList;
			for(var i = 0; i < groups.length; i++) {
				var g = groups[i];
				$("<option value ='"+g.groupName+">"+g.groupName+"</option>")
					.appendTo(g);
			}
		}
	});
	//获取用户列表
	$.UserInfo.getAllUsers({
		success : function(data) {
			var table = $('#usersTbl');
			var users = data.resList;
			for(var i = 0; i < users.length; i++) {
				var u = users[i];
				var tr = $("<tr></tr>")
					.attr('class', i % 2 === 0 ? 'odd' : 'even')
					.appendTo(table);
				$("<td>"+ u.groupId+"</td>").appendTo(tr);
				$("<td>"+ u.userName+"</td>").appendTo(tr);
				$("<td>"+ (u.isAdmin?'Yes':'No')+"</td>").appendTo(tr);
				$("<td>"+ (u.isValid?'Yes':'No')+"</td>").appendTo(tr);
			}
		}
	});
})(jQuery);