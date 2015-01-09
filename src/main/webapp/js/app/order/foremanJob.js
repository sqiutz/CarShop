(function($) {
   
    // 创建modifyque列表
    var createModifyQueue = function(queues) {
        var q = queues;
    }
    
    //获取modifyque列表
    var getModifyQueues = function() {
        $.OrderInfo.getModifyQueues({
            data : {
                step : 1
            },
            success : function(queues) {
                createModifyQueue(queues);
            }
        });
    };
    
    getModifyQueues();
})(jQuery);