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
            $('#title').text(FINAL_INSPECTION);
            $('#legendTitle').text(INPUT_EDIT_DATA);
            $('#regNoLabel').text(REG_NO);
            $('#idMechanicLabel').text(ID_MECHANIC);
            $('#roofNoLabel').text(ROOF_NO);
            $('#serviceAdvisorLabel').text(SERVICE_ADVISOR);
            $('#jobTypeLabel').text(JOB_TYPE);
            $('#rWarranty').text(WARRANTY);
            $('#rSubContract').text(SUB_CONTRACT);
            $('#closeBtn').text(CLOSE).attr('title', CLOSE);
        });
    }
    
    /*var modifyQue;
    $.OrderInfo.getModifyQueue({
        data : {
            id : 3
        },
        success : function(data) {
            if(data.code == '000000') {
                modifyQue = data.obj;
                $('#regNo').val(modifyQue && modifyQue.order ? modifyQue.order.registerNum : '');
                //$('#idMechanic').text(modifyQue && modifyQue.order ? modifyQue.order.registerNum : '');
                $('#roofNo').val(modifyQue && modifyQue.order ? modifyQue.order.roofNum : '');
                //$('#serviceAdvisor').text(modifyQue && modifyQue.order ? modifyQue.order.roofNum : '');
                $('#jobType').val(modifyQue ? modifyQue.jobType : '');
                $('#isWarranty').attr('checked', modifyQue && modifyQue.isWarrant ? true : false);
                $('#isSubContract').attr('checked', modifyQue && modifyQue.isSubContract ? true : false);
            }
        }
    });*/
    
    /*$('#finishBtn').bind('click', function() {
        $.OrderInfo.mFinish({
            data : {
                id : modifyQue.id
            },
            success : function(data) {
                if(data.code == '000000') {
                    $('#inProgress').text(FINISHED);
                }
            }
        });
    });*/
})(jQuery);