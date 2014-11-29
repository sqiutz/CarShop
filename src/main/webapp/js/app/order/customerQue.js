(function($) {
	var serve = {
		//获取服务队列
		getServeQueues : function() {
			var that = this;
			$.OrderInfo.getServeQueues({
				data : {
	            	step : 0
	            },
	            success : function(data) {
					that._serves = data.resList;
					/*for (var i = 0; i < that._groups.length; i++) {
						var g = that._groups[i];
						if('0' === g.groupName) {
						    continue;
						}
						$('#group').append(
								"<option value ='" + g.id + "'>"
								+ that.groupNameMapper[g.groupName] + "</option>");
					}*/
				}
			});
		}
	};
	
	serve.getServeQueues();
})(jQuery);