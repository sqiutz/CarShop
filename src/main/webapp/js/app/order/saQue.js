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
            $('#resumeBtn').text(RESUME).attr('title', RESUME);
            $('#sendToWorkshopBtn').text(WORKSHOP).attr('title', SEND_TO_WORKSHOP);
            $('#showDetails').text(SHOW_DETAILS + ' >>');
            $('#servingListLink').text(NOW_SERVING + '...');
            $('#holdListLink').text(HOLD_LIST);
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
    
    var oListIter = 0, sListIter = 0, interval = 3000, tab = 0;
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
	var selectedId = 0;
    var createServingList = function(serves) {
        $('#servingList tr.odd').remove();
        $('#servingList tr.even').remove();
        var availHeight = ($('#content').height() - 20) * 0.5 - $('#servingListTitle').height() - 15;
        var j, num = Math.floor(availHeight / 43) - 1;
        for (var i = 0; i < num; i++) {
            j = sListIter * num + i;
            var serve = serves && j < serves.length ? serves[j] : null;
            var tr = $('<tr></tr>').attr('class', i % 2 === 0 ? 'odd' : 'even')
                    .appendTo($('#servingList'));
            if(serve) {
                tr.addClass('hoverable').val(serve.id);
                if(1 === tab) {
                	if(serve.id === parseInt(selectedId)) {
                        tr.addClass('selected');
                    }
                    tr.bind('click', function() {
                        $('#servingList tr').removeClass('selected');
                        $(this).addClass('selected');
                        selectedId = $(this).val();
                        if(!currServe) {
                            $('#resumeBtn').attr('disabled', false);
                        }
                    })
                }                
            }
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
            	if(0 === tab) {
            		getServeQueues();
            	}
            	else {
            		getHoldQueues();
            	}
            }, interval);
        }
    };
    
    // 获取服务队列
    var getServeQueues = function() {
    	if(0 !== tab) {
    		return;
    	}
        $.OrderInfo.getServeQueues({
            data : {
                step : 0,
                isBooker : userProfile.isBooker
            },
            success : function(serves) {
            	if(0 !== tab) {
            		return;
            	}
                createServingList(serves);
            }
        });
    };
    
    // 获取Hold队列
    var getHoldQueues = function() {
    	if(1 !== tab) {
    		return;
    	}
        $.OrderInfo.getServeQueues({
            data : {
                step : 1,
                isBooker : userProfile.isBooker
            },
            success : function(serves) {
            	if(1 !== tab) {
            		return;
            	}
                createServingList(serves);
            }
        });
    };
    
    // 创建订单列表
    var createWaitingList = function(orders) {
    	$('#remaining').text(orders ? orders.length : 0);
        $('#waitingList tr.odd').remove();
        $('#waitingList tr.even').remove();
        var availHeight = ($('#content').height() - 20) * 0.5 - $('#waitingListTitle').height() - 15;
        var j, num = Math.floor(availHeight / 43) - 1;
        for (var i = 0; i < num; i++) {
            j = oListIter * num + i;
            var order = orders && j < orders.length ? orders[j] : null;
            var tr = $('<tr></tr>').attr('class', i % 2 === 0 ? 'odd' : 'even')
                    .appendTo($('#waitingList'));
            if(order) {
                tr.addClass('hoverable').val(order.id);
            }
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
    var currServe;
    var getServeQueue = function() {
    	$.OrderInfo.getServeQueue({
    		data : {
                step : 0,
                isBooker : userProfile.isBooker
            },
            success : function(data) {
            	if(data.code == '000000') {
            	    currServe = data.resList[0];
            		if(currServe) {
            		    $('#callBtn').attr('disabled', 'disabled');
            		    $('#resumeBtn').attr('disabled', 'disabled');
            		    $('#holdBtn').attr('disabled', false);
            		    $('#sendToWorkshopBtn').attr('disabled', false); 
            		}
            		else {
            		    $('#callBtn').attr('disabled', false);
            		    $('#holdBtn').attr('disabled', 'disabled');
            		    $('#resumeBtn').attr('disabled', 'disabled');
            		    $('#sendToWorkshopBtn').attr('disabled', 'disabled');
            		}
            		$('#currentNo').text(currServe ? currServe.order.registerNum : '')
            	}
            }
    	});
    }
       
    // Call
    $('#callBtn').bind('click', function() {
    	$.OrderInfo.call({
    		success : function(data) {
    		    if(data.code == '000000') {
                    getServeQueue();
    		    }
            }
    	});
    });
    
    // Hold
    $('#holdBtn').bind('click', function() {
        $.OrderInfo.hold({
            data : {
                id : currServe.id               
            },
            success : function(data) {
                if(data.code == '000000') {
                    getServeQueue();
                }                
            }
        });
    });
    
    // Resume
    $('#resumeBtn').bind('click', function() {
        $.OrderInfo.resume({
            data : {
                id : selectedId,         
            },
            success : function(data) {
                if(data.code == '000000') {
                    getServeQueue();
                }                
            }
        });
    });
    
    // send
    $('#sendToWorkshopBtn').bind('click', function() {
        $.OrderInfo.send({
            data : {
                id : currServe.id               
            },
            success : function(data) {
                if(data.code == '000000') {
                    getServeQueue();
                }                
            }
        });
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
    
    $('#holdListLink').bind('click', function() {
    	if('unselected' === $('#holdListTab').attr('class')) {
    		sListIter = 0;
    		tab = 1;
    		selectedId = 0;
    		$('#holdListTab').attr('class', '');
    		$('#servingListTab').attr('class', 'unselected');
    		getHoldQueues();
    	}
    });
    
    $('#servingListLink').bind('click', function() {
    	if('unselected' === $('#servingListTab').attr('class')) {
    		sListIter = 0;
    		tab = 0;
    		selectedId = 0;
    		$('#servingListTab').attr('class', '');
    		$('#holdListTab').attr('class', 'unselected');
    		getServeQueues();
    	}
    })
    
    //getServeQueue();
    //getServeQueues();
    //getOrderList();    
})(jQuery);;