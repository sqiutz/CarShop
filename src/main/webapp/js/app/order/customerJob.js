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
            $('#title').text(CUSTOMER_JOB_PROGRESS_BOARD);
            $('#regNoCol').text(REG_NO);
            $('#estimatedEndTimeCol').html(ESTIMATED_END_TIME);
            $('#jobStatusCol').text(JOB_STATUS);
        });
    }
    
    var oListIter = 0, interval = 3000;    
    // 获取订单列表
    var getOrderList = function() {
        $.OrderInfo.getOrderList({
            data : {
                startStatus : 1
            },
            success : createOrderList
        });
    };
    
    // 创建订单列表
    var createOrderList = function(orders) {
        $('#orderList tr.odd').remove();
        $('#orderList tr.even').remove();
        var availHeight = $('#content').height() - 20;
        var j, num = Math.floor(availHeight / 43) - 1;
        for (var i = 0; i < num; i++) {
            j = oListIter * num + i;
            var order = orders && j < orders.length ? orders[j] : null;
            var tr = $('<tr></tr>').attr('class', i % 2 === 0 ? 'odd' : 'even')
                    .appendTo($('#orderList'));
            if(order) {
                tr.addClass('hoverable').val(order.id);
            }
            $('<td></td>').text(order ? order.registerNum : '').appendTo(tr);
            $('<td></td>').text(order ? getEstEndTimeStr(order.startTime, order.estimationTime) : '').appendTo(tr);
            $('<td></td>').text(order ? order.statusValue : '').appendTo(tr);
        }
        if (orders && j < orders.length - 1) {
            oListIter++;
            setTimeout(function() {
                createOrderList(orders);
            }, interval);
        } else {
            oListIter = 0;
            setTimeout(function() {
                getOrderList();
            }, interval);
        }
    };
    
    function getEstEndTimeStr(startTime, estTime) {
        if(undefined === startTime || null === startTime || 
                undefined === estTime || null === estTime) {
            return '';
        }
        var data = new Date(startTime + estTime * 60000);
        var hours = data.getHours(); 
        var minutes = data.getMinutes();
        return (hours < 10 ? '0' : '') + hours + ':' + 
            (minutes < 10 ? '0' : '') + minutes;
    }
    
    getOrderList();
})(jQuery);