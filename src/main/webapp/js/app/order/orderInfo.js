define([], function() {
	var OrderInfo = {
		// 获取服务队列
		getServeQueues : function(options) {
			var serveList;
			$.common.ajax("getServeQueues", {
				type : "POST",
				data : options.data,
				success : function(data) {
					serveList = data.resList;
					options.success(serveList);
				}
			});
		},
		// 获取订单列表
		getOrderList : function(options) {
			var orderlist;
			$.common.ajax("getOrderList", {
				type : "POST",
				data : options.data,
				success : function(data) {
					orderlist = data.resList;
					options.success(orderlist);
				}
			});
		}
	};

	$.OrderInfo = OrderInfo;
	return OrderInfo;
});