function layout(minWidth, minHeight) {
    var headerTopH = 0;
    if($('#headerTop').length > 0) {
        headerTopH = $('#headerTop').height();
    } 
    if(!minWidth) {
    	minWidth = 1200;
    }
    if(!minHeight) {
    	minHeight = 750;
    }
    var width = $(window).width() > minWidth ? $(window).width() : minWidth;
    var height = ($(window).height() > minHeight ? $(window).height() : minHeight)- headerTopH;
    $('#container').css('height', height + 'px');
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
    return[width, height, contentH];
}