define([], function() {
	var OrderInfo = {
		//获取服务队列
		getServeQueues : function(options) {
			$.common.ajax("getServeQueues", {
				type : "POST",
				data : options.data,
				success : function(data) {
					options.success(data);
				},
				complete : function(data){                 
                    options.complete(data);                  
                }
			});
		},
		//获取订单列表
		getOrderList : function(options) {
			$.common.ajax("getOrderList", {
				type : "POST",
				data : options.data,
				success : function(data) {
					options.success(data);
				},
				complete : function(data){                 
                    options.complete(data);                  
                }
			});
		}
	};

	$.OrderInfo = OrderInfo;
	return OrderInfo;
});