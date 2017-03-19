$(document).ready(function () {
    // menu bar auto selected current page.
    $('ul.body-menu')
        .find('a')
        .each(function (index, element) {
                if ($(element).attr('href') == '/') {
                    if (index === 0) {
                        $(element).parent('ul.body-menu li').addClass('layui-this');
                    }
                } else {
                    if (window.location.href.indexOf($(element).attr('href')) != -1) {
                        $(element).parent('ul.body-menu li').addClass('layui-this');
                    }
                }
            }
        );
});