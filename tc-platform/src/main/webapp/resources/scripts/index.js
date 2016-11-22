layui.use(['form'], function () {
    var form = layui.form();

    // form verify
    form.verify({
        username: function (value) {
            if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5]{6,16}$").test(value)) {
                return '用户名由6-16位数字，字符，汉字，下划线组成';
            }
        },

        password: function (value) {
            if (!new RegExp("^[a-zA-Z0-9]{6,16}$").test(value)) {
                return '密码由6-16位数字字符组成';
            }
        },

        verificationCode: function (value) {
            if (!new RegExp("^[a-zA-Z0-9]{6}$").test(value)) {
                return '验证码由6位数字字符组成';
            }
        }
    });

    // form submit
    form.on('submit(signup)', function (data) {
        var password = data.field.password;
        // find token
        var token = fetchToken();
        // encode and set
        var cltPassword = $.md5($.md5(password) + token);
        $('#signup-form').find('input[type = "password"][name = "password"]').val(cltPassword);
        return true;
    });

    var fetchToken = function () {
        var token = null;
        $.ajax({
            url: __base + '/auth/token.json',
            type: 'post',
            async: false,
            success: function (data, textStatus, jqXHR) {
                __doWithTcR(data, function (body) {
                    token = body;
                });
            }
        });
        return token;
    }
});