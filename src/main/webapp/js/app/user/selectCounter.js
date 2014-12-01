(function($) {
	var counter = $('#counter'), nextBtn = $('#nextBtn');
	$.UserInfo.checkLogin({
	    success : function(data) {
	        if(data.code == '000000') {
	            $("#helloUserName").text('Hello ' + (data.obj ? data.obj.userName : ''));	            
	        }
	    }
	});
	
	$.UserInfo.getProperty({
	    data : {
            key : 'COUNTER_NUM'
        },
        success : function(data) {
            if(data.code == '000000') {
                $('#counter option').remove;
                $("<option></option>").val('').text('Select Counter')
                    .appendTo($('#counter'));
                var num = data.obj.value;
                for(var i = 0; i < num; i++) {
                    
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
    	        if(data.code == '000000') {
    	        	nextBtn.attr('disabled', false);	            
    	        }
    	        else {
    	        	
    	        }
    	    }
		});
	});
	
	//nextBtn.bind()
})(jQuery);