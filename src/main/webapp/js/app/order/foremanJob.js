(function($) {
   
    // 创建modifyque列表
    var listIter = 0, interval = 3000, selectedId = 0, modifyQues;
    var createModifyQueue = function(queues) {
        modifyQues = queues;
        $('#modifyQueue tr.odd').remove();
        $('#modifyQueue tr.even').remove();
        var availHeight = $('#content_rg').height() - $('#queueTitle').height() - 10;
        var j, num = Math.floor(availHeight / 43) - 1;
        for (var i = 0; i < num; i++) {
            j = listIter * num + i;
            var queue = queues && j < queues.length ? queues[j] : null;
            var tr = $('<tr></tr>').attr('class', i % 2 === 0 ? 'odd' : 'even')
                    .appendTo($('#modifyQueue'));
            if(queue) {
                tr.addClass('hoverable').val(queue.id);
                if (queue.id === parseInt(selectedId)) {
                    tr.addClass('selected');
                }
                tr.bind('click', function() {
                    $('#queue tr').removeClass('selected');
                    $(this).addClass('selected');
                    selectedId = $(this).val();
                    
                });
            }
            $('<td></td>').text(queue && queue.order ? queue.order.registerNum : '').appendTo(tr);
            $('<td></td>').text(queue && queue.order ? queue.order.queueNum : '').appendTo(tr);
            $('<td></td>').text(queue ? getTimeStr(queue.createTime) : '').appendTo(tr);
        }
        if (queues && j < queues.length - 1) {
            listIter++;
            setTimeout(function() {
                createModifyQueue(queues);
            }, interval);
        } else {
            listIter = 0;
            setTimeout(function() {
                getModifyQueues();
            }, interval);
        }
    }
    
    //获取modifyque列表
    var getModifyQueues = function() {
        $.OrderInfo.getModifyQueues({
            data : {
                step : 0
            },
            success : createModifyQueue
        });
    };
    
    getModifyQueues();
})(jQuery);