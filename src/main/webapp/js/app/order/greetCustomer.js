(function($) {
    applyLang();
    $.UserInfo.getProperty({
        data : {
            name : 'LANGUAGE'
        },
        success : function(data) {
            if (data.code == '000000') {
                var langCode = data.obj.value;
                applyLang(langCode);
            }
        }
    });
    
    function applyLang(langCode) {
        if(undefined === langCode || null === langCode) {
            langCode = 'en_US';
        }
        loadLang('lang/' + langCode + '.js', function() {
            $('#title').text(SUZUKI_SIAGA_LEBIH_MENGERTI_KELUARGA);
            $('#queingNumber').text(QUEING_NUMBER);
            $('#regNumberLabel').text(KEY_IN_REGISTRATION_NUMBER);
            $('#queryBtn').text(QUERY).attr('title', QUERY);
            $('#bookedTime').text(BOOKED_TIME);
            $('#queNumberLabel').text(QUE_WITH_NUMBER);
            $('#printBtn').text(PRINT).attr('title', PRINT);
            $('#bookBtn').text(BOOK).attr('title', BOOK);
        });
    } 
    
    $('#queryBtn').bind('click', function() {
    	$.OrderInfo.getOrder({
    		data : {
    			registerNumber : $('#regNumber').val()
    		},
    		success : function(data) {
                if(data.code == '000000') {
                	var order = data.obj;
                	$('#bookedTime').text(BOOKED_TIME + (order ? getTimeStr(order.createTime) : ''));
                	$('#queNumber').text(order ? order.queueNum : '');
                }
                else {
                	$('#bookedTime').text(BOOKED_TIME);
                	$('#queNumber').text('');
                }
    		}
    	});
    });
    
    $('#bookBtn').bind('click', function() {
    	$.OrderInfo.addOrder({
    		data : {
    			registerNumber : $('#regNumber').val()
    		},
    		success : function(data) {
    			if(data.code == '000000') {
    				
    			}
        	}
    	});
    });
})(jQuery);