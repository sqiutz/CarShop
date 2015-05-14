(function($) {
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
            $('#forIdLabel').text(FOR_ID);
            $('#technicianCol').text(TECHNICIAN + ':');
            $('#hrTakenCol').text(HR_TAKEN);
            $('#bayNoCol').text(BAY_NO);
            $('#noCol').text(NUMBER);
            $('#wRegNoCol').text(REG_NO);
            $('#roofNoCol').text(ROOF_NO);
            $('#saCol').text(SERVICE_ADVISOR);
            $('#periodicServiceCol').text(PERIODIC_SERVICE);
            $('#hrNeedCol').text(HR_NEED);
            $('#additionalHoursCol').text(ADDITIONAL_HOURS);
            $('#generalRepairCol').text(GENERAL_REPAIR);
            $('#warrantyCol').text(WARRANTY);
            $('#subContractCol').text(SUB_CONTRACT);
            $('#timeAllocatedCol').text(TIEM_ALLOCATED);
            $('#promiseTimeCol').text(PROMISE_TIME);
            $('#startTimeCol').text(START_TIME);
            $('#timeOnHoldCol').text(TIME_ON_HOLD);
            $('#finishTimeCol').text(FINISH_TIME);
            $('#remarksCol').text(REMARKS);
            
            $.UserInfo.checkLogin({
                success : function(data) {
                    if (data.code == '000000' && data.obj) {
                        userProfile = data.obj;
                        $("#helloUserName").text(
                                HELLO + ' ' + (userProfile ? userProfile.userName : ''));
                        $('#userName').text(userProfile ? userProfile.userName : ''); 
                    }
                }
            });
        });
    }
    
    var userProfile;
       
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
        $('#jobType').val(queue ? jobTypeMapping(queue.jobType) + ' - ' + queue.jobtypeTime + ' hour(s)': '');
        $('#additionTime').val(queue ? queue.additionTime + ' hour(s)' : '');
        $('#isWarranty').prop("checked", queue && queue.isWarrant ? true : false);
        $('#isSubContract').prop("checked", queue && queue.isSubContract ? true : false);
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
    
    var allWorkload, index = 0;
    var getAllWorkload = function() {
        $.OrderInfo.getAllWorkload({
            data : {
                name : 3
            },
            success : function(workload) {
                allWorkload = workload;
                $('#chartsDiv div.donut').remove();
                for(var i = 0; i < workload.length; i++) {
                    var load = workload[i];
                    var div = $('<div></div>').attr('class', 'donut').appendTo($('#chartsDiv'));
                    var id = 'chart' + i;
                    $('<div></div>').attr('id', id).attr('class', 'box-black-dark').appendTo(div);
                    $('<div></div>').attr('class', 'cAlign h3').text(load.userName)
                        .val(i)
                        .attr('style', 'cursor:pointer;')
                        .appendTo(div)
                        .bind('click', function() {
                            index = $(this).val();
                            setUserWorkload(index);                            
                        });
                    drawChart(id, load.totalLoadPercentage / 100);
                }
                setUserWorkload(index); 
            } 
        });
    }
    
    function setUserWorkload(i) {
        var load = allWorkload[i];
        getUserWorkload(load.userId);
        $('#techNo').text(1 + i);
        $('#technicianName').text(load.userName);
    }
    
    var colors = ['yellow', 'yellow', 'green', 'green', 'red', 'red'];
    var getUserWorkload = function(userId) {
        $.OrderInfo.getUserWorkload({
            data : {
                id : userId
            },
            success : function(workload) {
                $('#workloadTable tr.yellow').remove();
                $('#workloadTable tr.green').remove();
                $('#workloadTable tr.red').remove();
                var num = workload.length < 6 ? 6 : workload.length;
                for(var i = 0; i < num; i++) {
                    var load = i < workload.length ? workload[i] : null;
                    var tr = $('<tr></tr>').attr('class', colors[i % 6]).appendTo($('#workloadTable'));
                    $('<td></td>').attr('class', 'header').text(i + 1).appendTo(tr);
                    $('<td></td>').text(load && load.order ? load.order.registerNum : '').appendTo(tr);
                    $('<td></td>').text(load && load.order ? load.order.roofNum : '').appendTo(tr);
                    $('<td></td>').text(load && load.sa ? load.sa.userName : '').appendTo(tr);
                    $('<td></td>').text(load && load.modifyQueue ? jobTypeMapping(load.modifyQueue.jobType) : '').appendTo(tr);
                    $('<td></td>').text(load ? load.humanResource : '').appendTo(tr);
                    $('<td></td>').text(load ? load.additionalHours : '').appendTo(tr);
                    $('<td></td>').text(load ? load.generalRepaire : '').appendTo(tr);
                    $('<td></td>').text(load ? (load.isWarrant ? YES : NO) :'').appendTo(tr);
                    $('<td></td>').text(load ? (load.isSubContract ? YES : NO) :'').appendTo(tr);
                    $('<td></td>').text(load ? getTimeStr(load.allocatedTime) : '').appendTo(tr);
                    $('<td></td>').text(load && load.order ? getTimeStr(load.order.promiseTime) : '').appendTo(tr);
                    $('<td></td>').text(load && load.modifyQueue && load.modifyQueue.step >= 2 ? getTimeStr(load.startTime) : '').appendTo(tr);
                    $('<td></td>').text('').appendTo(tr);
                    $('<td></td>').text(load && load.modifyQueue && load.modifyQueue.step == 4 ? getTimeStr(load.endTime) : '').appendTo(tr);
                    $('<td></td>').text(load ? load.comment : '').appendTo(tr);
                }
            } 
        });
    }
    
    $('#allocationBtn').bind('click', function() {
        $.OrderInfo.allocate({
            data : {
                id : modifyQue.id,
                modifierId : $('#technician').val(),
                forId : $('#forId').val()
            },
            success : function(data) {
                if(data.code == '000000') {
                    $('#allocationBtn').attr('disabled', 'disabled');
                    setCurrentModifyQue();
                    getAllWorkload();
                }
            }
        });
        
        $.OrderInfo.iAllocate({
            data : {
                id : modifyQue.id,
                orderId : modifyQue.order.id,
                forId : $('#forId').val()
            },
            success : function(data) {
                if(data.code !== '000000') {
                    console.log(data.code + ' ' + data.msg);
                }
            }
        });
    });
    
    getModifyQueues();
    getAllWorkload();
})(jQuery);