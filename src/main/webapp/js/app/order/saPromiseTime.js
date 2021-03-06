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
            $('#title').text(SA_QUE_CALLING_BOARD);            
            $('#legendTitle').text(INPUT_EDIT_DATA);
            $('#regNoLabel').text(REG_NO);
            $('#additionTimeLabel').text(ADDITION_TIME);
            $('#roofNoLabel').text(ROOF_NO);
            $('#serviceAdvisorLabel').text(SERVICE_ADVISOR);
            $('#jobTypeLabel').text(JOB_TYPE);
            $('#rWarranty').text(WARRANTY);
            $('#rSubContract').text(SUB_CONTRACT);
            $('#promiseTimeRefLabel').text(ESTIMATE_PROMISE_TIME + ' - ' + REFERENCE);
            $('#promiseTimeLabel').text(ESTIMATE_PROMISE_TIME);
            $('#finishBtn').text(FINISH).attr('title', FINISH);
            $('#backBtn').text(BACK).attr('title', BACK);
            
            $.UserInfo.checkLogin({
                success : function(data) {
                    if (data.code == '000000' && data.obj) {
                        userProfile = data.obj;
                        $("#helloUserName").text(
                                HELLO + ' ' + (userProfile ? userProfile.userName : ''));
                        $('#userName').text(userProfile ? userProfile.userName : ''); 
                        $('#serviceAdvisor').val(userProfile ? userProfile.userName : '');
                    }
                }
            });
        });
    }

    var userProfile;
       
    var getWorkload = function() {
        $.OrderInfo.getTodayModifyQueue({
            success : refreshWorkload
        });
    }
    
    var color = ['red', 'green', 'blue'];
    var refreshWorkload = function(workloads) {
        $('#timeRefTable tr.odd').remove();
        $('#timeRefTable tr.even').remove();
        for(var i = 0; i < workloads.length; i++) {
            var workload = workloads[i];
            var tr = $('<tr></tr>').attr('class', i % 2 === 0 ? 'odd' : 'even')
                .appendTo($('#timeRefTable'));
            $('<td></td>').attr('class', 'header').text(workload.username).appendTo(tr);
            var queues = workload.modifyQueues;
            for(var j = 0, k = 0, c = 0; j < queues.length; j++) {
                var queue = queues[j];
                var start = new Date(queue.assignTime);
                var h = start.getHours(), m = start.getMinutes(), l = Math.ceil(queue.load / 0.5);
                if(h < 7) {
                    continue;
                }
                var n = (h - 7) * 2 + (m < 30 ? 0 : 1);
                var td;
                if(n >= k) {
                    for(var m = 0; m < n - k; m++) {
                        $('<td></td>').html('&nbsp;')
                            .appendTo(tr);
                    }
                    td = $('<td></td>').attr('colSpan', l)
                            .appendTo(tr);
                    k = n + l;
                }
                else {
                    continue;
                }
                if(td) {
                    $('<div></div>').attr('class', 'timeslot ' + color[c++]).text(jobTypeMapping(queue.jobType)).attr('title', queue.registerNum).appendTo(td);
                    if(c >= color.length) {
                        c = 0;
                    }
                }
            }
            for(; k < 22; k ++) {
                $('<td></td>').html('&nbsp;')
                    .appendTo(tr);
            }
        }
        for(; i < 5; i++) {
            var tr = $('<tr></tr>').attr('class', i % 2 === 0 ? 'odd' : 'even')
                .appendTo($('#timeRefTable'));
            $('<td></td>').attr('class', 'header').html('&nbsp;').appendTo(tr);
            for(k=0; k < 22; k ++) {
                $('<td></td>').html('&nbsp;')
                    .appendTo(tr);
            }
        }
    }
    
    var getJobType = function() {
        $.OrderInfo.getJobTypes({
            success : createJobTypeList
        });
        
        function createJobTypeList(jobTypes) {
            var selectedType = $.cookie('jobType');
            for(var i = 0; i < jobTypes.length; i++) {
                var jobType = jobTypes[i];
                var sel = $('#jobType');
                $('<option></option>').val(jobType.name).text(jobTypeMapping(jobType.name) + ' - ' + jobType.value + ' hour(s)')
                    .appendTo(sel);
                if(selectedType === jobType.name) {
                    sel.val(jobType.name);
                }
            }
        }
    }
    
    var serveId = $.cookie('serveId');
    $('#regNo').val($.cookie('registerNum'));
    
    $('#roofNo').keyup(function() {
        var disabled = false;
        if($('#roofNo').val().length == 0) {
            disabled = 'disabled';
        }
        else if($('#regNo').val().length == 0) {
            disabled = 'disabled';
        }
        else if($('#serviceAdvisor').val().length == 0) {
            disabled = 'disabled';
        }
        else if($('#jobType').val().length == 0) {
            disabled = 'disabled';
        }
        $('#finishBtn').attr('disabled', disabled);
    });
    
    // send
    $('#finishBtn').bind('click', function() {
        $.OrderInfo.send({
            data : {
                id : serveId,
                roofNum : $('#roofNo').val(),
                jobType : $('#jobType').val(),
                additionTime : $('#additionTime').val(),
                hour : $('#promiseTime').val().split(':')[0],
                minute : $('#promiseTime').val().split(':')[1],
                isWarrant : $('#isWarranty').is(':checked') ? 1 : 0,
                isSubContract : $('#isSubContract').is(':checked') ? 1 : 0
            },
            success : function(data) {
                if (data.code == '000000') {
                    $.cookie('serveId', '', {expires: -1});
                    $.cookie('registerNum', '', {expires: -1});
                    $.cookie('timerStartTime', '', {expires: -1});
                    $.cookie('jobType', '', {expires: -1});
                    location.href = 'sa_que.html';
                }
            }
        });
    });
    $('#backBtn').bind('click', function() {
        location.href = 'sa_que.html';
    });
    
    getWorkload();
    getJobType();
})(jQuery);
