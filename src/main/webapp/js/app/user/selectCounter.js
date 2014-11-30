(function($) {
	var counter = $('#counter'), nextBtn = $('#nextBtn');
	$.UserInfo.checkLogin({
	    success : function(data) {
	        if(data.code == '000000') {
	            $("#helloUserName").text('Hello ' + (data.obj ? data.obj.userName : ''));	            
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