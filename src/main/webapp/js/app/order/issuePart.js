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
            $('#title').text(ISSUE_PARTS);
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
            $('#startBtn').text(START).attr('title', START);
            $('#holdBtn').text(HOLD).attr('title', HOLD);
            $('#finishBtn').text(FINISH).attr('title', FINISH);
        });
    }
    
    var issueQue;
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
        $('#regNo').val(issueQue && issueQue.order ? issueQue.order.registerNum : '');
        $('#idMechanic').val(issueQue && issueQue.modifier ? issueQue.modifier.userName : '');
        $('#roofNo').val(issueQue && issueQue.order ? issueQue.order.roofNum : '');
        $('#serviceAdvisor').val(issueQue && issueQue.user ? issueQue.user.userName : '');
        $('#jobType').val(issueQue ? jobTypeMapping(issueQue.jobType) + ' - ' + issueQue.jobtypeTime + ' hour(s)' : '');
        $('#isWarranty').prop('checked', issueQue && issueQue.isWarrant ? true : false);
        $('#isSubContract').prop('checked', issueQue && issueQue.isSubContract ? true : false);
        
        if(issueQue) {
            switch(issueQue.step) {
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
        getIssueQueue($('#orderId').val());
    });
    $('#orderId').keyup(function(event) {
        var myEvent = event || window.event;
        var keyCode = myEvent.keyCode;
        if(keyCode == 13){ //Enter
            getIssueQueue($('#orderId').val());
        }
    });
    
    $('#startBtn').bind('click', function() {
        if(!issueQue || issueQue.step !== 1) {
            return;
        }
        $.OrderInfo.mStart({
            data : {
                id : issueQue.id
            },
            success : function(data) {
                if(data.code == '000000') {
                    //$('#inProgress').text(IN_PROGRESS);
                    getIssueQueue(issueQue.id);
                }
            }
        });
    });    
    $('#holdBtn').bind('click', function() {
        if(!issueQue || issueQue.step !== 1) {
            return;
        }
        $.OrderInfo.mHold({
            data : {
                id : issueQue.id
            },
            success : function(data) {
                if(data.code == '000000') {
                    //$('#inProgress').text(ON_HOLD);
                    getIssueQueue(issueQue.id);
                }
            }
        });
    });
    $('#finishBtn').bind('click', function() {
        if(!issueQue || (issueQue.step !== 2 && issueQue.step !== 3)) {
            return;
        }
        $.OrderInfo.mPreapprove({
            data : {
                id : issueQue.id
            },
            success : function(data) {
                if(data.code == '000000') {
                    //$('#inProgress').text(FINISHED);
                    getIssueQueue(issueQue.id);
                }
            }
        });
    });
    
})(jQuery);