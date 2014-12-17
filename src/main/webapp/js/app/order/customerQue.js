(function($) {
    applyLang();
    $.UserInfo.getProperty({
        data : {
            name : 'LANGUAGE'
        },
        success : function(data) {
            if (data.code == '000000') {
                var langCode = data.obj.value;
                applyLang(langCode);
            }
        }
    });
    
    function applyLang(langCode) {
        if(undefined === langCode || null === langCode) {
            langCode = 'en_US';
        }
        loadLang('lang/' + langCode + '.js', function() {
            $('#title').text(CUSTOMER_QUE_STATUS_BOARD);
            $('#servingListTitle').text(NOW_SERVING + '...');
            $('#welcome').text(WELCOME);
            $('#waitingListTitle').text(WAITING_LIST);
            $('#regNoCol').text(REG_NO);
            $('#estTimeCol').text(EST_WAIT);
            $('#nRegNoCol').text(REG_NO);
            $('#nEstTimeCol').text(EST_WAIT);
            $('#plsProceedTo').text(PLEASE_PROCEED_TO);
            $('#booking').text(BOOKING);
            $('#nonBooking').text(NON_BOOKING);
        });
    }
    
	var oListIter = 0, nListIter = 0, sListIter = 0, interval = 3000;

	// 创建服务队列
	var createServingList = function(serves) {
		$('#servingList div.b1').remove();
		var j, num = 3;
		for (var i = sListIter, j = 0; i < num + sListIter; i++,j++) {
			var serve = serves && i < serves.length ? serves[i] : null;
			var div = $(
					"<div class='b1 " + (j % 2 === 0 ? "odd" : "even")
							+ "'></div>").appendTo($('#servingList'));
			$("<span></span>").text(serve ? serve.order.registerNum : '').attr(
					'class', 'b1 col1').appendTo(div);
			$("<span></span>").text(serve ? serve.order.queueNum : '').attr(
					'class', 'b1 col2').appendTo(div);
			$("<span></span>").text(serve ? serve.user.counter : '').attr(
					'class', 'b1 col3').appendTo(div);
		}
		if (serves && i < serves.length - 1) {
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
		var j, num = Math.floor(availHeight / 43) - 2;
		for (var i = 0; i < num; i++) {
			j = oListIter * num + i;
			var order = orders && j < orders.length ? orders[j] : null;
			var tr = $('<tr></tr>').attr('class', i % 2 === 0 ? 'odd' : 'even')
					.appendTo($('#waitingList'));
			$('<td></td>').text(order ? order.registerNum : '').appendTo(tr);
			$('<td></td>').text(order ? order.estimationTime + ' mins' : '').appendTo(tr);
		}
		if (orders && j < orders.length - 1) {
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
	
	// 创建NonBooking订单列表
    var createNonBookingList = function(orders) {
        $('#nonBookingList tr.odd').remove();
        $('#nonBookingList tr.even').remove();
        var availHeight = $('#content').height() - $('#welcome').height()
            - $('#servingList').height() - $('#waitingListTitle').height()
            - 15;
        var j, num = Math.floor(availHeight / 43) - 2;
        for (var i = 0; i < num; i++) {
            j = nListIter * num + i;
            var order = orders && j < orders.length ? orders[j] : null;
            var tr = $('<tr></tr>').attr('class', i % 2 === 0 ? 'odd' : 'even')
                    .appendTo($('#nonBookingList'));
            $('<td></td>').text(order ? order.registerNum : '').appendTo(tr);
            $('<td></td>').text(order ? order.estimationTime + ' mins' : '').appendTo(tr);
        }
        if (orders && j < orders.length - 1) {
            nListIter++;
            setTimeout(function() {
                createNonBookingList(orders);
            }, interval);
        } else {
            nListIter = 0;
            setTimeout(function() {
                getNonBookingList();
            }, interval);
        }
    };
    
    // 获取NonBooking订单列表
    var getNonBookingList = function() {
        $.OrderInfo.getOrderList({
            data : {
                status : 1
            },
            success : createNonBookingList
        });
    };

	getServeQueues();
	getOrderList();
	getNonBookingList();

})(jQuery);