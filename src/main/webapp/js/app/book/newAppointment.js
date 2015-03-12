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
            $('#promiseTimeLabel').text(PROMISE_TIME);    
            $('#groupNoLabel').text(GROUP_NO);
        });
    }
    
    $.UserInfo.getProperty({
        data : {
            name : 'BOOKING_GROUP_NO'
        },
        success : function(data) {
            if (data.code == '000000') {
                var num = data.obj.value;
                for (var i = 0; i < num; i++) {
                    $("<option></option>").val(i + 1).text(i + 1).appendTo($('#groupNo'));
                }
            }
        }
    });
    
    var defaultDate, defaultJobType, update = false;
    function initialize() {
        var order = $.cookie('currOrder');
        if(order) {
            update = true;
            order =  JSON.parse(order);
            defaultDate = order.assignDate;
            $('#groupNo').val(order.groupid);
            $('#policeNo').val(order.registerNum);
            $('#policeNo').attr('disabled', 'disabled');
            $('#customer').val(order.userName);
            $('#contact').val(order.mobilePhone);
            $('#remarks').val(order.comment);
            defaultJobType =  order.jobType ? order.jobType : (CONST_EXPRESS + order.express);
            $('#promiseTime').val(order.bookStartTime);
        }
        else {
            if($.cookie('selectedGroup')) {
                $('#groupNo').val($.cookie('selectedGroup'));
            }
            if($.cookie('selectedDate')) {
                defaultDate = $.cookie('selectedDate');
            }
            else {
                defaultDate = new Date();
            }
            if($.cookie('selectedTime')) {
                $('#promiseTime').val($.cookie('selectedTime'));
            }
        }
        
        $("#dateDiv").datepicker({
            inline: true,
            showMonthAfterYear: true,
            changeMonth: true,
            changeYear: true,
            buttonImageOnly: true,
            dateFormat: 'yy-mm-dd',
            defaultDate: defaultDate
        });
    }
    
    var getJobTypeList = function() {
        $.OrderInfo.getJobTypes({
            success : createJobTypeList,
            error : createJobTypeList
        });
    };
    
    var jobTypeSelected = null;
    var createJobTypeList = function(jobTypes) {
        for(var i = 0; i < jobTypes.length; i++) {
            var div, jobTypeName, jobType = jobTypes[i];
            if(jobType.name.indexOf(CONST_EXPRESS) > -1) {
                jobTypeName = jobType.name.substring(CONST_EXPRESS.length);
                div = $('#expressMaintenance');
            }
            else {
                jobTypeName = jobType.name;
                div = $('#regularService');
            }
            $('<input></input>').attr('type', 'checkbox')
                .val(jobType.name).attr('class', 'jobTypeCkb').attr('checked', jobType.name === defaultJobType)
                .appendTo(div)
                .bind('click', function() {
                    if($(this).is(':checked')) {
                        jobTypeSelected = $(this).val();
                        $('#serviceTypeErrMsg').html('').hide('normal');
                        var ckbs = $('.jobTypeCkb');
                        for(var j = 0; j < ckbs.length; j++) {
                            if($(ckbs[j]).val() !== jobTypeSelected) {
                                $(ckbs[j]).attr('checked', false);
                            }
                        }  
                        //$('#otherSelection').attr('checked', false);
                    }
                    else {
                        jobTypeSelected = null;
                    }
                });
            $('<span></span>').attr('style', 'display:inline-block;width:100px;text-align:left;padding-left:10px')
                .text(jobTypeName).appendTo(div);
        }
        /*var box = $('#regularServiceBox');
        $('<input></input>').attr('type', 'checkbox')
            .attr('id', 'otherSelection')
            .attr('style', 'margin-left:10px')
            .bind('click', function() {
                if($(this).is(':checked')) {
                    jobTypeSelected = null;
                    var ckbs = $('.jobTypeCkb');
                    for(var j = 0; j < ckbs.length; j++) {
                        $(ckbs[j]).attr('checked', false);
                    }
                }
            })
            .appendTo(box);
        $('<span></span>').attr('style', 'display:inline-block;text-align:left;padding-left:10px')
            .text(OTHERS).appendTo(box);
        $('<input></input>').attr('type', 'text')
            .attr('style', 'width:80px;margin-left:10px')
            .appendTo(box)
            .keyup(function(event) {
                var myEvent = event || window.event;
                var keyCode = myEvent.keyCode;
                if(keyCode == 13){ //Enter
                    var dom = $('#remarks').get(0);
                    dom.focus();
                }
            });
        $('<span></span>').attr('style', 'display:inline-block;font-style:italic;text-align:left;padding-left:10px')
            .text('(' + INPUT_EST_TIME + ')').appendTo(box);*/
    };
    
    $('#saveBtn').bind('click', function() {
        var policeNo = validatePoliceNo();
        var promiseTime = validatePromiseTime();
        var serviceType = validateServiceType();
        var customer = validateCustomer();
        if(!(policeNo && promiseTime && serviceType && customer)) {
            return;
        }
        $.cookie('currOrder', '', {expires: -1});
        $.cookie('selectedDate', '', {expires: -1});
        $.cookie('selectedTime', '', {expires: -1});
        $.cookie('selectedGroup', '', {expires: -1});
        var startTime = getDateString($('#dateDiv').datepicker('getDate')) + " " + 
            $('#promiseTime').val() + ":00";
        var order = {
                registerNum : $('#policeNo').val(),
                userName : $('#customer').val(),
                mobilePhone : $('#contact').val(),
                assignDate : startTime,
                bookStartTime : startTime,
                groupid : $('#groupNo').val(),
                comment : $('#remarks').val()
        };
        if(jobTypeSelected.indexOf(CONST_EXPRESS) > -1) {
            order.express = jobTypeSelected.substring(CONST_EXPRESS.length);
            delete order.jobType;
        }
        else {
            order.jobType = jobTypeSelected;
            delete order.express;
        }
        if(update) {
            $.OrderInfo.update({
                data : order,
                success : function(data) {
                    if (data.code == '000000') {
                        $('#errMsg').html('').hide('normal');
                        location.href = 'appointment_index.html';
                    }
                    else {
                        $('#errMsg').html('System error, failed to save the appointment!').show('normal');
                    }
                }
            });            
        } else {
            $.OrderInfo.book({
                data : order,
                success : function(data) {
                    if (data.code == '000000') {
                        $('#errMsg').html('').hide('normal');
                        location.href = 'appointment_index.html';
                    }
                    else if(data.code == '010301') {
                        $('#errMsg').html("This customer has already booked today, please don't book again!").show('normal');
                    }  
                    else {
                        $('#errMsg').html('System error, failed to save the appointment!').show('normal');
                    }
                }
            });
        }        
    });
    
    
    initialize();
    getJobTypeList();
    
    function validatePoliceNo() {
        var policeNo = $('#policeNo').val();
        var flag;
        if(policeNo.length > 6) {
            $('#policeNoErrMsg').html('').hide('normal');
            $('#policeNo').css('border', '1px solid #CCC');
            flag = true;
        }
        else {
            $('#policeNoErrMsg').html('The polic No. must be at least 7 characters or numbers!').show('normal');
            $('#policeNo').css('border', '1px solid #F00');
            flag = false;
        }
        return flag;
    }
    
    $('#policeNo').bind("blur", function() {
        validatePoliceNo();
    });
    $('#policeNo').bind("focus", function() {
        $('#errMsg').html('').hide('normal');
        $('#policeNoErrMsg').html('').hide('normal');
        $('#policeNo').css('border', '1px solid #CCC');
    });
    $('#policeNo').keyup(function(event) {
        var myEvent = event || window.event;
        var keyCode = myEvent.keyCode;
        if(keyCode == 13){ //Enter
            var dom = $('#customer').get(0);
            dom.focus();
        }
    });
    
    function validateCustomer() {
        var customer = $('#customer').val();
        var flag;
        if(customer) {
            $('#customerErrMsg').html('').hide('normal');
            $('#customer').css('border', '1px solid #CCC');
            flag = true;
        }
        else {
            $('#customerErrMsg').html('Please input the customer!').show('normal');
            $('#customer').css('border', '1px solid #F00');
            flag = false;
        }
        return flag;
    }
    
    $('#customer').bind("blur", function() {
        validateCustomer();
    });
    $('#customer').bind("focus", function() {
        $('#errMsg').html('').hide('normal');
        $('#customerErrMsg').html('').hide('normal');
        $('#customer').css('border', '1px solid #CCC');
    });
    $('#customer').keyup(function(event) {
        var myEvent = event || window.event;
        var keyCode = myEvent.keyCode;
        if(keyCode == 13){ //Enter
            var dom = $('#contact').get(0);
            dom.focus();
            
        }
    });
    
    $('#contact').keyup(function(event) {
        var myEvent = event || window.event;
        var keyCode = myEvent.keyCode;
        if(keyCode == 13){ //Enter
            var dom = $('#promiseTime').get(0);
            dom.focus();
        }
    });
    
    function validatePromiseTime() {
        var promiseTime = $('#promiseTime').val();
        var flag;
        if(promiseTime) {
            $('#promiseTimeNoErrMsg').html('').hide('normal');
            $('#promiseTime').css('border', '1px solid #CCC');
            flag = true;
        }
        else {
            $('#promiseTimeErrMsg').html('Please select the promise time!').show('normal');
            $('#promiseTime').css('border', '1px solid #F00');
            flag = false;
        }
        return flag;
    }
    $('#promiseTime').bind("blur", function() {
        validatePromiseTime();
    });
    $('#promiseTime').bind("focus", function() {
        $('#errMsg').html('').hide('normal');
        $('#promiseTimeErrMsg').html('').hide('normal');
        $('#promiseTime').css('border', '1px solid #CCC');
    });
    
    function validateServiceType() {
        var flag = false, ckbs = $('.jobTypeCkb');
        for(var j = 0; j < ckbs.length; j++) {
            if($(ckbs[j]).is(':checked')) {
                flag = true;
                jobTypeSelected = $(ckbs[j]).val();
                $('#serviceTypeErrMsg').html('').hide('normal');
                break;
            }
        }
        if(!flag) {
            $('#serviceTypeErrMsg').html('Please select the service type!').show('normal');
        }
        return flag;
    }
    
})(jQuery);