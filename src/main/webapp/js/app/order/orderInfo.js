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
        // cancel
        cancel : function(options) {
            $.common.ajax("cancel", {
                data : options.data,
                success : function(data) {
                    options.success(data);
                }
            });
        },  
        getSettleQueues : function(options) {
            var settleList;
            $.common.ajax("getSettleQueues", {
                data : options.data,
                success : function(data) {
                    settleList = data.resList;
                    options.success(settleList);
                },
                error : function(error) {
                    options.success();
                }
            });
        },
        getSettleQueue : function(options) {
            $.common.ajax("getSettleQueue", {
                data : options.data,
                success : function(data) {
                    options.success(data);
                }
            });
        },
        sStart : function(options) {
            $.common.ajax("sStart", {
                data : options.data,
                success : function(data) {
                    options.success(data);
                }
            });
        },
        sFinish : function(options) {
            $.common.ajax("sFinish", {
                data : options.data,
                success : function(data) {
                    options.success(data);
                }
            });
        },
        sCancel : function(options) {
            $.common.ajax("sCancel", {
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
		getBookedOrderList : function(options) {
            var orderlist;
            $.common.ajax("getBookedOrderList", {
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
        getBookedOrderListByDate : function(options) {
            var orderlist;
            $.common.ajax("getBookedOrderListByDate", {
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
        getBookedOrderListByDay : function(options) {
            var orderlist;
            $.common.ajax("getBookedOrderListByDay", {
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
        getBookedOrderListByWeek : function(options) {
            var orderlist;
            $.common.ajax("getBookedOrderListByWeek", {
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
        getBookedOrderListByMonth : function(options) {
            var orderlist;
            $.common.ajax("getBookedOrderListByMonth", {
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
        // 获取单个订单
        getOrder : function(options) {
            $.common.ajax("getOrder", {
            	data : options.data,
                success : function(data) {
                    options.success(data);
                }
            });
        },
        // 下订单
        startOrder : function(options) {
            $.common.ajax("startOrder", {
            	type :"POST",
            	data : options.data,
                success : function(data) {
                    options.success(data);
                }
            });
        },
        printOrder : function(options) {
            $.common.ajax("printOrder", {
                type :"POST",
                data : options.data,
                success : function(data) {
                    options.success(data);
                }
            });
        },
        book : function(options) {
            $.common.ajax("book", {
                type :"POST",
                data : options.data,
                success : function(data) {
                    options.success(data);
                }
            });
        },
        update : function(options) {
            $.common.ajax("update", {
                type :"POST",
                data : options.data,
                success : function(data) {
                    options.success(data);
                }
            });
        },
        cancelBooking : function(options) {
            $.common.ajax("cancelBooking", {
                type :"POST",
                data : options.data,
                success : function(data) {
                    options.success(data);
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
        getIssueQueue : function(options) {
            $.common.ajax("getIssueQueue", {
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
        mReject : function(options) {
            $.common.ajax("mReject", {
                data : options.data,
                success : function(data) {
                    options.success(data);
                }
            });
        },
        mPreapprove : function(options) {
            $.common.ajax("mPreapprove", {
                data : options.data,
                success : function(data) {
                    options.success(data);
                }
            });
        },
        iStart : function(options) {
            $.common.ajax("iStart", {
                data : options.data,
                success : function(data) {
                    options.success(data);
                }
            });
        },
        iHold : function(options) {
            $.common.ajax("iHold", {
                data : options.data,
                success : function(data) {
                    options.success(data);
                }
            });
        },
        iFinish : function(options) {
            $.common.ajax("iFinish", {
                data : options.data,
                success : function(data) {
                    options.success(data);
                }
            });
        },
        //分配
        allocate : function(options) {
            $.common.ajax("allocate", {
                data : options.data,
                success : function(data) {
                    options.success(data);
                }
            });
        },
        iAllocate : function(options) {
            $.common.ajax("iAllocate", {
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
        // 获取工作调度
        getTodayModifyQueue : function(options) {
            var queues;
            $.common.ajax("getTodayModifyQueue", {
                success : function(data) {
                    queues = data.resList;
                    options.success(queues);
                },
                error : function(error) {
                    options.success();
                }
            });
        },
        // 获取工作调度列表
        getModifyQueues : function(options) {
            var queues;
            $.common.ajax("getModifyQueues", {
                data : options.data,
                success : function(data) {
                    queues = data.resList;
                    options.success(queues);
                },
                error : function(error) {
                    options.success();
                }
            });
        },
        // 获取Job Type列表
        getJobTypes : function(options) {
            var jobTypes;
            $.common.ajax("getJobTypes", {
                success : function(data) {
                    jobTypes = data.resList
                    options.success(jobTypes);
                },
                error : function(error) {
                    options.success();
                }
            });
        },
        // 添加Job Type
        addJobType : function(options) {
            $.common.ajax("addJobType", {
                type:"POST",
                data:options.data,
                success:function(data){                 
                    options.success(data);                  
                }
            });
        },
        // 修改Job Type
        modifyJobType : function(options) {
            $.common.ajax("modifyJobType", {
                type:"POST",
                data:options.data,
                success:function(data){                 
                    options.success(data);                  
                }
            });
        },
        // 删除Job Type
        deleteJobType : function(options) {
            $.common.ajax("deleteJobType", {
                type:"POST",
                data:options.data,
                success:function(data){                 
                    options.success(data);                  
                }
            });
        },
        // Workload
        getAllWorkload : function(options) {
            var workload;
            $.common.ajax("getAllWorkload", {
                data:options.data,
                success : function(data) {
                    workload = data.resList
                    options.success(workload);
                },
                error : function(error) {
                    options.success();
                }
            });
        },
        getUserWorkload : function(options) {
            var workload;
            $.common.ajax("getUserWorkload", {
                data:options.data,
                success : function(data) {
                    workload = data.resList
                    options.success(workload);
                },
                error : function(error) {
                    options.success();
                }
            });
        },
        getReport : function(options) {
            var report;
            $.common.ajax("getReport", {
                data:options.data,
                success : function(data) {
                    report = data.resList
                    options.success(report);
                },
                error : function(error) {
                    options.success();
                }
            });
        },
        getBookedOrdersByDate : function(options) {
            var list;
            $.common.ajax("getBookedOrdersByDate", {
                data:options.data,
                success : function(data) {
                    list = data.resList
                    options.success(list);
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