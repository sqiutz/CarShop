(function($) {
    $.cookie('currOrder', '', {expires: -1});
    $.cookie('selectedDate', '', {expires: -1});
    $.cookie('selectedTime', '', {expires: -1});
    $.cookie('selectedGroup', '', {expires: -1});
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
    
    $('#groupNo').change(function() {
        $('#calendarReguler').fullCalendar('refetchEvents');
    });
    
    function onDayClick(date, allDay, jsEvent, view) {
        $.cookie('selectedDate', '' + date.year() + '-' + (date.month() + 1) + '-'+ date.date());
        $.cookie('selectedTime', getTimeStr(date));
        $.cookie('selectedGroup', $('#groupNo').val());
        location.href = 'new_appointment.html';
    }
    
    function onEventClick(calEvent, jsEvent, view) {
        calEvent;
        var currOrder = '{"groupid":' + '"' + calEvent.order.groupid + '",' +
                        '"assignDate":' + '"' + getDateString(new Date(calEvent.order.assignDate))  + '",' +
                        '"registerNum":' + '"' + calEvent.order.registerNum + '",' +
                        '"userName":' + '"' + calEvent.order.userName + '",' +
                        '"mobilePhone":' + '"' + calEvent.order.mobilePhone + '",' +
                        '"bookStartTime":' + '"' + getTimeStr(calEvent.order.bookStartTime) + '",' +
                        '"jobType":' + '"' + calEvent.order.jobType + '",' +
                        '"comment":' + '"' + calEvent.order.comment + '"' + '}';
        $.cookie('currOrder', currOrder);
        location.href = 'new_appointment.html';
    }
    
    $('#calendarExpress').fullCalendar({
        height: 700,
        header:{    
            center: 'title',  
            left: 'agendaDay,agendaWeek,month',
            right: ''
        },  
        theme: true,  
        editable: false,  
        allDaySlot : false,
        timeFormat : 'HH:mm',
        minTime : '07:00:00',
        maxTime : '18:00:00',
        events:  function(start, end, timezone, callback){  
            var events = [];  
            
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
             
            callback(events);  
        },
        dayClick: onDayClick,
        eventClick: onEventClick
    });
    
    $('#calendarReguler').fullCalendar({
        height: 700,
        header:{  
            left: 'agendaDay,agendaWeek,month',
            right: 'prev,next today',
            center: 'title'
            },  
        theme: true,  
        editable: false,  
        allDaySlot : false,
        timeFormat : 'HH:mm',
        minTime : '07:00:00',
        maxTime : '18:00:00',
        events:  function(start, end, timezone, callback){  
            var events = [];
            $.OrderInfo.getBookedOrderListByDate({
                data : {
                    beginDate: getDateString(start),
                    endDate : getDateString(end),
                    isBook: 1,
                    groupid : $('#groupNo').val() ? $('#groupNo').val() : 1
                },
                success : function(orders) {
                    events = createEvents(orders);
                    callback(events);
                },
                error : function(orders) {
                    events = createEvents(orders);
                    callback(events);
                }
            });   
        },
        dayClick: onDayClick,
        eventClick: onEventClick
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
    
    function createEvents(orders) {
        var events = [];
        if(!orders) {
            return;
        }
        for(var i = 0; i < orders.length; i++) {
            var order = orders[i];
            var evtstart = new Date(order.bookStartTime);
            var evtend = new Date(order.bookStartTime + order.load * 3600000);
            events.push({   
                title: order.registerNum,  
                start: evtstart,  
                end: evtend,
                order:order
            });
        }
        return events;
    }
    
})(jQuery);