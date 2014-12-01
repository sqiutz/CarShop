(function($) {
	var counter = $('#counter'), nextBtn = $('#nextBtn');
	var userProfile;
	$.UserInfo.checkLogin({
	    success : function(data) {
	        if(data.code == '000000') {
	            userProfile = data.obj;
	            $("#helloUserName").text('Hello ' + (userProfile ? userProfile.userName : ''));	            
	        }
	    }
	});
	
	$("<option></option>").val('').text('Select Counter')
        .appendTo($('#counter'));
	$.UserInfo.getProperty({
	    data : {
            pkey : 'COUNTER_NUM'
        },
        success : function(data) {
            if(data.code == '000000') {
                var num = data.obj.pvalue;
                for(var i = 0; i < num; i++) {
                    $("<option></option>").val('COUNTER ' + (i+1)).text('COUNTER ' + (i+1))
                        .appendTo($('#counter'));
                }
            }
        }
	});
	
	counter.bind('change', function() {
		if('' === counter.val()) {
			nextBtn.attr('disabled', 'disabled');
			return;
		}
		$.UserInfo.checkCounter({
			data : {
            	counter : counter.val()
            },
            success : function(data) {
                nextBtn.attr('disabled', false);
    	        if(data.code !== '000000') {
    	            var u = data.obj;
    	            if(u.userName !== userProfile.userName) {
    	                $('#errMsg').html(counter.val() + 
                                ' has been selected by ' +
                                u.userName + 
                                ' . If you confirm, click Next')
                                .show('normal');
    	            }    	            
    	        }
    	    }
		});
	});
	
	//nextBtn.bind()
})(jQuery);