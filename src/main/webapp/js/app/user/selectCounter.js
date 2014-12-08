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
            $('#changePwd').text(CHANGE_PASSW0RD);
            $('#logout').text(LOGOUT);
            $('#title').text(SUZUKI_SIAGA_LEBIH_MENGERTI_KELUARGA);
            $('#legend').text(SELECT_COUNTER);
            $('#isBookerLabel').text(IS_BOOKER);
            $('#opNo').text(NO);
            $('#opYes').text(YES);
            $('#counterLabel').text(COUNTER_NO);
            $('#nextBtn').text(NEXT).attr('title', NEXT);
        });
    }
    
	var counter = $('#counter'), isBooker = $('#isBooker'), nextBtn = $('#nextBtn');
	var userProfile;
	$.UserInfo.checkLogin({
		success : function(data) {
			if (data.code == '000000') {
				userProfile = data.obj;
				$("#helloUserName").text(
						'Hello ' + (userProfile ? userProfile.userName : ''));
			}
		}
	});

	$("<option></option>").val('').text('Select Counter').appendTo(
			$('#counter'));
	$.UserInfo.getProperty({
		data : {
			name : 'COUNTER_NUM'
		},
		success : function(data) {
			if (data.code == '000000') {
				var num = data.obj.value;
				for (var i = 0; i < num; i++) {
					$("<option></option>").val('COUNTER ' + (i + 1)).text(
							'COUNTER ' + (i + 1)).appendTo($('#counter'));
				}
			}
		}
	});

	counter.bind('change', function() {
		if ('' === counter.val()) {
			nextBtn.attr('disabled', 'disabled');
			return;
		}
		$.UserInfo.checkCounter({
			data : {
				counter : counter.val()
			},
			success : function(data) {
				nextBtn.attr('disabled', false);
				if (data.code !== '000000'
						&& data.obj.userName !== userProfile.userName) {
					$('#errMsg').html(
							counter.val() + ' has been selected by '
									+ data.obj.userName
									+ ' . If you confirm, click Next.').show(
							'normal');
				} else {
					$('#errMsg').hide('normal');
				}
			}
		});
	});

	nextBtn.bind('click', function() {
		$.UserInfo.modifyUser({
			data : {
				id : userProfile.id,
				groupId : userProfile.groupId,
				userName : userProfile.userName,
				passwd : userProfile.passwd,
				isAdmin : userProfile.isAdmin,
				isValid : 1,
				counter : counter.val(),
				isBooker : parseInt(isBooker.val())
			},
			success : function(data) {
				if (data.code == '000000') {
					location.href = 'sa_que.html';
				}
			}
		});
	})
})(jQuery);