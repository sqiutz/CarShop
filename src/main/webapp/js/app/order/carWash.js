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
            $('#title').text(CAR_WASH_QUEU_BOARD);
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
    var selectedId = 0, defaultId = 0;
    var createCarWashList = function(queues) {
        $('#carWashList tr.odd').remove();
        $('#carWashList tr.even').remove();
        var availHeight = $('#content').height() - $('#currentDiv').height() - 
            $('#controllerDiv').height() - $('#nextOnQue').height() - 64;
        var j, num = Math.floor(availHeight / 43) - 1;
        for (var i = 0; i < num; i++) {
            j = cListIter * num + i;
            var queue = queues && j < queues.length ? queues[j] : null;
            if(0 === tab && 0 === i && 0 === selectedId) {
                setCurrentCarWashQue(queue);
            }
            var tr = $('<tr></tr>').attr('class', i % 2 === 0 ? 'odd' : 'even')
                    .appendTo($('#carWashList'));
            if(queue) {
                tr.addClass('hoverable').val(queue.id);
                if(queue.id === parseInt(selectedId)) {
                    tr.addClass('selected');
                }
                tr.bind('click', function() {
                    $('#carWashList tr').removeClass('selected');
                    $(this).addClass('selected');
                    if(0 === tab) {
                        selectedId = $(this).val();
                    }                    
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
                getCarWashList();
            }, interval);
        }
    };
    
    var setCurrentCarWashQue = function(queue) {
        if(queue) {
            $('#startBtn').attr('disabled', false);
            $('#cancelBtn').attr('disabled', false);
            $('#finishBtn').attr('disabled', false);
        }
        else {
            $('#startBtn').attr('disabled', 'disabled');
            $('#cancelBtn').attr('disabled', 'disabled');
            $('#finishBtn').attr('disabled', 'disabled');
        }
        defaultId = queue ? queue.id : 0;
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
        $.OrderInfo.cStart({
            data : {
                id : selectedId ? selectedId : defaultId
            },
            success : function(data) {
                if(data.code == '000000') {
                    
                }
            }
        });
    });    
    $('#cancelBtn').bind('click', function() {
        $.OrderInfo.cCancel({
            data : {
                id : selectedId ? selectedId : defaultId
            },
            success : function(data) {
                if(data.code == '000000') {
                    
                }
            }
        });
    });
    $('#finishBtn').bind('click', function() {
        $.OrderInfo.cFinish({
            data : {
                id : selectedId ? selectedId : defaultId
            },
            success : function(data) {
                if(data.code == '000000') {
                    
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