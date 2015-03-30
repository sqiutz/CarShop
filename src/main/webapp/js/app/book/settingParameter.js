(function($) {    
    $.UserInfo.getProperty({
        data : {
            name : 'LANGUAGE'
        },
        success : function(data) {
            if (data.code == '000000') {
                var langCode = data.obj.value;
                applyLang(langCode);
            }
            else {
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
            //$('#changePwd').text(CHANGE_PASSW0RD).attr('title', CHANGE_PASSW0RD);
            //$('#logout').text(LOGOUT).attr('title', LOGOUT);
            $('#title').text(SUZUKI_SIAGA_LEBIH_MENGERTI_KELUARGA);
            $('#legendTitle').text(SETTING_PARAMETER);
            $('#manpowerLabel').text(MANPOWER);
            $('#servicesRateLabel').text(SERVICES_RATE_TIME);
            $('#manpowerAllocLabel').text(MANPOWER_ALLOCATION);
            $('#regJobCkbText').text(REGULAR_SERVICE);
            $('#expJobCkbText').text(EXPRESS_MAINTENANCE);
            $('#cancelBtn').text(Cancel).attr('title', Cancel);
            
            getRegJobTypeList();
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
    
    $('#regJobCkb').bind('click', function(){
        if($(this).is(':checked')) {
            $('#expJobCkb').prop('checked', false);
            getRegJobTypeList();
        }
    });
    
    $('#expJobCkb').bind('click', function(){
        if($(this).is(':checked')) {
            $('#regJobCkb').prop('checked', false);
            getExpJobTypeList();
        }
    });
    
    $('#cancelBtn').bind('click', function() {
        location.href = 'appointment_index.html';
    });
    
    var tab = 0;
    var getRegJobTypeList = function() {
        tab = 0;
        $.OrderInfo.getJobTypes({
            success : function(jobTypes) {
                var regJobTyps = [];
                for(var i = 0; i < jobTypes.length; i++) {
                    if(jobTypes[i].name.indexOf(CONST_EXPRESS) < 0) {
                        regJobTyps.push(jobTypes[i]);
                    }
                }
                createJobTypeList(regJobTyps);
            },
            error : createJobTypeList
        });
    };
    var getExpJobTypeList = function() {
        tab = 1;
        $.OrderInfo.getJobTypes({
            success : function(jobTypes) {
                var regJobTyps = [];
                for(var i = 0; i < jobTypes.length; i++) {
                    if(jobTypes[i].name.indexOf(CONST_EXPRESS) > -1) {
                        jobTypes[i].name = jobTypes[i].name.substring(CONST_EXPRESS.length);
                        regJobTyps.push(jobTypes[i]);
                    }
                }
                createJobTypeList(regJobTyps);
            },
            error : createJobTypeList
        });
    };
    
    var jobTypeSelected = null;
    var createJobTypeList = function(jobTypes) {
        $('#servicesRateList tr').remove();
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
            var sel = $('<select></select>').attr('style', 'width:45px').appendTo($(this))
                .bind('change', function() {
                    
                })
                .blur(function() {
                    onSave.call(this, sel.val());
                });
            var v = 0;
            for(var j = 1; j < 21; j++) {
                $('<option></option>').val(j * 0.5).text(j * 0.5).appendTo(sel);
                if(val == j * 0.5) {
                    v = j * 0.5;
                }
            }
            sel.val(v);
            var dom = sel.get(0);
            dom.focus();
            $(this).unbind('click');
        }
        
        function onSave(oValue) {
            var td = $(this).parent('td');
            var id = td.attr('id').split('_')[1];
            var name = td.attr('id').split('_')[2];
            if(1 == tab) {
                name = CONST_EXPRESS + name;
            }
            var value = $(this).val();
            td.bind('click', onClick);
                   
            $.OrderInfo.modifyJobType({ 
                 data : { 
                     id : id, 
                     name : name,
                     value : value 
                 }, 
                 success : function(data) {
                     if(data.code == '000000') {
                         td.html(value);
                     }
                     else {
                         td.html(oValue);
                     }                    
                 } 
             });             
        }
    };
})(jQuery);