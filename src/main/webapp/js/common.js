define([ "jquery", "userInfo", "orderInfo"], function() {
    (function($) {
        if (typeof $.common !== "undefined") {
            return false;
        } else {
            var common = {
                api : {
                    // 登录
                    userLogin : {
                        url : "user.do?action=login",
                        args : [ 
                                 "username", // 账号
                                 "passwd" // 密码
                        ]
                    },
                   logout : {
                        url : "user.do?action=logout"
                    },
                    // 检查用户是否已经登录
                    checkLogin : {
                        url : "user.do?action=checklogin"
                    },
                    // 获取用户对应的柜台
                    checkCounter : {
                    	 url : "user.do?action=checkcounter",
                         args : [ 
                                  "counter"
                         ]
                    },
                    // 获取配置信息
                    getProperty : {
                        url : "property.do?action=property",
                        args : [ 
                                 "name"
                        ]
                    },
                    // 修改配置信息
                    modifyProperty : {
                        url : "property.do?action=modify",
                        args : [ 
                                 "name",
                                 "value"
                        ]
                    },
                    // 获取用户组列表
                    getAllGroups : {
                        url : "user.do?action=allgroups"
                    },
                    // 获取用户列表
                    getAllUsers : {
                        url : "user.do?action=alllist"
                    },
                    // 添加用户
                    addUser : {
                        url : "user.do?action=add",
                        args : [ 
                                 "groupId", //用户组id
                                 "userName", // 账号
                                 "passwd" // 密码
                        ]
                    },
                    // 修改用户
                    modifyUser : {
                        url : "user.do?action=modifyuser",
                        args : [ 
                                 "id",
                                 "groupId", //用户组id
                                 "userName", // 账号
                                 "passwd", // 密码
                                 "isAdmin",
                                 "isValid",
                                 "counter",
                                 "isBooker"
                        ]
                    },
                    disableCounter : {
                        url : "user.do?action=disablecounter",
                        args : [ 
                                "id"
                       ]
                    },
                    // 获取服务队列
                    getServeQueues : {
                        url : "servequeue.do?action=alllist",
                        args : [ 
                                 "step",
                                 "isBooker"
                        ]
                    },
                    // 获取单个服务单
                    getServeQueue : {
                        url : "servequeue.do?action=getone",
                        args : [ 
                                 "step",
                                 "isBooker"
                        ]
                    },
                    // call
                    call : {
                    	url : "servequeue.do?action=call"
                    },
                    // hold
                    hold : {
                        url : "servequeue.do?action=hold",
                        args : [ 
                                "id"
                       ]
                    },
                    // resume
                    resume : {
                        url : "servequeue.do?action=cancelhold",
                        args : [ 
                                "id"
                       ]
                    },
                    // send
                    send : {
                        url : "servequeue.do?action=send",
                        args : [ 
                                "id",
                                "roofNum",
                                "jobType",
                                "additionTime",
                                "isWarrant",
                                "isSubContract",
                                "hour",
                                "minute"
                       ]
                    },
                    // cancel
                    cancel : {
                        url : "servequeue.do?action=cancel",
                        args : [ 
                                "id"
                       ]
                    },
                    // 获取订单列表
                    getOrderList : {
                        url : "order.do?action=alllist",
                        args : [ 
                                 "status",
                                 "startStatus",
                                 "assignDate"
                        ]
                    },
                    getBookedOrderList : {
                        url : "order.do?action=allbooklist",
                        args : [ 
                                 "status",
                                 "startStatus",
                                 "isBook"
                        ]                        
                    },
                    getBookedOrderListByDate : {
                        url : "order.do?action=alltodaybooklist",
                        args : [ 
                                 "beginDate",
                                 "endDate",
                                 "isBook",
                                 "groupid",
                                 "express"
                        ]                        
                    },
                    getBookedOrderListByDay : {
                        url : "order.do?action=allcurdaybooklist",
                        args : [ 
                                 "assignDate"
                        ]                        
                    },
                    getBookedOrderListByWeek : {
                        url : "order.do?action=allcurweekbooklist",
                        args : [ 
                                   "beginDate",
                                   "endDate"
                        ]                        
                    },
                    getBookedOrderListByMonth : {
                        url : "order.do?action=allcurmonthbooklist",
                        args : [ 
                                    "beginDate",
                                    "endDate"
                        ]                        
                    },
                    // 获取单个订单
                    getOrder : {
                    	url : "order.do?action=getone",
                        args : [ 
                                 "registerNum"
                        ]
                    },
                    // 下订单
                    startOrder : {
                    	url : "order.do?action=start",
                        args : [ 
                                 "registerNum",
                                 "assignDate"
                        ]
                    },
                    book : {
                        url : "order.do?action=book",
                        args : [ 
                                 "registerNum",
                                 "userName",
                                 "mobilePhone",
                                 "jobType",
                                 "assignDate",
                                 "bookStartTime",
                                 "groupid",
                                 "comment",
                                 "express"
                        ]
                    },
                    update : {
                        url : "order.do?action=update",
                        args : [ 
                                 "registerNum",
                                 "userName",
                                 "mobilePhone",
                                 "jobType",
                                 "assignDate",
                                 "bookStartTime",
                                 "groupid",
                                 "comment",
                                 "express"
                        ]
                    },
                    // 获取modify queue
                    getModifyQueue : {
                        url : "modifyqueue.do?action=getone",
                        args : [ 
                                 "id"
                        ]
                    },
                    getIssueQueue : {
                        url : "issuequeue.do?action=getone",
                        args : [ 
                                 "id"
                        ]
                    },
                    // 获取modifyqueue list
                    getModifyQueues : {
                        url : "modifyqueue.do?action=alllist",
                        args : [ 
                                 "step"
                        ]
                    },
                    // Start, Hold and Finish in mechanic update
                    mStart : {
                        url : "modifyqueue.do?action=start",
                        args : [ 
                                 "id"
                        ]
                    },
                    mHold : {
                        url : "modifyqueue.do?action=hold",
                        args : [ 
                                 "id"
                        ]
                    },
                    mFinish : {
                        url : "modifyqueue.do?action=finish",
                        args : [ 
                                 "id"
                        ]
                    },
                    mReject : {
                        url : "modifyqueue.do?action=reject",
                        args : [ 
                                 "id"
                        ]
                    },
                    mPreapprove : {
                        url : "modifyqueue.do?action=preapproval",
                        args : [ 
                                 "id"
                        ]
                    },
                    iStart : {
                        url : "issuequeue.do?action=start",
                        args : [ 
                                 "id"
                        ]
                    },
                    iHold : {
                        url : "issuequeue.do?action=hold",
                        args : [ 
                                 "id"
                        ]
                    },
                    iFinish : {
                        url : "issuequeue.do?action=finish",
                        args : [ 
                                 "id"
                        ]
                    },
                    // 分配
                    allocate : {
                        url : "modifyqueue.do?action=allocate",
                        args : [ 
                                "id",
                                "modifierId"
                       ]
                    },
                    iAllocate : {
                        url : "issuequeue.do?action=allocate",
                        args : [ 
                                "id",
                                "modifierId"
                       ]
                    },
                    // 获取洗车列表
                    getCarWashQueues : {
                        url : "cashqueue.do?action=alllist",
                        args : [ 
                                 "step"
                        ]
                    },
                    // Start, Stop and Cancel in car wash
                    cStart : {
                        url : "cashqueue.do?action=start",
                        args : [ 
                                 "id"
                        ]
                    },
                    cFinish : {
                        url : "cashqueue.do?action=finish",
                        args : [ 
                                 "id"
                        ]
                    },
                    cCancel : {
                        url : "cashqueue.do?action=cancel",
                        args : [ 
                                 "id"
                        ]
                    },
                    // 获取工作调度
                    getTodayModifyQueue: {
                        url : "modifyqueue.do?action=gettoday"
                    },
                    // 获取Job Type列表
                    getJobTypes : {
                        url : "jobtype.do?action=getall"
                    },
                    // 添加Job Type
                    addJobType : {
                        url : "jobtype.do?action=add",
                        args : [ 
                                "name",
                                "value"
                       ]
                    },
                    // 修改Job Type
                    modifyJobType : {
                        url : "jobtype.do?action=modify",
                        args : [ 
                                "id",
                                "name",
                                "value"
                       ]
                    },
                    // 删除Job Type
                    deleteJobType : {
                        url : "jobtype.do?action=delete",
                        args : [ 
                                "id",
                       ]
                    },
                    // workload
                    getAllWorkload : {
                        url : "userworkload.do?action=getallload",
                        args : [ 
                                "name",
                       ]
                    },
                    getUserWorkload : {
                        url : "userworkload.do?action=getone",
                        args : [ 
                                "id",
                       ]
                    },
                    getReport : {
                        url : "order.do?action=report",
                        args : [ 
                                "startDate",
                                "endDate"
                       ]
                    }
                },
                ajax : function(key, args) {
                    var self = this;
                    var domain = conf.domain;
                    var api = self.api;
                    var url = api[key].url, data = "";
                    var a = api[key].args;
                    var params = {};
                    var type = args.type !== "undefined" ? args.type : "GET";
                    if (args.data && a) {
                        for (var i = 0; i < a.length; ++i) {
                            params[a[i]] = args.data[a[i]] !== "undefined" ? args.data[a[i]] : "";
                        }
                    }
                    var xhr = $.ajax({
                        type : type,
                        url : domain + url,
                        data : {
                            "param" : JSON.stringify(params)
                        },
                        cache : false,
                        contentType : "application/x-www-form-urlencoded;charset=UTF8",
                        dataType : "json",
                        timeout : 1000 * 30, // 超时时间
                        error : function(xhr, type, error_thrown) {
                            // "timeout", "error", "abort",
                            // "parsererror"
                            if (type == "timeout") {// 请求超时
                                alert('Request timeout, please try later.');
                            } else if (type == "parsererror") {
                                alert('Data parsing error, please try later.');
                            } else if (type == "abort") {
                                alert('Request failed, please check your network.');
                            }else if (type == "error") {
                                alert('The server has no response.');
                            }
                            if (args.error) {
                                args.error(error_thrown);
                            }
                        },
                        success : function(data) {
                            if (data.code == "000004") {// 登录超时跳转到登录界面
                                alert('Your session has been timeout, please login again.');
                                location.href="index.html";
                            } else {                                
                                if(data.code != "000000") {
                                    console.log(data.code + ' ' + data.msg + ' ' + domain + url);
                                }
                                if (args.success) {
                                    args.success(data);
                                }
                            }

                        },
                        beforeSend : function(data) {
                            if (args.beforeSend) {
                                args.beforeSend(data);
                            }
                        },
                        complete : function(data) {
                            if (args.complete) {
                                args.complete(data);
                            }
                        }
                    });
                    return xhr;

                }
            };

            $.common = common;
        }
    })($);

    (function($) {

    })($);
});
