(function($) {
	var serve = {
		// 获取服务队列
		getServeQueues : function() {
			var that = this;
			$.OrderInfo
					.getServeQueues({
						data : {
							step : 0
						},
						success : function(data) {
							$('#servingList div').remove();
							that._serves = data.resList;
							var size = that._serves.length;
							for (var i = 0; i < 3; i++) {
								var serve = i < size ? that._serves[i] : null;
								var div = $(
										"<div class='b1 "
												+ (i % 2 === 0 ? "odd" : "even")
												+ "'></div>")
												.appendTo($('#servingList'));
								$("<span class='b1 col1'></span>").text(
										serve ? serve.order.queueNum : '')
										.appendTo(div);
								$("<span class='b1 col2'></span>").text(
										serve ? serve.order.registerNum : '')
										.appendTo(div);
								$("<span class='b1 col3'></span>").text(
										serve ? serve.user.userName : '')
										.appendTo(div);
							}
						},
						complete : this.currServe
					});
		},

		// 叫号
		currServe : function() {
			if (serve._serves && serve._serves.length > 0) {
				var curr = serve._serves[0];
				$('#currRegNum').text(curr.order.registerNum);
				$('#currUserName').text(curr.user.userName);
			}
		}
	};

	serve.getServeQueues();
})(jQuery);