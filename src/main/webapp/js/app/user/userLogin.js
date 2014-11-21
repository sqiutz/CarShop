(function($) {
    // 账号 密码
    // var userName=$("#userLogin"),userPass=$("#userPass");
    // 公用的验证方法
    var user = {
        // 验证登录名称
        validateName : function() {
            return true;
        },
        // 验证登录密码
        validatePass : function() {
            return true;
        }
    };
    // 登录事件
    $("#loginBtn").bind("click",function(){
        $.UserInfo.login({
            data : {
                email : 'userName',
                passwd : 'userPass'
            },
            success : function(data) {
                if(data.code == "000000") {
                    location.href = "mechanic_update.html";
                }else {
                   
                }
            }
        });
    });   
})(jQuery);
