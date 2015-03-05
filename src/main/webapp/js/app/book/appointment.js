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
            $('#title').text(SUZUKI_SIAGA_LEBIH_MENGERTI_KELUARGA);
            $('#newBtn').text(NEW_APPOINTMENT).attr('title', NEW_APPOINTMENT);
            $('#settingBtn').text(SETTING_PARAMETER).attr('title', SETTING_PARAMETER);
            $('#serviceTypeLabel').text(SERVICE_TYPE);
            $('#manpowerLabel').text(MANPOWER_ALLOCATION);
            $('#expressMainLabel').text(EXPRESS_MAINTENANCE);
            $('#regServiceLabel').text(REGULAR_SERVICE);
        });
    }
    
    $('#newBtn').bind("click", function(){
        location.href = 'new_appointment.html';
    });
    
    $('#settingBtn').bind("click", function(){
        location.href = 'appointment_parameter.html';
    });
    
    $("#dateDiv").datepicker({
        inline: true,
        showMonthAfterYear: true,
        changeMonth: true,
        changeYear: true,
        buttonImageOnly: true,
        dateFormat: 'yy-mm-dd',
    });
    
    function getTimeStr(date) {
        var hours = date.hour(); 
        var minutes = date.minute();
        return (hours < 10 ? '0' : '') + hours + ':' + 
            (minutes < 10 ? '0' : '') + minutes;
    }
    
    function onDayClick(date, allDay, jsEvent, view) {
        $.cookie('selectedDate', '' + date.year() + '-' + (date.month() + 1) + '-'+ date.date());
        $.cookie('selectedTime', getTimeStr(date));
        location.href = 'new_appointment.html';
    }
    
    $('#calendarExpress').fullCalendar({
        header:{    
            center: 'title',  
            left: 'agendaDay,agendaWeek,month',
            right: ''
        },  
        theme: true,  
        editable: false,  
        allDaySlot : false,
        minTime : '07:00:00',
        maxTime : '18:00:00',
        events:  function(start, end, timezone, callback){  
            var events = [];  
            
            var now = new Date();  
            for(var i=-10;i<60;i++){  
                var evtstart = new Date(now.getFullYear() , now.getMonth() , (now.getDate()-i), now.getHours()-3, now.getMinutes(), now.getSeconds(), now.getMilliseconds());  
                var evtend = new Date(now.getFullYear() , now.getMonth() , (now.getDate()-i), now.getHours(), now.getMinutes(), now.getSeconds(), now.getMilliseconds());               
                events.push({  
                    sid: 1,  
                    uid: 1,  
                    title: 'Daily Scrum meeting',  
                    start: evtstart,  
                    end: evtend,  
                    fullname: 'terry li',  
                    confname: 'Meeting 1',  
                    confshortname: 'M1',  
                    confcolor: '#ff3f3f',  
                    confid: 'test1',  
                    allDay: false,  
                    topic: 'Daily Scrum meeting',  
                    description : 'Daily Scrum meeting',  
                    id: 1,  
                    });         
            } 
             
            callback(events);  
        },
        dayClick: onDayClick        
    });
    
    $('#calendarReguler').fullCalendar({
        header:{  
            left: 'agendaDay,agendaWeek,month',
            right: 'prev,next today',
            center: 'title'
            },  
        theme: true,  
        editable: false,  
        allDaySlot : false,
        minTime : '07:00:00',
        maxTime : '18:00:00',
        events:  function(start, end, timezone, callback){  
            var events = [];
            $.OrderInfo.getBookedOrderListByDate({
                data : {
                    assignDate: getDateString(start),
                    isBook: 1
                },
                success : function(orders) {
                    for(var i = 0; i < orders.length; i++) {
                        
                    }
                }
            });
            
            /*var now = new Date();  
            for(var i=-10;i<60;i++){  
                var evtstart = new Date(now.getFullYear() , now.getMonth() , (now.getDate()-i), now.getHours()-3, now.getMinutes(), now.getSeconds(), now.getMilliseconds());  
                var evtend = new Date(now.getFullYear() , now.getMonth() , (now.getDate()-i), now.getHours(), now.getMinutes(), now.getSeconds(), now.getMilliseconds());               
                events.push({  
                    sid: 1,  
                    uid: 1,  
                    title: 'Daily Scrum meeting',  
                    start: evtstart,  
                    end: evtend,  
                    fullname: 'terry li',  
                    confname: 'Meeting 1',  
                    confshortname: 'M1',  
                    confcolor: '#ff3f3f',  
                    confid: 'test1',  
                    allDay: false,  
                    topic: 'Daily Scrum meeting',  
                    description : 'Daily Scrum meeting',  
                    id: 1,  
                    });         
            }*/
             
            //callback(events);  
        },
        dayClick: onDayClick
    });
    $('#calendarReguler .fc-left').css("visibility","hidden");
    $('#calendarReguler .fc-center').css("visibility","hidden");
    var express = $('<label></label>').attr('id', 'expressMainLabel').css('width', '500px');
    $('#calendarExpress .fc-view-container').before(express);
    var reguler = $('<label></label>').attr('id', 'regServiceLabel').css('width', '500px');
    $('#calendarReguler .fc-view-container').before(reguler);
    
    $('#calendarExpress .fc-left .fc-agendaDay-button').bind('click', function() {
        $('#calendarExpress .fc-axis').css('width', '50px');
        $('#calendarReguler').fullCalendar('changeView', 'agendaDay');
        $('#calendarReguler .fc-axis').hide();
    });
    
    $('#calendarExpress .fc-left .fc-agendaWeek-button').bind('click', function() {
        $('#calendarExpress .fc-axis').css('width', '50px');
        $('#calendarReguler').fullCalendar('changeView', 'agendaWeek');
        $('#calendarReguler .fc-axis').hide();
    });
    
    $('#calendarExpress .fc-left .fc-month-button').bind('click', function() {
        $('#calendarReguler').fullCalendar('changeView', 'month');
    });
    
    $('#calendarReguler .fc-right .fc-prev-button').bind('click', function() {        
        $('#calendarExpress').fullCalendar('prev');
        $('#calendarExpress .fc-axis').css('width', '50px');
        $('#calendarReguler .fc-axis').hide();
    });
    
    $('#calendarReguler .fc-right .fc-next-button').bind('click', function() {        
        $('#calendarExpress').fullCalendar('next');
        $('#calendarExpress .fc-axis').css('width', '50px');
        $('#calendarReguler .fc-axis').hide();
    });
    
    $('#calendarReguler .fc-right .fc-today-button').bind('click', function() { 
        $('#calendarReguler .fc-axis').hide();
        $('#calendarExpress').fullCalendar('today');
        $('#calendarExpress .fc-axis').css('width', '50px');        
    });
    
    function getDateString(date) {
        return '' + date.year() + '-' + (date.month() + 1) + '-' + date.date();
    }
    
})(jQuery);