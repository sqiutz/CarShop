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
        });
    }

    var userProfile;
    $.UserInfo.checkLogin({
        success : function(data) {
            if (data.code == '000000' && data.obj) {
                userProfile = data.obj;
                $("#helloUserName").text(
                        'Hello ' + (userProfile ? userProfile.userName : ''));
                $('#userName').text(userProfile ? userProfile.userName : ''); 
                $('#serviceAdvisor').val(userProfile ? userProfile.userName : '');
            }
        }
    });
    
    var getWorkload = function() {
        var today = new Date();
        $.OrderInfo.getWorkload({
            data : {
                today : today.toLocaleDateString()
            },
            success : refreshWorkload
        });
    }
    
    var refreshWorkload = function(workload) {
        var w = workload;
    }
    
    var getJobType = function() {
        $.OrderInfo.getJobTypes({
            success : createJobTypeList
        });
        
        function createJobTypeList(jobTypes) {
            for(var i = 0; i < jobTypes.length; i++) {
                var jobType = jobTypes[i];
                var sel = $('#jobType');
                $('<option></option>').val(jobType.name).text(jobType.name + ' (' + jobType.value + ' mins)')
                    .appendTo(sel);
            }
        }
    }
    
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
        else if($('#additionTime').val().length == 0) {
            disabled = 'disabled';
        }
        $('#finishBtn').attr('disabled', disabled);
    });
    
    // send
    $('#finishBtn').bind('click', function() {
        
    });
    $('#backBtn').bind('click', function() {
        location.href = 'sa_que.html';
    });
    
    getWorkload();
    getJobType();
})(jQuery);