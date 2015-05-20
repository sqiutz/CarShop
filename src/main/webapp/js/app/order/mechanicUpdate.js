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
            $('#title').text(MECHANIC_UPDATE_JOB_PROGRESS);
            $('#legendTitle').text(INPUT_EDIT_DATA);
            $('#regNoLabel').text(REG_NO);
            $('#idMechanicLabel').text(ID_MECHANIC);
            $('#roofNoLabel').text(ROOF_NO);
            $('#serviceAdvisorLabel').text(SERVICE_ADVISOR);
            $('#jobTypeLabel').text(JOB_TYPE);
            $('#orderIdLabel').text(ID);
            $('#rWarranty').text(WARRANTY);
            $('#rSubContract').text(SUB_CONTRACT);
            $('#jobProcess').text(JOB_PROCESS);
            $('#issueProcess').text(ISSUE_PROCESS);
            $('#startBtn').text(START).attr('title', START);
            $('#holdBtn').text(HOLD).attr('title', HOLD);
            $('#iStartBtn').text(START).attr('title', START);
            $('#iHoldBtn').text(HOLD).attr('title', HOLD);
            $('#finishBtn').text(FINISH).attr('title', FINISH);
        });
    }
    
    var modifyQue, issueQue;
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
                case 2:
                    $('#inProgress').text(IN_PROGRESS);
                    break;
                case 3:
                    $('#inProgress').text(ON_HOLD);
                    break;
                case 4:
                case 5:
                case 6:
                    $('#inProgress').text(FINISHED);
                    break;
                default:
                    $('#inProgress').text('');
                    break;
            }
        }
        else {
            $('#inProgress').text('');
        }
    }
        
    $('#orderId').bind("blur", function() {
        getModifyQueue($('#orderId').val());
        getIssueQueue($('#orderId').val());
    });
    $('#orderId').keyup(function(event) {
        var myEvent = event || window.event;
        var keyCode = myEvent.keyCode;
        if(keyCode == 13){ //Enter
            getModifyQueue($('#orderId').val());
            getIssueQueue($('#orderId').val());
        }
    });
    
    $('#startBtn').bind('click', function() {
        if(!modifyQue || modifyQue.step !== 1) {
            return;
        }
        $.OrderInfo.mStart({
            data : {
                id : modifyQue.id
            },
            success : function(data) {
                if(data.code == '000000') {
                    getModifyQueue(modifyQue.forId);
                }
            }
        });
    });    
    $('#holdBtn').bind('click', function() {
        if(!modifyQue || modifyQue.step !== 1) {
            return;
        }
        $.OrderInfo.mHold({
            data : {
                id : modifyQue.id
            },
            success : function(data) {
                if(data.code == '000000') {
                    getModifyQueue(modifyQue.forId);
                }
            }
        });
    });
    $('#finishBtn').bind('click', function() {
        if(!modifyQue || (modifyQue.step !== 2 && modifyQue.step !== 3)) {
            return;
        }
        $.OrderInfo.mPreapprove({
            data : {
                id : modifyQue.id
            },
            success : function(data) {
                if(data.code == '000000') {
                    getModifyQueue(modifyQue.forId);
                }
            }
        });
    });
    
    function getIssueQueue(id) {
        if(!id) {
            setIssueQueue();
            return;
        }
        $.OrderInfo.getIssueQueue({
            data : {
                id : id
            },
            success : function(data) {
                if(data.code == '000000') {
                    issueQue = data.obj;                    
                }
                else {
                    issueQue = null;
                }
                setIssueQueue(issueQue);
            }
        });
    } 
    
    function setIssueQueue(issueQue) {        
        if(issueQue) {
            switch(issueQue.step) {
                case 1:
                    $('#iInProgress').text(IN_PROGRESS);
                    break;
                case 2:
                    $('#iInProgress').text(ON_HOLD);
                    break;
                case 3:
                    $('#iInProgress').text(FINISHED);
                    break;
                default:
                    $('#iInProgress').text('');
                    break;
            }
        }
        else {
            $('#iInProgress').text('');
        }
    }
    
    $('#iStartBtn').bind('click', function() {
        if(!issueQue || (issueQue.step !== 0 && issueQue.step !== 2)) {
            return;
        }
        $.OrderInfo.iStart({
            data : {
                id : issueQue.id
            },
            success : function(data) {
                if(data.code == '000000') {
                    getIssueQueue(issueQue.forId);
                }
            }
        });
    });    
    $('#iHoldBtn').bind('click', function() {
        if(!issueQue || issueQue.step !== 1) {
            return;
        }
        $.OrderInfo.iHold({
            data : {
                id : issueQue.id
            },
            success : function(data) {
                if(data.code == '000000') {
                    getIssueQueue(issueQue.forId);
                }
            }
        });
    });
})(jQuery);