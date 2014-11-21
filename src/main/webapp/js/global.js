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