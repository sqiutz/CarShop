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
            $('#title').text(SUZUKI_SIAGA_LEBIH_MENGERTI_KELUARGA);
            $('#queingNumber').text(QUEING_NUMBER);
            $('#regNumberLabel').text(KEY_IN_REGISTRATION_NUMBER);
            $('#bookedTime').text(BOOKED_TIME);
            $('#queNumberLabel').text(QUE_WITH_NUMBER);
            $('#queryBtn').text(QUERY).attr('title', QUERY);
            $('#printBtn').text(PRINT).attr('title', PRINT);
        });
    } 
    
    $('#queryBtn').bind('click', function() {
    	$.OrderInfo.startOrder({
    		data : {
    			registerNum : $('#regNumber').val(),
    			assignDate : getDateString(new Date())
    		},
    		success : function(data) {
                if(data.code == '000000') {
                    //$('#regNumber').val('');
                    var order = data.obj;
                    $('#bookedTime').text(BOOKED_TIME + (order ? getTimeStr(order.bookTime) : ''));
                    $('#queNumber').text(order ? (order.bakQueueNum ? order.bakQueueNum : order.queueNum) : '');
                }                
    		}
    	});
    });
    
    $('#printBtn').bind('click', function() {
        $.OrderInfo.printOrder({
            data : {
                registerNum : $('#regNumber').val(),
                assignDate : getDateString(new Date())
            },
            success : function(data) {
                if(data.code == '000000') {
                }                
            }
        });
    });
    
    $('#regNumber').keyup(function() {
        var regNum = $('#regNumber').val();
        if(regNum.length > 6) {
            $('#queryBtn').attr('disabled', false);
            $('#printBtn').attr('disabled', false);
        }
        else {
            $('#queryBtn').attr('disabled', 'disabled');
            $('#printBtn').attr('disabled', false);
        }
    });
    
    function getOrder() {
        $.OrderInfo.getOrder({
            data : {
                registerNum : $('#regNumber').val()
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
    }
})(jQuery);