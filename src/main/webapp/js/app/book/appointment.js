(function($) {
    $.cookie('currOrder', '', {expires: -1});
    $.cookie('selectedDate', '', {expires: -1});
    $.cookie('selectedTime', '', {expires: -1});
    $.cookie('selectedGroup', '', {expires: -1});
    $('#monthlyView').hide();
    $('#weeklyView').hide();
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
    
    
    var view = 0, selectedDate = new Date();
    $("#dateDiv").datepicker({
        inline: true,
        showMonthAfterYear: true,
        changeMonth: true,
        changeYear: true,
        buttonImageOnly: true,
        dateFormat: 'yy-mm-dd',
        onSelect: function(dateText, inst) {
            var date = new Date(dateText);
            selectedDate = date;
            switch(view) {
                case 0:
                    createDailyView();
                    break;
                case 1:
                    createWeeklyView();
                    break;
                case 2:
                    var dateString = selectedDate.toDateString().split(' ');
                    $('#canlendarTitle').text(dateString[1] + ' ' + dateString[3]);
                    $('#monthlyView').fullCalendar('gotoDate', date);
                    break;
            }
        }
    });

    $('#dayBtn').bind("click", function(){
        if(0 !== view) {
            view = 0;
            $('#monthlyView').hide();
            $('#weeklyView').hide();
            $('#weekBtn').removeClass('ui-state-active');
            $('#monthBtn').removeClass('ui-state-active');
            $('#dayBtn').addClass('ui-state-active');
            $('#dailyView').show();            
            createDailyView();
        }
    });
    $('#weekBtn').bind("click", function(){
        if(1 !== view) {
            view = 1;
            $('#monthlyView').hide();
            $('#dailyView').hide();
            $('#dayBtn').removeClass('ui-state-active');
            $('#monthBtn').removeClass('ui-state-active');
            $('#weekBtn').addClass('ui-state-active');
            $('#weeklyView').show();            
            createWeeklyView();
        }
    });
    $('#monthBtn').bind("click", function(){
        if(2 !== view) {
            view = 2;
            $('#weeklyView').hide();
            $('#dailyView').hide();
            $('#dayBtn').removeClass('ui-state-active');
            $('#weekBtn').removeClass('ui-state-active');
            $('#monthBtn').addClass('ui-state-active');
            $('#monthlyView').show();            
            createMonthlyView();
        }
    });
        
    function createDailyView() {
        var dateString = selectedDate.toDateString().split(' ');
        $('#canlendarTitle').text(dateString[1] + ' ' + dateString[2] + ', ' + dateString[3]);
        $('#dailyView table').remove();
        var table = $('<table></table>').attr('class', 'calender').css('width', '720px').appendTo($('#dailyView'));
        var tr = $('<tr></tr>').appendTo(table);
        $('<th></th>').addClass('ui-widget-header').text('').appendTo(tr);
        $('<th></th>').addClass('ui-widget-header').attr('colSpan', 2).text(getWeekDay(selectedDate)).appendTo(tr);        
        for(var i = 0; i < 6; i++) {
            tr = $('<tr></tr>').appendTo(table);
            $('<td></td>').text('EM-' + (i+1))
                .css('width', '53px').appendTo(tr);
//            $('<td></td>').text((i+1)*10 + '%')
//                .css('border-bottom', '0').appendTo(tr);            
            var td = $('<td></td>').css('border-right', '0')
                .css('width', '110px').appendTo(tr);
            $('<div></div>').attr('id', 'd-content-' + i).css('margin', '0px auto')
                .css('width', '90px').css('height', '90px').appendTo(td);
            drawChart('d-content-' + i, 0.1*(i+1));  
            //tr = $('<tr></tr>').appendTo(table);
            var td = $('<td></td>').css('border-left', '0').css('text-align', 'left').appendTo(tr);            
            var div = $('<div></div>').css('display', 'inline-block')
                .appendTo(td);
            $('<a></a>').attr('class', 'link').text('AC00342').appendTo(div);
            $('<a></a>').attr('class', 'link').text('BC00123').appendTo(div);
            $('<a></a>').attr('class', 'link').text('DS12345').appendTo(div);
        }
    }
    
    function createWeeklyView() {
        $('#weeklyView table').remove();
        var table = $('<table></table>').attr('class', 'calender').css('width', '720px').appendTo($('#weeklyView'));
        var tr = $('<tr></tr>').appendTo(table);
        $('<th></th>').addClass('ui-widget-header').text('').appendTo(tr);
        var start = new Date(selectedDate);
        start.setDate(start.getDate() - start.getDay());
        var dateString = start.toDateString().split(' ');
        $('#canlendarTitle').text(dateString[1] + ' ' + dateString[2] + ' - ' + (parseInt(dateString[2]) +  6) + ', ' + dateString[3]);
        for(var i = 0; i < 7; i++) {
            var d = new Date(start);
            d.setDate(start.getDate() + i);
            $('<th></th>').addClass('ui-widget-header').css('width', '95px')
                .text(getWeekDay(d, true) + ' ' + (d.getMonth() + 1) + '/' + d.getDate()).appendTo(tr);
        }
        for(var j = 0; j < 6; j++) {
            tr = $('<tr></tr>').appendTo(table);
            $('<td></td>').text('EM-' + (j+1)).appendTo(tr);
            for(i = 0; i < 7; i++) {
                var td = $('<td></td>').appendTo(tr);
                $('<div></div>').attr('id', 'w-content-' + j + '-' + i).css('margin', '0px auto')
                    .css('width', '90px').css('height', '90px').appendTo(td);
                drawChart('w-content-' + j + '-' + i, 0.01*((j+1)*10+i));
            }            
        }
    }
    
    function createMonthlyView() {
        var dateString = selectedDate.toDateString().split(' ');
        $('#canlendarTitle').text(dateString[1] + ' ' + dateString[3]);
        $('#monthlyView').fullCalendar({
            height: 700,
            header:{  
                left: '',
                right: '',
                center: ''
                },  
            theme: true,  
            editable: false,  
            allDaySlot : true,
            timeFormat : 'HH:mm',
            minTime : '07:00:00',
            maxTime : '18:00:00',
            eventColor : '#ffffff',
            events:  function(start, end, timezone, callback){ 
                var events = [];
                var s = new Date(selectedDate);
                s.setDate(1);
                var d = new Date(s), m = s.getMonth();
                for(i = 0; d.getMonth() == m; i++, d = new Date(s.getTime() + i * 86400000)) {
                    if(0 == d.getDay() || 6 == d.getDay()) {
                        continue;
                    }
                    var p = 50 + i;
                    var evtstart = new Date(d);
                    evtstart.setHours(8);
                    evtstart.setMinutes(0);
                    evtstart.setSeconds(0);
                    var evtend = new Date(d);
                    evtend.setHours(17);
                    evtend.setMinutes(0);
                    evtend.setSeconds(0);
                    events.push({  
                        id:'fc-content-'+d.getTime(),
                        title: p + '%',  
                        start: evtstart,  
                        end: evtend,
                        percent:0.01 * p
                    });
                }
                callback(events);
                for(i = 0; i < events.length; i++) {
                    var event = events[i];
                    drawChart(event.id, event.percent);
                }
            },
            eventRender: function(event, element) {
                $(element).find('.fc-content').attr('id', event.id).html('')
                    .css('width', '90px').css('height', '90px');
//                $('<div></div>').css('width', '90px').css('height', '90px')
//                    .attr('id', event.id).appendTo($(element));
//                drawChart(event.id, event.percent);
//                $(element).css('height', (90 * event.percent) + 'px')
//                $(element).append($('<span></span>').attr('class', 'fc-title')
//                        .text(event.title));
                
            }
        });
        $('#monthlyView .fc-toolbar').hide();        
        //$('#monthlyView').fullCalendar('refetchEvents');
    }
    
    function getWeekDay(date, short) {
        var result = '';
        switch(date.getDay()) {
            case 0 : 
                result = short ? 'Sun' : 'Sunday';
                break;
            case 1 : 
                result = short ? 'Mon' : 'Monday';
                break;
            case 2 : 
                result = short ? 'Tue' : 'Tuesday';
                break;
            case 3 : 
                result = short ? 'Wed' : 'Wednesday';
                break;
            case 4 : 
                result = short ? 'Thu' : 'Thursday';
                break;
            case 5 : 
                result = short ? 'Fri' : 'Friday';
                break;
            case 6 : 
                result = short ? 'Sat' : 'Saturday';
                break;                
        }
        return result;
    }
    
    createDailyView();
     
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