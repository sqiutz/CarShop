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
            $('#changePwd').text(CHANGE_PASSW0RD).attr('title', CHANGE_PASSW0RD);
            $('#logout').text(LOGOUT).attr('title', LOGOUT);
            $('#title').text(SUZUKI_SIAGA_LEBIH_MENGERTI_KELUARGA);
            $('#legend').text(SELECT_COUNTER);
            $('#isBookerLabel').text(IS_BOOKER);
            $('#opNo').text(NO);
            $('#opYes').text(YES);
            $('#counterLabel').text(COUNTER_NO);
            $('#nextBtn').text(NEXT).attr('title', NEXT);
            
            $("<option></option>").val('').text(SELECT_COUNTER).appendTo($('#counter'));
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
            $.UserInfo.checkLogin({
                success : function(data) {
                    if (data.code == '000000') {
                        userProfile = data.obj;
                        $("#helloUserName").text(
                                HELLO + ' ' + (userProfile ? userProfile.userName : ''));
                    }
                }
            });
        });
    }
    
	var counter = $('#counter'), isBooker = $('#isBooker'), nextBtn = $('#nextBtn');
	var userProfile;

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
							counter.val() + ' ' + MSG_COUNTER_HAVE_BEEN_SELECTED + ' '
									+ data.obj.userName
									+ ' . ' + MSG_CLICK_NEXT).show(
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