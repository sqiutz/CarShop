(function($) {
    $('#startBtn').attr('disabled', 'disabled');
    $('#cancelBtn').attr('disabled', 'disabled');
    $('#finishBtn').attr('disabled', 'disabled');
    
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
            $('#title').text(CAR_WASH_QUE_BOARD);
            $('#currentNoLabel').text(CURRENT_NUMBER);
            $('#startBtn').text(START).attr('title', START);
            $('#finishBtn').text(FINISH).attr('title', FINISH);
            $('#cancelBtn').text(CANCEL).attr('title', CANCEL);            
            $('#nextOnQueLink').text(NEXT_ON_QUE);
            $('#inProgressLink').text(IN_PROGRESS);
            $('#regNoCol').text(REG_NO);
            $('#RoofNoCol').text(ROOF_NO);
            $('#saCol').text(SA);
            $('#promiseTimeCol').text(PROMISE_TIME);
        });
    } 
    
    var cListIter = 0, interval = 3000;
    // 创建洗车列表
    var currQue, selectedId = 0;
    var createCarWashList = function(queues) {
        $('#carWashList tr.odd').remove();
        $('#carWashList tr.even').remove();
        var availHeight = $('#content').height() - $('#currentDiv').height() - 
            $('#controllerDiv').height() - $('#nextOnQue').height() - 64;
        var j, num = Math.floor(availHeight / 43) - 1;
        for (var i = 0; i < num; i++) {
            j = cListIter * num + i;
            var queue = queues && j < queues.length ? queues[j] : null;
            if(0 == selectedId && 0 == i) {
                setCurrentCarWashQue(queue);
            }
            var tr = $('<tr></tr>').attr('class', i % 2 === 0 ? 'odd' : 'even')
                    .appendTo($('#carWashList'))
                    .data(queue);
            if(queue) {
                tr.addClass('hoverable').val(queue.id);
                if(queue.id == selectedId) {
                    tr.addClass('selected');
                }
                tr.bind('click', function() {
                    $('#carWashList tr').removeClass('selected');
                    $(this).addClass('selected');
                    currQue = $(this).data();   
                    selectedId = currQue.id;
                    setCurrentCarWashQue(currQue);
                })
            }
            $('<td></td>').text(queue && queue.order ? queue.order.registerNum : '').appendTo(tr);
            $('<td></td>').text(queue && queue.order ? queue.order.queueNum : '').appendTo(tr);
            $('<td></td>').text(queue && queue.user ?  queue.user.userName: '').appendTo(tr);
            $('<td></td>').text(queue && queue.order ? getTimeStr(queue.order.promiseTime) : '').appendTo(tr);
        }
        if (queues && j < queues.length - 1) {
            cListIter++;
            setTimeout(function() {
                createWaitingList(queues);
            }, interval);
        } else {
            cListIter = 0;
            setTimeout(function() {
                if(0 == tab) {
                    getCarWashList();
                }
                else {
                    getInProgressList();
                }
            }, interval);
        }
    };
    
    var setCurrentCarWashQue = function(queue) {
        currQue = queue;
        if(queue) {
            if(0 == queue.step) {
                $('#startBtn').attr('disabled', false);
                $('#finishBtn').attr('disabled', 'disabled');
                $('#cancelBtn').attr('disabled', false);
            }
            else if(1 == queue.step){
                $('#finishBtn').attr('disabled', false);
                $('#startBtn').attr('disabled', 'disabled');
                $('#cancelBtn').attr('disabled', false);
            }
            else {
                $('#startBtn').attr('disabled', 'disabled');
                $('#cancelBtn').attr('disabled', 'disabled');
                $('#finishBtn').attr('disabled', 'disabled');
            }            
            
        }
        else {
            $('#startBtn').attr('disabled', 'disabled');
            $('#cancelBtn').attr('disabled', 'disabled');
            $('#finishBtn').attr('disabled', 'disabled');
        }
        $('#currRegNo').text(queue && queue.order ? queue.order.registerNum : '');
        $('#currQueNo').text(queue && queue.order ? queue.order.queueNum : '');
    };
    
    // 获取洗车列表
    var getCarWashList = function() {
        if(0 !== tab) {
            return;
        }
        $.OrderInfo.getCarWashQueues({
            data : {
                step : 0
            },
            success : createCarWashList
        });
    };
    var getInProgressList = function() {
        if(1 !== tab) {
            return;
        }
        $.OrderInfo.getCarWashQueues({
            data : {
                step : 1
            },
            success : createCarWashList
        });
    };
    
    $('#startBtn').bind('click', function() {
        if(!currQue || 0 !== currQue.step) {
            return;
        }
        $.OrderInfo.cStart({
            data : {
                id : currQue.id
            },
            success : function(data) {
                if(data.code == '000000') {
                    currQue = null;
                    selectedId = 0;
                }
            }
        });
    });    
    $('#cancelBtn').bind('click', function() {
        if(!currQue || (0 !== currQue.step && 1 !== currQue.step)) {
            return;
        }
        $.OrderInfo.cCancel({
            data : {
                id : currQue.id
            },
            success : function(data) {
                if(data.code == '000000') {
                    currQue = null;
                    selectedId = 0;
                }
            }
        });
    });
    $('#finishBtn').bind('click', function() {
        if(!currQue || 1 !== currQue.step) {
            return;
        }
        $.OrderInfo.cFinish({
            data : {
                id : currQue.id
            },
            success : function(data) {
                if(data.code == '000000') {
                    currQue = null;
                    selectedId = 0;
                }
            }
        });
    });
    
    var tab = 0;
    $('#nextOnQueLink').bind('click', function() {
        if('unselected' === $('#nextOnQueTab').attr('class')) {
            cListIter = 0;
            tab = 0;
            selectedId = 0;
            $('#inProgressTab').attr('class', 'unselected');
            $('#nextOnQueTab').attr('class', '');
            getCarWashList();
        }
    });
    
    $('#inProgressLink').bind('click', function() {
        if('unselected' === $('#inProgressTab').attr('class')) {
            cListIter = 0;
            tab = 1;
            selectedId = 0;
            $('#nextOnQueTab').attr('class', 'unselected');
            $('#inProgressTab').attr('class', '');
            getInProgressList();
        }
    })
    
    getCarWashList();
})(jQuery);