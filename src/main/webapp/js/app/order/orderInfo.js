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
		},
        // 获取单个modify queue
		getModifyQueue : function(options) {
            $.common.ajax("getModifyQueue", {
                data : options.data,
                success : function(data) {
                    options.success(data);
                }
            });
        },
        // Start, Hold and Finish in mechanic update
        mStart : function(options) {
            $.common.ajax("mStart", {
                data : options.data,
                success : function(data) {
                    options.success(data);
                }
            });
        },
        mHold : function(options) {
            $.common.ajax("mHold", {
                data : options.data,
                success : function(data) {
                    options.success(data);
                }
            });
        },
        mFinish : function(options) {
            $.common.ajax("mFinish", {
                data : options.data,
                success : function(data) {
                    options.success(data);
                }
            });
        },
        // 获取洗车列表
        getCarWashQueues : function(options) {
            var carWashlist;
            $.common.ajax("getCarWashQueues", {
                data : options.data,
                success : function(data) {
                    carWashlist = data.resList;
                    options.success(carWashlist);
                },
                error : function(error) {
                    options.success();
                }
            });
        },
        // Start, Stop and Cancel in car wash
        cStart : function(options) {
            $.common.ajax("cStart", {
                data : options.data,
                success : function(data) {
                    options.success(data);
                }
            });
        },
        cFinish : function(options) {
            $.common.ajax("cFinish", {
                data : options.data,
                success : function(data) {
                    options.success(data);
                }
            });
        },
        cCancel : function(options) {
            $.common.ajax("cCancel", {
                data : options.data,
                success : function(data) {
                    options.success(data);
                }
            });
        },
	};

	$.OrderInfo = OrderInfo;
	return OrderInfo;
});