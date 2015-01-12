﻿define([ "jquery", "userInfo", "orderInfo"], function() {
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
                                 "startStatus"
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
                    // 获取单个订单
                    getOrder : {
                    	url : "order.do?action=getone",
                        args : [ 
                                 "registerNumber"
                        ]
                    },
                    // 下订单
                    startOrder : {
                    	url : "order.do?action=start",
                        args : [ 
                                 "registerNumber"
                        ]
                    },
                    // 获取modify queue
                    getModifyQueue : {
                        url : "modifyqueue.do?action=getone",
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
                    // 分配
                    allocate : {
                        url : "modifyqueue.do?action=delete",
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
                                self.showTips({
                                    msg : "请求超时,稍候再试..",
                                    bottom : "50%",
                                    times : 5000
                                });
                            } else if (type == "parsererror") {
                                self.showTips({
                                    msg : "数据转换出错,稍候再试..",
                                    bottom : "50%",
                                    times : 5000
                                });
                            } else if (type == "abort") {
                                self.showTips({
                                    msg : "请求失败,检查网络..",
                                    bottom : "50%",
                                    times : 5000
                                });
                            }
                            if (args.error) {
                                args.error(error_thrown);
                            }
                        },
                        success : function(data) {
                            if (data.code == "000004") {// 登录超时跳转到登录界面
                                alert('Your session has been timeout, please login again.');
                                location.href="index.html";
                                /*
                                 * require(['jsMessage'],function(){
                                 * dhtmlx.message(data.msg);
                                 * $.UserInfo.logOut(); setTimeout(function(){
                                 * location.href="login.html";
                                 * 
                                 * window.header.init(); },1500); });
                                 */
                            } else {
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

                },
                showTips : function(data, callback) {
                    /*
                     * require(["jnotify"],function(jnotify){ if(typeof
                     * data ==="string"){ jNotify(data); }else{
                     * jNotify(data.msg); } });
                     */
                }
            };

            $.common = common;
        }
    })($);

    (function($) {

    })($);
});
