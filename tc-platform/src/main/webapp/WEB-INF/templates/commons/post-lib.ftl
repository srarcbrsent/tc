<!-- + post-lib -->
<@static.script src="/libs/jquery-3.1.0.min.js"/>
<@static.script src="/libs/vue.min.js"/>
<@static.script src="/libs/axios.min.js"/>
<@static.script src="/libs/underscore.min.js"/>
<@static.script src="/libs/assets/js/amazeui.min.js"/>
<@static.script src="/libs/layer/layer.min.js"/>

<script type="text/javascript">
    // tc base
    const __base = '${base}';
    const __staticBase = '${staticBase}';

    // ajax help config
    const __axiosConfig = {
        // 6s
        timeout: 6000,
        xsrfCookieName: 'XSRF-TOKEN',
        xsrfHeaderName: 'X-XSRF-TOKEN',
        // 50k
        maxContentLength: 51200
    };

    // ajax helper func
    const __doWithTcR = function (tcR, okCallback, exCallback) {
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
<!-- - post-lib -->