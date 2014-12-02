(function($) {
    var oListIter = 0, sListIter = 0, interval = 3000;
    
    // 创建服务队列
    var createServingList = function(serves) {
        $('#servingList tr.odd').remove();
        $('#servingList tr.even').remove();
        var availHeight = ($('#content').height() - 20) * 0.5 - $('#servingListTitle').height() - 15;
        var j, num = Math.floor(availHeight / 43) - 1;
        for (var i = 0; i < num; i++) {
            j = sListIter * num + i;
            var serve = serves && j < serves.length ? serves[j] : null;
            var tr = $('<tr></tr>').attr('class', i % 2 === 0 ? 'odd' : 'even')
                    .appendTo($('#servingList'));
            $('<td></td>').text(serve ? serve.order.registerNum : '').appendTo(tr);
            $('<td></td>').text(serve ? serve.order.queueNum : '').appendTo(tr);
            $('<td></td>').text(serve ? serve.user.userName : '').appendTo(tr);
            $('<td></td>').text(serve ? getTimeStr(serve.order.startTime) : '').appendTo(tr);
            $('<td></td>').text(serve ? getTimeStr(serve.order.endTime) : '').appendTo(tr);
        }
        if (j < serves.length - 1) {
            sListIter++;
            setTimeout(function() {
                createServingList(serves);
            }, interval);
        } else {
            sListIter = 0;
            setTimeout(function() {
                getServeQueues();
            }, interval);
        }
    };
    
    // 获取服务队列
    var getServeQueues = function() {
        $.OrderInfo.getServeQueues({
            data : {
                step : 0
            },
            success : function(serves) {
                createServingList(serves);
            }
        });
    };
    
    // 创建订单列表
    var createWaitingList = function(orders) {
        $('#waitingList tr.odd').remove();
        $('#waitingList tr.even').remove();
        var availHeight = ($('#content').height() - 20) * 0.5 - $('#waitingListTitle').height() - 15;
        var j, num = Math.floor(availHeight / 43) - 1;
        for (var i = 0; i < num; i++) {
            j = oListIter * num + i;
            var order = orders && j < orders.length ? orders[j] : null;
            var tr = $('<tr></tr>').attr('class', i % 2 === 0 ? 'odd' : 'even')
                    .appendTo($('#waitingList'));
            $('<td></td>').text(order ? order.registerNum : '').appendTo(tr);
            $('<td></td>').text(order ? order.queueNum : '').appendTo(tr);
            $('<td></td>').text(order ? getTimeStr(order.startTime) : '').appendTo(tr);
        }
        if (j < orders.length - 1) {
            oListIter++;
            setTimeout(function() {
                createWaitingList(orders);
            }, interval);
        } else {
            oListIter = 0;
            setTimeout(function() {
                getOrderList();
            }, interval);
        }
    };
    
    // 获取订单列表
    var getOrderList = function() {
        $.OrderInfo.getOrderList({
            data : {
                status : 1
            },
            success : createWaitingList
        });
    };
    
    function getTimeStr(time) {
        if(undefined === time || null === time) {
            return '';
        }
        var data = new Date(time);
        var hours = data.getHours(); 
        var minutes = data.getMinutes();
        return (hours < 10 ? '0' : '') + hours + ':' + 
            (minutes < 10 ? '0' : '') + minutes;
    }
    
    getServeQueues();
    getOrderList();
})(jQuery);;