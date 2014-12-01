(function($) {
	var oListIter = 0, sListIter = 0, interval = 3000;

	// 创建服务队列
	var createServingList = function(serves) {
		$('#servingList div').remove();
		var j, num = 3;
		for (var i = 0; i < num; i++) {
			j = sListIter * num + i;
			var serve = serves && j < serves.length ? serves[j] : null;
			var div = $(
					"<div class='b1 " + (i % 2 === 0 ? "odd" : "even")
							+ "'></div>").appendTo($('#servingList'));
			$("<span></span>").text(serve ? serve.order.queueNum : '').attr(
					'class', 'b1 col1').appendTo(div);
			$("<span></span>").text(serve ? serve.order.registerNum : '').attr(
					'class', 'b1 col2').appendTo(div);
			$("<span></span>").text(serve ? serve.user.counter : '').attr(
					'class', 'b1 col3').appendTo(div);
		}
		if (j < serves.length - 1) {
			sListIter++;
			setTimeout(function() {
				createServingList(serves);
			}, interval);
		} else {
			sListIter = 0;
			setTimeout(function() {
				getServeQueues();
			}, interval);
		}
	};

	var createCurrServe = function(serves) {
		if (serves && serves.length > 0) {
			var curr = serves[0];
			$('#currRegNum').text(curr.order.registerNum);
			$('#currUserName').text(curr.user.counter);
		}
	};

	// 获取服务队列
	var getServeQueues = function() {
		$.OrderInfo.getServeQueues({
			data : {
				step : 0
			},
			success : function(serves) {
				createServingList(serves);
				createCurrServe(serves);
			}
		});
	};

	// 创建订单列表
	var createWaitingList = function(orders) {
		$('#waitingList tr.odd').remove();
		$('#waitingList tr.even').remove();
		var availHeight = $('#content').height() - $('#welcome').height()
				- $('#servingList').height() - $('#waitingListTitle').height()
				- 15;
		var j, num = Math.floor(availHeight / 43) - 1;
		for (var i = 0; i < num; i++) {
			j = oListIter * num + i;
			var order = orders && j < orders.length ? orders[j] : null;
			var tr = $('<tr></tr>').attr('class', i % 2 === 0 ? 'odd' : 'even')
					.appendTo($('#waitingList'));
			$('<td></td>').text(order ? order.registerNum : '').appendTo(tr);
			$('<td></td>').text(order ? order.queueNum : '').appendTo(tr);
			$('<td></td>').text(order ? order.estimationTime + ' mins' : '').appendTo(tr);
		}
		if (j < orders.length - 1) {
			oListIter++;
			setTimeout(function() {
				createWaitingList(orders);
			}, interval);
		} else {
			oListIter = 0;
			setTimeout(function() {
				getOrderList();
			}, interval);
		}
	};

	// 获取订单列表
	var getOrderList = function() {
		$.OrderInfo.getOrderList({
			data : {
				status : 1
			},
			success : createWaitingList
		});
	};

	getServeQueues();
	getOrderList();

})(jQuery);