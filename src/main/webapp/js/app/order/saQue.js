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
            $('#changePwd').text(CHANGE_PASSW0RD);
            $('#logout').text(LOGOUT);
            $('#title').text(SA_QUE_CALLING_BOARD);
            $('#currentNoLabel').text(CURRENT_NUMBER);
            $('#remainingLabel').text(REMAINING);
            $('#waitingTimeLabel').text(WAITING_TIME);
            $('#avgWaitingTimeLabel').text(AVG_WAITING_TIME);
            $('#timerLabel').text(TIMER);
            $('#callBtn').text(CALL).attr('title', CALL);
            $('#recallBtn').text(RECALL).attr('title', RECALL);
            $('#holdBtn').text(HOLD).attr('title', HOLD);
            $('#sendToWorkshopBtn').text(WORKSHOP).attr('title', SEND_TO_WORKSHOP);
            $('#showDetails').text(SHOW_DETAILS + ' >>');
            $('#servingListTab').text(NOW_SERVING + '...');
            $('#holdListTab').text(HOLD_LIST);
            $('#waitingListTitle').text(WAITING_LIST);
            $('#regNoCol').text(REG_NO);
            $('#queNoCol').text(QUE_NO);
            $('#saCol').text(SA);
            $('#startTimeCol').text(START_TIME);
            $('#endTimeCol').text(END_TIME);
            $('#wRegNoCol').text(REG_NO);
            $('#wQueNoCol').text(QUE_NO);
            $('#queStartCol').text(QUE_START);
        });
    }
    
    var oListIter = 0, sListIter = 0, interval = 3000;
    var userProfile;
	$.UserInfo.checkLogin({
		success : function(data) {
			if (data.code == '000000' && data.obj) {
				userProfile = data.obj;
				$("#helloUserName").text(
						'Hello ' + (userProfile ? userProfile.userName : ''));
				$('#userName').text(userProfile ? userProfile.userName : '');
				getServeQueue();
				getServeQueues();
				getOrderList(); 
			}
		}
	});
    
    // 创建服务队列
    var createServingList = function(serves) {
    	$('#remaining').text(serves ? serves.length : 0);
        $('#servingList tr.odd').remove();
        $('#servingList tr.even').remove();
        var availHeight = ($('#content').height() - 20) * 0.5 - $('#servingListTitle').height() - 15;
        var j, num = Math.floor(availHeight / 43) - 1;
        for (var i = 0; i < num; i++) {
            j = sListIter * num + i;
            var serve = serves && j < serves.length ? serves[j] : null;
            var tr = $('<tr></tr>').attr('class', i % 2 === 0 ? 'odd' : 'even')
                    .appendTo($('#servingList'));
            $('<td></td>').text(serve && serve.order ? serve.order.registerNum : '').appendTo(tr);
            $('<td></td>').text(serve && serve.order ? serve.order.queueNum : '').appendTo(tr);
            $('<td></td>').text(serve && serve.user ? serve.user.userName : '').appendTo(tr);
            $('<td></td>').text(serve && serve.order ? getTimeStr(serve.order.startTime) : '').appendTo(tr);
            $('<td></td>').text(serve && serve.order ? getTimeStr(serve.order.endTime) : '').appendTo(tr);
        }
        if (serves && j < serves.length - 1) {
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
    
    // 获取服务队列
    var getServeQueues = function() {
        $.OrderInfo.getServeQueues({
            data : {
                step : 0,
                isBooker : userProfile.isBooker
            },
            success : function(serves) {
                createServingList(serves);
            }
        });
    };
    
    // 创建订单列表
    var createWaitingList = function(orders) {
        $('#waitingList tr.odd').remove();
        $('#waitingList tr.even').remove();
        var availHeight = ($('#content').height() - 20) * 0.5 - $('#waitingListTitle').height() - 15;
        var j, num = Math.floor(availHeight / 43) - 1;
        for (var i = 0; i < num; i++) {
            j = oListIter * num + i;
            var order = orders && j < orders.length ? orders[j] : null;
            var tr = $('<tr></tr>').attr('class', i % 2 === 0 ? 'odd' : 'even')
                    .appendTo($('#waitingList'));
            $('<td></td>').text(order ? order.registerNum : '').appendTo(tr);
            $('<td></td>').text(order ? order.queueNum : '').appendTo(tr);
            $('<td></td>').text(order ? getTimeStr(order.startTime) : '').appendTo(tr);
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
    
    // 获取当前订单
    var getServeQueue = function() {
    	$.OrderInfo.getServeQueue({
    		data : {
                step : 0,
                isBooker : userProfile.isBooker
            },
            success : function(data) {
            	if(data.code == '000000') {
            		var serve = data.resList[0];
            		if(serve) {
            		    $('#callBtn').attr('disabled', 'disabled');
            		}
            		$('#currentNo').text(serve ? serve.order.registerNum : '')
            	}
            }
    	});
    }
       
    // Call
    $('#callBtn').bind('click', function() {
    	$.OrderInfo.call({
    		success : function(data) {
    			$('#callBtn').attr('disabled', 'disabled');
    			getServeQueue();
            }
    	});
    });
    
    $('#cancelBtn').bind('click', function() {
        $('#callBtn').attr('disabled', false);
    });
    
    $('#sendToWorkshopBtn').bind('click', function() {
        $('#callBtn').attr('disabled', false);
    })
    
    function getTimeStr(time) {
        if(undefined === time || null === time) {
            return '';
        }
        var data = new Date(time);
        var hours = data.getHours(); 
        var minutes = data.getMinutes();
        return (hours < 10 ? '0' : '') + hours + ':' + 
            (minutes < 10 ? '0' : '') + minutes;
    }
    
    //getServeQueue();
    //getServeQueues();
    //getOrderList();    
})(jQuery);;