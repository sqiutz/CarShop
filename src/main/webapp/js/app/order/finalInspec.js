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
            $('#foremanJob').text(FOREMAN_JOB_DISTRIBUTION_LIST).attr('title', FOREMAN_JOB_DISTRIBUTION_LIST);
            $('#title').text(FINAL_INSPECTION);
            $('#legendTitle').text(INPUT_EDIT_DATA);
            $('#regNoLabel').text(REG_NO);
            $('#idMechanicLabel').text(ID_MECHANIC);
            $('#roofNoLabel').text(ROOF_NO);
            $('#serviceAdvisorLabel').text(SERVICE_ADVISOR);
            $('#jobTypeLabel').text(JOB_TYPE);
            $('#rWarranty').text(WARRANTY);
            $('#rSubContract').text(SUB_CONTRACT);
            $('#closeBtn').text(FINISH).attr('title', FINISH);
            $('#orderIdLabel').text(ID);
            $('#holdBtn').text(HOLD).attr('title', HOLD);
            
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
    
    var modifyQue;
    function getModifyQueue(id) {
        if(!id) {
            setModifyQueue();
            return;
        }
        $.OrderInfo.getModifyQueue({
            data : {
                id : id
            },
            success : function(data) {
                if(data.code == '000000') {
                    modifyQue = data.obj;                    
                }
                else {
                    modifyQue = null;
                }
                setModifyQueue(modifyQue);
            }
        });
    } 
    
    function setModifyQueue(modifyQue) {
        $('#regNo').val(modifyQue && modifyQue.order ? modifyQue.order.registerNum : '');
        $('#idMechanic').val(modifyQue && modifyQue.modifier ? modifyQue.modifier.userName : '');
        $('#roofNo').val(modifyQue && modifyQue.order ? modifyQue.order.roofNum : '');
        $('#serviceAdvisor').val(modifyQue && modifyQue.user ? modifyQue.user.userName : '');
        $('#jobType').val(modifyQue ? jobTypeMapping(modifyQue.jobType) + ' - ' + modifyQue.jobtypeTime + ' hour(s)' : '');
        $('#isWarranty').prop('checked', modifyQue && modifyQue.isWarrant ? true : false);
        $('#isSubContract').prop('checked', modifyQue && modifyQue.isSubContract ? true : false);

        if(modifyQue) {
            switch(modifyQue.step) {
                case 4:
                    //$('#holdBtn').show();
                    $('#finalInspec').text('');
                    break;
                case 5:
                    //$('#holdBtn').hide();
                    $('#finalInspec').text(ON_HOLD);
                    break;
                case 6:
                    //$('#holdBtn').show();
                    $('#finalInspec').text(FINISHED);
                    break;
                default:
                    $('#finalInspec').text('');
                    break;
            }
        }
        else {
            $('#finalInspec').text('');
        }
    }
        
    $('#orderId').bind("blur", function() {
        getModifyQueue($('#orderId').val());
    });
    $('#orderId').keyup(function(event) {
        var myEvent = event || window.event;
        var keyCode = myEvent.keyCode;
        if(keyCode == 13){ //Enter
            getModifyQueue($('#orderId').val());
        }
    });    
      
    $('#holdBtn').bind('click', function() {
        if(!modifyQue || modifyQue.step !== 4) {
            return;
        }
        $.OrderInfo.mReject({
            data : {
                id : modifyQue.id
            },
            success : function(data) {
                if(data.code == '000000') {
                    //$('#finalInspec').text(ON_HOLD);
                    getModifyQueue(modifyQue.forId);
                }
            }
        });
    });
    $('#closeBtn').bind('click', function() {
        if(!modifyQue || (modifyQue.step !== 4 && modifyQue.step !== 5)) {
            return;
        }
        $.OrderInfo.mFinish({
            data : {
                id : modifyQue.id
            },
            success : function(data) {
                if(data.code == '000000') {
                    $('#holdBtn').show();
                    //$('#finalInspec').text(FINISHED);
                    getModifyQueue(modifyQue.forId);
                }
            }
        });
    });
})(jQuery);