define(
		[ "jquery", "userInfo" ],
		function() {
			(function($) {
				if (typeof $.common !== "undefined") {
					return false;
				} else {
					var common = {
						api : {
							// 登录
							userLogin : {
								url : "user.do?action=login",
								args : [ "username", // 账号
								"passwd" // 密码
								]
							},
							// 获取用户组列表
							getAllGroups : {
							    url : "user.do?action=allgroups"
							},
							// 获取用户列表
							getAllUsers : {
							    url : "user.do?action=alllist"
							}
						},
						ajax : function(key, args) {
							var self = this;
							var domain = conf.domain;
							var api = self.api;
							var url = api[key].url, data = "";
							var a = api[key].args;
							var params = {};
							var type = args.type !== "undefined" ? 
							        args.type : "GET";
							if (args.data && a) {
								for (var i = 0; i < a.length; ++i) {
									params[a[i]] = args.data[a[i]] !== "undefined" ? 
									        args.data[a[i]] : "";
								}
							}
							var xhr = $
									.ajax({
										type : type,
										url : domain + url,
										data : {
											"param" : JSON.stringify(params)
										},
										cache : false,
										contentType : "application/x-www-form-urlencoded;charset=UTF8",
										dataType : "json",
										timeout : 1000 * 30, // 超时时间
										error : function(xhr, type,
												error_thrown) {
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
												/*
												 * require(['jsMessage'],function(){
												 * dhtmlx.message(data.msg);
												 * $.UserInfo.logOut();
												 * setTimeout(function(){
												 * location.href="login.html";
												 * 
												 * window.header.init();
												 * },1500); });
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
