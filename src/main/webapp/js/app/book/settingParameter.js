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
            $('#legendTitle').text(SETTING_PARAMETER);
            $('#manpowerLabel').text(MANPOWER);
            $('#servicesRateLabel').text(SERVICES_RATE_TIME);
            $('#manpowerAllocLabel').text(MANPOWER_ALLOCATION);
            $('#intakeCtrlLabel').text(INTAKE_CONTROL);
        });
    }
    
    $("#dateDiv").datepicker({
        inline: true,
        showMonthAfterYear: true,
        changeMonth: true,
        changeYear: true,
        buttonImageOnly: true,
        dateFormat: 'yy-mm-dd',
    });
    
    var getJobTypeList = function() {
        $.OrderInfo.getJobTypes({
            success : createJobTypeList,
            error : createJobTypeList
        });
    };
    
    var jobTypeSelected = null;
    var createJobTypeList = function(jobTypes) {
        var table = $('#servicesRateList');
        var th = $('<tr></tr>').appendTo(table);
        $('<th></th>').text(SERVICE_TYPE).appendTo(th);
        var tr = $('<tr></tr>').appendTo(table);
        $('<td></td>').text(NO_OF_HOURS).appendTo(tr);
        for(var i = 0; i < jobTypes.length; i++) {
            var jobType = jobTypes[i];
            $('<th></th>').text(jobType.name).appendTo(th);
            $('<td></td>').attr('id', 'td_' + jobType.id + '_' + jobType.name).text(jobType.value).appendTo(tr)
                .attr('style', 'min-width:40px')
                .bind('click', onClick);
        }
        
        function onClick() {
            var val = $(this).text();
            $(this).empty();
            var input = $('<input></input>').attr('type', 'text').attr('style', 'width:35px')
                .val(val).appendTo($(this));
            input.blur(onSave);
            input.keyup(function(event) {
                var td = $(this).parent('td');
                var myEvent = event || window.event;
                var keyCode = myEvent.keyCode;
                if(keyCode == 27){ //ESC
                    td.html(val);
                    td.bind('click', onClick);
                }
                else if(keyCode == 13){ //Enter
                    onSave.call(this);
                }
            });
            var dom = input.get(0);
            dom.select();
            $(this).unbind('click');
        }
        
        function onSave() {
            var td = $(this).parent('td');
            var id = td.attr('id').split('_')[1];
            var name = td.attr('id').split('_')[2];
            var value = $(this).val();
            td.html($(this).val());
            td.bind('click', onClick);
            /*
             * $.OrderInfo.modifyJobType({ data : { id : id, name : name,
             * value : value }, success : function(data) { td.html(value);
             * td.bind('click', onClick); } });
             */
        }
    };
    
//    $('#saveBtn').bind('click', function() {
//        $.OrderInfo.book({
//            data : {
//                registerNum : $('#policeNo').val(),
//                userName : $('#customer').val(),
//                mobilePhone : $('#contact').val(),
//                jobType : jobTypeSelected,
//                assignDate : $('#dateDiv').datepicker('getDate').getTime()
//            },
//            success : function(data) {
//                if (data.code == '000000') {
//                    location.href = 'appointment_index.html';
//                }
//            }
//        });
//    });
    
    getJobTypeList();
    
})(jQuery);