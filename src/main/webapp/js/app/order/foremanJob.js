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
            $('#title').text(FOREMAN_JOB_DISTRIBUTION_LIST);            
            $('#queueTitle').text(NEXT_ON_QUE);
            $('#regNoCol').text(REG_NO);
            $('#queNoCol').text(QUE_NO);
            $('#timeSendCol').text(TIME_SEND);
            $('#legendTitle').text(INPUT_EDIT_DATA);
            $('#regNoLabel').text(REG_NO);
            $('#additionTimeLabel').text(ADDITION_TIME);
            $('#roofNoLabel').text(ROOF_NO);
            $('#serviceAdvisorLabel').text(SERVICE_ADVISOR);
            $('#jobTypeLabel').text(JOB_TYPE);
            $('#rWarranty').text(WARRANTY);
            $('#rSubContract').text(SUB_CONTRACT);
            $('#promiseTimeRefLabel').text(PROMISE_TIME);            
            $('#technicianLabel').text(TECHNICIAN);
            $('#allocationBtn').text(ALLOCATION);
            $('#workloadLabel').text(WORKLOAD);
            
        });
    }
   
    // 创建modifyque列表
    var listIter = 0, interval = 3000, selectedId = 0, modifyQues, modifyQue;
    var createModifyQueue = function(queues) {
        modifyQues = queues;
        $('#modifyQueue tr.odd').remove();
        $('#modifyQueue tr.even').remove();
        var availHeight = $('#content_rg').height() - $('#queueTitle').height() - 10;
        var j, num = Math.floor(availHeight / 43) - 1;
        for (var i = 0; i < num; i++) {
            j = listIter * num + i;
            var queue = queues && j < queues.length ? queues[j] : null;
            var tr = $('<tr></tr>').attr('class', i % 2 === 0 ? 'odd' : 'even')
                    .appendTo($('#modifyQueue'));
            if(queue) {
                tr.addClass('hoverable').val(queue.id);
                if (queue.id === parseInt(selectedId)) {
                    tr.addClass('selected');
                }
                tr.bind('click', function() {
                    $('#queue tr').removeClass('selected');
                    $(this).addClass('selected');
                    selectedId = $(this).val();
                    for(var n = 0; n < modifyQues.length; n++) {
                        var que = modifyQues[n];
                        if(que.id == selectedId) {
                            modifyQue = que;
                            setCurrentModifyQue(modifyQue);
                            if(users && users.length > 0) {
                                $('#allocationBtn').attr('disabled', false);
                            }
                            break;
                        }
                    }
                });
            }
            $('<td></td>').text(queue && queue.order ? queue.order.registerNum : '').appendTo(tr);
            $('<td></td>').text(queue && queue.order ? queue.order.queueNum : '').appendTo(tr);
            $('<td></td>').text(queue ? getTimeStr(queue.createTime) : '').appendTo(tr);
        }
        if (queues && j < queues.length - 1) {
            listIter++;
            setTimeout(function() {
                createModifyQueue(queues);
            }, interval);
        } else {
            listIter = 0;
            setTimeout(function() {
                getModifyQueues();
            }, interval);
        }
    }
    
    //获取modifyque列表
    var getModifyQueues = function() {
        $.OrderInfo.getModifyQueues({
            data : {
                step : 0
            },
            success : createModifyQueue
        });
    };
    
    function setCurrentModifyQue(queue) {
        $('#regNo').val(queue && queue.order ? queue.order.registerNum : '');
        $('#roofNo').val(queue && queue.order ? queue.order.roofNum : '');
        $('#serviceAdvisor').val(queue && queue.user ? queue.user.userName : '');
        $('#jobType').val(queue ? queue.jobType + ' - ' + queue.jobtypeTime + ' hour(s)': '');
        $('#additionTime').val(queue ? queue.additionTime + ' hour(s)' : '');
        $('#isWarranty').attr("checked", queue && queue.isWarrant ? true : false);
        $('#isSubContract').attr("checked", queue && queue.isSubContract ? true : false);
        $('#promiseTime').val(queue && queue.order ? getTimeStr(queue.order.promiseTime) : '');
    }
    
    var users;
    $.UserInfo.getAllUsers({
        success : function(data) {
            users = data.resList;
            for(var i = 0; i < users.length; i++) {
                var user = users[i];
                if(user.groupName == 3) {
                    $('<option></option>').text(user.userName).val(user.id)
                        .appendTo('#technician');
                }
            }
        }
    });
    
    $('#allocationBtn').bind('click', function() {
        $.OrderInfo.allocate({
            data : {
                id : modifyQue.id,
                modifierId : $('#technician').val()
            },
            success : function(data) {
                if(data.code == '000000') {
                    $('#allocationBtn').attr('disabled', 'disabled');
                }
            }
        });
    });
    
    getModifyQueues();
})(jQuery);