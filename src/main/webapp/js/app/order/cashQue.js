(function($) {
	$('#callBtn').attr('disabled', 'disabled');
//	$('#recallBtn').attr('disabled', 'disabled');
    $('#finishBtn').attr('disabled', 'disabled');
    $('#cancelBtn').attr('disabled', 'disabled');
    $.UserInfo.getProperty({
        data : {
            name : 'LANGUAGE'
        },
        success : function(data) {
            if (data.code == '000000') {
                var langCode = data.obj.value;
                applyLang(langCode);
            }else {
                applyLang();
            }
        },
        error : function() {
            applyLang();
        }
    });
    
    function applyLang(langCode) {
        if(undefined === langCode || null === langCode) {
            langCode = 'en_US';
        }
        loadLang('lang/' + langCode + '.js', function() {
            $('#changePwd').text(CHANGE_PASSW0RD).attr('title', CHANGE_PASSW0RD);
            $('#logout').text(LOGOUT).attr('title', LOGOUT);
            $('#title').text(CASH_QUE_CALLING_BOARD);
            $('#currentNoLabel').text(CURRENT_NUMBER);
            $('#remainingLabel').text(REMAINING);
            $('#waitingTimeLabel').text(WAITING_TIME);
//            $('#avgWaitingTimeLabel').text(AVG_WAITING_TIME);
            $('#timerLabel').text(TIMER);
            $('#callBtn').text(CALL).attr('title', CALL);
//            $('#recallBtn').text(RECALL).attr('title', RECALL);
//            $('#holdBtn').text(HOLD).attr('title', HOLD);
            $('#finishBtn').text(FINISH).attr('title', FINISH);
            $('#cancelBtn').text(CANCEL).attr('title', CANCEL);
            $('#servingListTitle').text(NOW_SERVING + '...');
//            $('#holdListLink').text(SUSPEND_LIST);
            $('#waitingListTitle').text(WAITING_LIST);
            $('#regNoCol').text(REG_NO);
            $('#queNoCol').text(QUE_NO);
            $('#saCol').text(SA);
            $('#startTimeCol').text(START_TIME);
            $('#endTimeCol').text(END_TIME);
            $('#wRegNoCol').text(REG_NO);
            $('#wQueNoCol').text(QUE_NO);
            $('#queStartCol').text(QUE_START);            
            $('#legendTitle').text(INPUT_EDIT_DATA);
            $('#regNoLabel').text(REG_NO);
            $('#additionTimeLabel').text(ADDITION_TIME);
            $('#roofNoLabel').text(ROOF_NO);
            $('#serviceAdvisorLabel').text(SERVICE_ADVISOR);
            $('#jobTypeLabel').text(JOB_TYPE);
            $('#rWarranty').text(WARRANTY);
            $('#rSubContract').text(SUB_CONTRACT);
            $('#promiseTimeRefLabel').text(PROMISE_TIME + ' - ' + REFERENCE);
            $('#promiseTimeLabel').text(PROMISE_TIME);
            $('#finishBtn').text(FINISH).attr('title', FINISH);
            $('#backBtn').text(BACK).attr('title', BACK);
            
            $.UserInfo.checkLogin({
                success : function(data) {
                    if (data.code == '000000' && data.obj) {
                        userProfile = data.obj;
                        $("#helloUserName").text(
                                HELLO + ' ' + (userProfile ? userProfile.userName : ''));
                        $('#userName').text(userProfile ? userProfile.userName : '');
                        //getServeQueue();
                        getServeQueues();
                        getOrderList(); 
                    }
                }
            });
        });
    }
   
    var oListIter = 0, sListIter = 0, interval = 3000;
    var userProfile;
	    
    // 创建服务队列
	var selectedId = 0;
    var createServingList = function(serves) {
        $('#servingList tr.odd').remove();
        $('#servingList tr.even').remove();
        var availHeight = ($('#content').height() - 20) * 0.5 - $('#servingListTitle').height() - 4;
        var j, num = Math.floor(availHeight / 43) - 1;
        for (var i = 0; i < num; i++) {
            j = sListIter * num + i;
            var serve = serves && j < serves.length ? serves[j] : null;
            var tr = $('<tr></tr>').attr('class', i % 2 === 0 ? 'odd' : 'even')
                    .appendTo($('#servingList'));
            if(serve) {
                if(i === 0) {
                    if(!currServe || currServe.order.id !== serve.id) {
                        currServe = serve;
                        getServeQueue(serve.id);
                    }                    
                }
                tr.addClass('hoverable').val(serve.id);

                	if(serve.id === parseInt(selectedId)) {
                        tr.addClass('selected');
                    }
                    tr.bind('click', function() {
                        $('#servingList tr').removeClass('selected');
                        $(this).addClass('selected');
                        selectedId = $(this).val();
                    });

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
            	getServeQueues();
            }, interval);
        }
    };
    
    // 获取服务队列
    var getServeQueues = function() {
        $.OrderInfo.getSettleQueues({
            data : {
                step : 1
            },
            success : function(serves) {
                createServingList(serves);
            }
        });
    };
        
    // 创建订单列表
    var waitinglist;
    var createWaitingList = function(orders) {
        waitinglist = orders;
    	$('#remaining').text(orders ? orders.length : 0);
    	if(!currServe && orders && orders.length > 0) {
    		$('#callBtn').attr('disabled', false);
    	}
        $('#waitingList tr.odd').remove();
        $('#waitingList tr.even').remove();
        var availHeight = ($('#content').height() - 20) * 0.5 - $('#waitingListTitle').height() - 4;
        var j, num = Math.floor(availHeight / 43) - 1;
        for (var i = 0; i < num; i++) {
            j = oListIter * num + i;
            var order = orders && j < orders.length ? orders[j].order : null;
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
        $.OrderInfo.getSettleQueues({
            data : {
                step : 0
            },
            success : function(serves) {
                createWaitingList(serves);
            }
        });
    };
    
    // 获取当前订单
    var currServe;
    var getServeQueue = function(id) {
    	$.OrderInfo.getSettleQueue({
    		data : {
                id : id
            },
            success : function(data) {
            	if(data.code == '000000') {
            	    currServe = data.obj;
            		if(currServe) {
            		    $('#finishBtn').attr('disabled', false); 
                        $('#cancelBtn').attr('disabled', false); 
            		    start = new Date(currServe.startTime);
                        //timer();
            		}
            		else {
            		    $('#finishBtn').attr('disabled', 'disabled');
                        $('#cancelBtn').attr('disabled', 'disabled');
            		    //start = 0;
            		}
            		$('#currentNo').text(currServe ? currServe.order.queueNum : '')
            		//$('#waitingMins').text(currServe ? getMins(currServe.delayTime) + "'" : '');
                    //$('#waitingSecs').text(currServe ? getSecs(currServe.delayTime) + "''" : '');
            	}
            }
    	});
    }
       
    // Call
    $('#callBtn').bind('click', function() {
        var order = waitinglist[0].order;
        $.OrderInfo.sStart({
            data : {
                id : order.id               
            },
            success : function(data) {
                if(data.code == '000000') {
                    getServeQueue(order.id);
                }                
            }
        });
    });
    
    $('#finishBtn').bind('click', function() {
        $.OrderInfo.sFinish({
            data : {
                id : currServe.order.id               
            },
            success : function(data) {
                if(data.code == '000000') {
                    currServe = null;
                    $('#finishBtn').attr('disabled', 'disabled');
                    $('#cancelBtn').attr('disabled', 'disabled');
                    $('#currentNo').text('');
                }                
            }
        });
    });
    
    $('#cancelBtn').bind('click', function() {
        $.OrderInfo.sCancel({
            data : {
                id : currServe.order.id               
            },
            success : function(data) {
                if(data.code == '000000') {
                    currServe = null;
                    $('#finishBtn').attr('disabled', 'disabled');
                    $('#cancelBtn').attr('disabled', 'disabled');
                    $('#currentNo').text('');
                }                
            }
        });
    });
        
    // Timer
//    var start = 0;
//    function timer() {
//        if(0 == start) {
//            $('#timerMinutes').text(0 + "'");
//            $('#timerSeconds').text(0 + "''");
//            return;
//        }
//        var now = new Date();
//        $('#timerMinutes').text(getMinutes(now) + "'");
//        $('#timerSeconds').text(getSeconds(now) + "''");
//        setTimeout(function() {
//            timer()
//        }, 1000);
//    }
//    
//    function getMinutes(now) {
//        var diff = now - start;
//        return getMins(diff);
//    }
//    
//    function getSeconds(now) {        
//        var diff = now - start;
//        return getSecs(diff);
//    }
//    
//    function getMins(diff) {
//        return Math.floor(diff / 60000);
//    }
//    
//    function getSecs(diff) {
//        var mins = getMins(diff);
//        return Math.floor((diff - mins * 60000) / 1000);
//    }
})(jQuery);