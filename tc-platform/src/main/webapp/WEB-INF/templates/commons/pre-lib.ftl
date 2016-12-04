<meta charset="UTF-8">
<meta name="viewport"
      content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="icon"
      type="image/x-icon"
      href="${staticBase}/images/favicon.ico"/>

<script src="${staticBase}/libs/jquery-3.1.0.min.js"
        type="application/javascript"></script>
<script src="${staticBase}/libs/vue.min.js"
        type="application/javascript"></script>
<script src="${staticBase}/libs/axios.min.js"
        type="application/javascript"></script>
<script src="${staticBase}/libs/underscore.min.js"
        type="application/javascript"></script>
<script src="${staticBase}/libs/assets/js/amazeui.min.js"
        type="application/javascript"></script>
<script src="${staticBase}/libs/layer/layer.min.js"
        type="application/javascript"></script>

<link href="${staticBase}/libs/normalize.min.css"
      type="text/css"
      rel="stylesheet"/>
<link href="${staticBase}/libs/assets/css/amazeui.flat.min.css"
      type="text/css"
      rel="stylesheet"/>

<script type="text/javascript">
    // tc base
    var __base = '${base}';
    var __staticBase = '${staticBase}';

    // ajax help config
    var __axiosConfig = {
        // 6s
        timeout: 6000,
        xsrfCookieName: 'XSRF-TOKEN',
        xsrfHeaderName: 'X-XSRF-TOKEN',
        // 50k
        maxContentLength: 51200
    };

    // ajax helper func
    var __doWithTcR = function (tcR, okCallback, exCallback) {
        if (_.isUndefined(tcR)) {
            layer.alert('系统繁忙，请稍后再试！');
        }
        if (tcR.code == 200) {
            if (_.isFunction(okCallback)) {
                okCallback(tcR.body);
            } else {
                // do nothing
            }
        } else {
            if (_.isFunction(exCallback)) {
                exCallback(tcR.code, tcR.message, tcR.extra);
            } else {
                layer.msg('系统异常，请刷新页面再试！');
            }
        }
    };
</script>