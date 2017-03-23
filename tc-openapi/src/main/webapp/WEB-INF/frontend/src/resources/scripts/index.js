layui.use(['element', 'form'], function () {
    // import
    var element = layui.element();
    var form = layui.form();

    // bind layui component listener
    form.verify({
        username: function (value) {
            var isMobile = new RegExp("^1[0-9]{10}$").test(value);
            var isEmail = new RegExp("^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$").test(value);
            if (!isMobile && !isEmail) {
                return '用户名为手机号或者邮箱';
            }
        },
        password: function (value) {
            if (!new RegExp("^[a-zA-Z0-9_]{6,16}$").test(value)) {
                return '密码为6-16位的英文字符或数字';
            }
        },
        verificationCode: function (value) {
            if (!new RegExp("^[0-9_]{6}$").test(value)) {
                return '验证码为6位数字';
            }
        }
    });

    form.on('submit(fm-login)', function (data) {
        var password = data.field.password;

        getPublicKeyAsync()
            .then(function (publicKey) {
                return login(data.field, publicKey);
            })
            .then(function (data) {
                var code = data.code;
                doLogin(code);
            })
            .catch(function () {
                _TcC.defaultAxiosExHandler();
            });

        // reject form submit
        return false;
    });

    // bind jquery listener
    bindRefreshVerificationCodeListener();

    // init
    refreshVerificationCode();

    // function definition
    function getPublicKeyAsync() {
        return new Promise(function (resolve, reject) {
            _TcAxios
                .post('/auths/get_public_key.json')
                .then(function (response) {
                    _TcC.doWithTcR(response.data, function (code, body) {
                        if (code === 0) {
                            resolve(body);
                        } else {
                            console.error('axios [/auths/get_public_key.json] -> ' + code);
                            reject();
                        }
                    });
                })
                .catch(function (error) {
                    console.error('axios [/auths/get_public_key.json] => ' + error);
                    reject();
                });
        });
    }

    function login(form, publicKey) {
        return new Promise(function (resolve, reject) {
            var password = form.password;
            var encrypt = new JSEncrypt();
            encrypt.setPublicKey(publicKey);
            form.password = encrypt.encrypt(password);

            _TcAxios
                .post('/auths/login.json', $.param(form))
                .then(function (response) {
                    _TcC.doWithTcR(response.data, function (code, body) {
                        resolve({
                            code: code,
                            body: body
                        });
                    });
                })
                .catch(function (error) {
                    console.error('axios [/auths/login.json] => ' + error);
                    reject();
                });
        });
    }

    function doLogin(code) {
        if (code === 0) {
            // login succ, reload page, will auto redirect to home.
            location.reload();
            return;
        } else if (code === 1) {
            // expired verification code
            layer.msg('验证码已过期，请重新登陆！');
            resetLoginForm();
        } else if (code === 2) {
            // verification code not match
            layer.msg('验证码输入错误，请重新登陆！');
        } else if (code === 3) {
            // account not exists
            layer.msg('账号不存在，请重新登陆！');
        } else if (code === 4) {
            // account be locked
            layer.msg('账号被锁定，请稍后再试！');
        } else if (code === 5) {
            // account not match password
            layer.msg('账号密码错误，请重新登陆！');
        } else {
            _TcC.defaultAxiosExHandler();
        }
        // if not successful login, reset form.
        resetLoginForm();
    }

    function bindRefreshVerificationCodeListener() {
        $('form.index-login-fm a.refresh-verification-code-btn').click(function () {
            refreshVerificationCode();
        });
    }

    function resetLoginForm() {
        $(".index-login-fm")[0].reset();
        refreshVerificationCode();
    }

    function refreshVerificationCode() {
        _TcAxios
            .get('/auths/get_verification_code.json')
            .then(function (response) {
                _TcC.doWithTcR(response.data, function (code, body) {
                    $('form.index-login-fm a.verification-code-btn').html(body);
                });
            })
            .catch(function (error) {
                console.error('axios [/auths/get_verification_code.json] => ' + error);
                _TcC.defaultAxiosExHandler();
            });

    }

});
