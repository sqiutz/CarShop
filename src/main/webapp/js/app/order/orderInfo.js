define([], function() {
	var OrderInfo = {
		// 获取服务队列
		getServeQueues : function(options) {
			var serveList;
			$.common.ajax("getServeQueues", {
				data : options.data,
				success : function(data) {
					serveList = data.resList;
					options.success(serveList);
				},
				error : function(error) {
					options.success();
				}
			});
		},
		// 获取单个服务单
		getServeQueue : function(options) {
			$.common.ajax("getServeQueue", {
				data : options.data,
				success : function(data) {
					options.success(data);
				}
			});
		},
		// call
		call : function(options) {
			$.common.ajax("call", {
				success : function(data) {
					options.success(data);
				}
			});
		},
		// hold
        hold : function(options) {
            $.common.ajax("hold", {
                data : options.data,
                success : function(data) {
                    options.success(data);
                }
            });
        },
        // resume
        resume : function(options) {
            $.common.ajax("resume", {
                data : options.data,
                success : function(data) {
                    options.success(data);
                }
            });
        },
        // send
        send : function(options) {
            $.common.ajax("send", {
                data : options.data,
                success : function(data) {
                    options.success(data);
                }
            });
        },
		// 获取订单列表
		getOrderList : function(options) {
			var orderlist;
			$.common.ajax("getOrderList", {
				data : options.data,
				success : function(data) {
					orderlist = data.resList;
					options.success(orderlist);
				},
				error : function(error) {
					options.success();
				}
			});
		}
	};

	$.OrderInfo = OrderInfo;
	return OrderInfo;
});