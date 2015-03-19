(function($) {
    $.cookie('currOrder', '', {expires: -1});
    $.cookie('selectedDate', '', {expires: -1});
    $.cookie('selectedTime', '', {expires: -1});
    $.cookie('selectedGroup', '', {expires: -1});
    $('#monthlyView').hide();
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
            $('#title').text(SUZUKI_SIAGA_LEBIH_MENGERTI_KELUARGA);
            $('#newBtn').text(NEW_APPOINTMENT).attr('title', NEW_APPOINTMENT);
            $('#settingBtn').text(SETTING_PARAMETER).attr('title', SETTING_PARAMETER);
            $('#serviceTypeLabel').text(SERVICE_TYPE);
            $('#manpowerLabel').text(MANPOWER_ALLOCATION);
            $('#expressMainLabel').text(EXPRESS_MAINTENANCE);
            $('#regServiceLabel').text(REGULAR_SERVICE);
            $('#groupNoLabel').text(GROUP_NO);
            $('#dayBtn').text(DAY).attr('title', DAY);
            $('#weekBtn').text(WEEK).attr('title', WEEK);
            $('#monthBtn').text(MONTH).attr('title', MONTH);
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
        
    function createDailyView(parenet, color) {
        var table = $('<table></table>').attr('class', 'calender calender' + color).css('width', '710px').appendTo(parenet);
        var tr = $('<tr></tr>').appendTo(table);
        $('<th></th>').text('').appendTo(tr);
        $('<th></th>').text('Wednesday').appendTo(tr);        
        for(var i = 0; i < 6; i++) {
            tr = $('<tr></tr>').appendTo(table);
            $('<td></td>').attr('rowSpan', '2').text('EM-' + (i+1))
                .css('width', '53px').css('text-align', 'center').appendTo(tr);
            $('<td></td>').text((i+1)*10 + '%')
                .css('border-bottom', '0').css('padding-left', '4px').appendTo(tr);
            tr = $('<tr></tr>').appendTo(table);
            var td = $('<td></td>').css('border-top', '0').appendTo(tr);
            $('<a></a>').attr('class', 'link').text('AC00342').appendTo(td);
            $('<a></a>').attr('class', 'link').text('BC00123').appendTo(td);
            $('<a></a>').attr('class', 'link').text('DS12345').appendTo(td);
        }
    }
    
    createDailyView($('#dailyExpress'), 'Green');
    createDailyView($('#dailyReguler'), 'Red');
    
    /*$('#groupNo').change(function() {
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
                        '"userName":' + '"' + calEvent.order.customer.userName + '",' +
                        '"mobilePhone":' + '"' + calEvent.order.customer.mobilephone + '",' +
                        '"bookStartTime":' + '"' + getTimeStr(calEvent.order.bookStartTime) + '",' +
                        '"jobType":' + '"' + (calEvent.order.jobType || '') + '",' +
                        '"express":' + '"' + (calEvent.order.express || '') + '",' +
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
            $.OrderInfo.getBookedOrderListByDate({
                data : {
                    beginDate: getDateString(start),
                    endDate : getDateString(end),
                    isBook: 1,
                    express : 'A',
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
    }*/
    
})(jQuery);