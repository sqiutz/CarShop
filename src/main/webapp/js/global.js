function layout(minWidth, minHeight, contentPadding) {
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
    var width = $(window).width();
    if(width < minWidth) {
    	width = minWidth
    }
    var height = $(window).height();
    if(height < minHeight) {
    	height = minHeight
    }
    height -= headerTopH;
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
    if(undefined === contentPadding) {
    	contentPadding = 10
    }
    var contentH = height - headerH - footerH - contentPadding;
    $('#content').css('height', contentH + 'px');
    return[width, height, contentH];
}

function loadLang(url, complete) {
    var head = document.getElementsByTagName("head")[0] || document.documentElement;
    var script = document.createElement("script");
    script.type = 'text/javascript';
    script.src = url;
    script.onload = script.onreadystatechange = function() {
        complete();
    }
    
    if (head.firstChild) {
        head.insertBefore(script, head.firstChild);
    } else {
        head.appendChild(script);
    }
}