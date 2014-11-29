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
		},
		
		// 获取订单列表 
		getOrderList : function(status, complete) {
			var orderlist;
			$.OrderInfo.getOrderList({
				data : {
					status : 1
				},
				success : function(data) {
					orderlist = data.resList;
				},
				complete : function(data) {
					if(complete) {
						complete(orderlist);
					}
				}
			});
		},
		
		createWaitingList : function(orders) {
			$('#waitingList tr.odd').remove();
			$('#waitingList tr.even').remove();
			var availHeight = $('#content').height() - 
				$('#welcome').height() - 
				$('#servingList').height() - 
				$('#waitingListTitle').height() - 15;
			var num = Math.floor(availHeight / 43) - 1;
			for(var i = 0; i < num; i++) {
				var tr = $('<tr></tr>').attr('class', i%2===0?'odd':'even')
					.appendTo($('#waitingList'));
				var order = orders && i < orders.length ? orders[i] : null
				$('<td></td>').text(order ? order.registerNum : '')
					.appendTo(tr);
				$('<td></td>').text(order ? order.queueNum : '')
				.appendTo(tr);
				$('<td></td>').text('')
				.appendTo(tr);
			}
		}
	};

	serve.getServeQueues();
	
	serve.getOrderList(1, serve.createWaitingList);
	
	//serve.createWaitingList();
})(jQuery);