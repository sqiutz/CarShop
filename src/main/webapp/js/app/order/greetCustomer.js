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
            $('#printBtn').text(PRINT).attr('title', PRINT);
        });
    } 
    
    $('#printBtn').bind('click', function() {
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
                    $('#queNumber').text(order ? order.queueNum : '');
                }                
    		}
    	});
    });
    
    $('#regNumber').keyup(function() {
        var regNum = $('#regNumber').val();
        if(regNum.length > 6) {
            $('#printBtn').attr('disabled', false);
        }
        else {
            $('#printBtn').attr('disabled', 'disabled');
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