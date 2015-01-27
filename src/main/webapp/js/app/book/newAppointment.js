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
            //$('#changePwd').text(CHANGE_PASSW0RD).attr('title', CHANGE_PASSW0RD);
            //$('#logout').text(LOGOUT).attr('title', LOGOUT);
            $('#title').text(SUZUKI_SIAGA_LEBIH_MENGERTI_KELUARGA);
            $('#legendTitle').text(NEW_APPOINTMENT);
            $('#policeNoLabel').text(POLICE_NO);
            $('#customerLabel').text(CUSTOMER);
            $('#contactLabel').text(CONTACT);
            $('#serviceTypeLabel').text(SERVICE_TYPE);
            $('#remarksLabel').text(REMARKS);
            $('#regularServiceLabel').text(REGULAR_SERVICE);
            $('#expressMaintenanceLabel').text(EXPRESS_MAINTENANCE);
            $('#saveBtn').text(SAVE).attr('title', SAVE);
        });
    }
    
    var getJobTypeList = function() {
        $.OrderInfo.getJobTypes({
            success : createJobTypeList,
            error : createJobTypeList
        });
    };
    
    var createJobTypeList = function(jobTypes) {
        for(var i = 0; i < jobTypes.length; i++) {
            var jobType = jobTypes[i];
            var div = $('#regularService');
            $('<input></input>').attr('type', 'checkbox')
                .val(jobType.name).appendTo(div);
            $('<span></span>').attr('style', 'display:inline-block;width:100px;text-align:left;padding-left:10px')
                .text(jobType.name).appendTo(div);
        }
        var box = $('#regularServiceBox');
        $('<input></input>').attr('type', 'checkbox')
            .attr('style', 'margin-left:10px')
            .appendTo(box);
        $('<span></span>').attr('style', 'display:inline-block;text-align:left;padding-left:10px')
            .text(OTHERS).appendTo(box);
        $('<input></input>').attr('type', 'text')
            .attr('style', 'width:80px;margin-left:10px')
            .appendTo(box);
        $('<span></span>').attr('style', 'display:inline-block;font-style:italic;text-align:left;padding-left:10px')
            .text('(' + INPUT_EST_TIME + ')').appendTo(box);
    };
    
    getJobTypeList();
    
})(jQuery);