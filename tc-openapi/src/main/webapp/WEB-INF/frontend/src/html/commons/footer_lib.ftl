<#-- + footer_lib -->
<#-- @build see glupfile.js / mg-script-libs -->
<script src="/tc-static/src/resources/libs/_header_lib.min.js"
        type="text/javascript"></script>
<#-- @build -->
<script src="/tc-static/src/resources/libs/layui/layui.js"
        type="text/javascript"></script>
<script src="/tc-static/src/resources/scripts/global/global.js"
        type="text/javascript"></script>
<script src="/tc-static/src/resources/scripts/global/header_menu_footer.js"
        type="text/javascript"></script>

<script type="text/javascript">
    // system infrastructure
    var _TcC = {

        _htmlBase: '${htmlBase}',

        _openApiBase: '${openApiBase}',

        _staticBase: '${staticBase}',

        // ajax helper func
        doWithTcR: function (tcR, okCallback, badRequestCallback, exCallback) {
            if (_.isUndefined(tcR)) {
                layer.alert('系统繁忙，请稍后再试！');
                return;
            }
            if (tcR.code == 8888) {
                if (_.isFunction(badRequestCallback)) {
                    badRequestCallback(tcR.code, tcR.message, tcR.extra);
                } else {
                    layer.msg('系统繁忙，请刷新页面再试！');
                }
            } else if (tcR.code == 9999) {
                if (_.isFunction(exCallback)) {
                    exCallback(tcR.code, tcR.message, tcR.extra);
                } else {
                    layer.msg('系统异常，请刷新页面再试！');
                }
            } else {
                if (_.isFunction(okCallback)) {
                    okCallback(tcR.code, tcR.body, tcR.extra);
                } else {
                    // do nothing
                }
            }
        },

        defaultAxiosExHandler: function () {
            layer.alert('抱歉，系统繁忙，请稍后再试！');
        }
    };

    // axios infrastructure
    var _TcAxios = axios.create({

        baseURL: _TcC._openApiBase,

        // 30s
        timeout: 30000,

        withCredentials: true,

        xsrfCookieName: 'XSRF-TOKEN',

        xsrfHeaderName: 'X-XSRF-TOKEN',

        // 50k
        maxContentLength: 51200

    });

    (function () {
        // Add a request interceptor, only logic, do nothing
        _TcAxios.interceptors.request.use(function (config) {
            return config;
        }, function (error) {
            return Promise.reject(error);
        });

        // Add a response interceptor, only logic, do nothing
        _TcAxios.interceptors.response.use(function (response) {
            if (_.isUndefined(response)) {
                throw new Error('empty response');
            }
            return response;
        }, function (error) {
            return Promise.reject(error);
        });
    })();
</script>
<#-- - footer_lib -->