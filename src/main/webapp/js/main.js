//基本配置
var conf={
    domain:"http://localhost:8080/business/"
};

//路径定义
require.config({
    baseUrl : 'js/',
    paths : {
        common : 'common',
        jquery : 'lib/jquery-2.1.1.min',
        userInfo:'app/user/userInfo',
        orderInfo:'app/order/orderInfo',
        md5:'lib/md5'
    }
});
require(["jquery", "common"],function($, common){
    $(function(){
        function layout() {
            var width = $('#container').width();
            var height = $('#container').height();
            var headerH = width * 0.085;
            $('#header').css('height', headerH + 'px');
            $('#header').css('margin', '0px ' + width * 0.255 + 'px 0px' + width * 0.172 + 'px');
            $('#title').css('line-height', width * 0.085 + 'px');
            var footerH = 0;
            if ($('#footer').length > 0) {
                footerH = width * 0.043;
                $('#footer').css('height', footerH + 'px');
            }
            var contentH = height - headerH - footerH - 10;
            $('#content').css('height', contentH + 'px');
        }
        
        var jsArr=$("body").attr("data-js")?$("body").attr("data-js").split(" "):[];
        if(jsArr.length){
            $.each(jsArr,function(i,n){
                jsArr[i] = "../js/app/"+n;
            });
            require(jsArr,function(){});
        }
    });
});
