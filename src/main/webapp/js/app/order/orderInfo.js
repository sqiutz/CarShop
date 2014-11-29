define([], function() {
	var OrderInfo = {
		//获取服务队列
		getServeQueues : function(options) {
			$.common.ajax("getServeQueues", {
				type : "POST",
				data : options.data,
				success : function(data) {
					options.success(data);
				}
			});
		}
	};

	$.OrderInfo = OrderInfo;
	return OrderInfo;
});