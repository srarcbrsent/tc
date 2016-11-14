<script src="${.vars.staticBase}/libs/jquery-3.1.0.min.js" type="application/javascript"></script>
<script src="${.vars.staticBase}/libs/layui/layui.js" type="application/javascript"></script>
<link href="${.vars.staticBase}/libs/normalize.min.css" type="text/css" rel="stylesheet"/>
<link href="${.vars.staticBase}/libs/layui/css/layui.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript">
    // tc base
    var __base = '${base}';
    var __staticBase = '${staticBase}';

    // import layer
    layui.use(['layer'], function () {
        var layer = layui.layer;
    });

    // ajax helper func
    var __doWithTcR = function (tcR, okCallback, exCallback) {
        if (tcR.code == 200) {
            okCallback(tcR.body);
        } else {
            if (exCallback != undefined && exCallback != null) {
                exCallback(tcR.code, tcR.message, tcR.extra);
            } else {
                layer.msg('系统异常，请稍后刷新页面再试！');
            }
        }
    };
</script>