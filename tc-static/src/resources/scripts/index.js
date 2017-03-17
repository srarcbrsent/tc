layui.use(['element', 'form'], function () {
    // import
    var element = layui.element();
    var form = layui.form();

    // bind event
    form.verify({
        username: function (value) {
            if (!new RegExp("^1[0-9]{10}$").test(value)
                && !new RegExp("^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$").test(value)) {
                return '用户名为手机号或者邮箱';
            }
        },
        password: function (value) {
            if (!new RegExp("^[a-zA-Z0-9_]{6,16}$").test(value)) {
                return '密码为6-16位的英文字符或数字';
            }
        }
    });

    form.on('submit(fm-signup)', function (data) {
        var password = data.field.password;

        new Promise(function (resolve, reject) {
            return getPublicKeyAsync();
        }).then(function (publicKey) {
            console.log(publicKey);
        }).catch(function () {
            _TcC.defaultAxiosExHandler();
        });

        // reject form submit
        return false;
    });

    // bind listener
    bindRefreshVerificationCodeListener();

    // function definition
    function getPublicKeyAsync() {
        return new Promise(function (resolve, reject) {
            _TcAxios
                .post('/auths/public_key.json')
                .then(function (response) {
                    _TcC.doWithTcR(response.data, function (code, body) {
                        if (code === 0) {
                            resolve(body);
                        } else {
                            console.error('axios [/auths/public_key.json] -> ' + code);
                            reject();
                        }
                    });
                })
                .catch(function (error) {
                    console.error('axios [/auths/public_key.json] => ' + error);
                    reject();
                });
        });
    }

    function bindRefreshVerificationCodeListener() {
        $('form.index-signup-fm a.refresh-verification-code-btn').click(function () {
            refreshVerificationCode();
        });
    }

    function refreshVerificationCode() {
        _TcAxios
            .get('/auths/get_verification_code.json')
            .then(function (response) {
                _TcC.doWithTcR(response.data, function (code, body) {
                    $('form.index-signup-fm a.verification-code-btn').html(body);
                });
            })
            .catch(function (error) {
                console.error('axios [/auths/get_verification_code.json] => ' + error);
                _TcC.defaultAxiosExHandler();
            });

    }

});

// var indexVue = new Vue({
//
//     el: '#doc-signup-div',
//
//     data: {
//         ui: {
//             form: {
//                 username: '',
//                 password: '',
//                 verificationCode: '',
//                 rememberMe: false
//             },
//             verificationCode: ''
//         },
//
//         hidden: {},
//
//         state: {
//             usernameValid: true,
//             passwordValid: true,
//             verificationCodeValid: true,
//             rememberMeValid: true
//         }
//
//     },
//
//     mounted: function () {
//         this.reloadVerificationCode();
//     },
//
//     methods: {
//
//         _layerLoading: undefined,
//
//         reloadVerificationCode: function () {
//             _TcAxios
//                 .get('/auths/get_verification_code.json')
//                 .then(function (response) {
//                     _TcC.doWithTcR(response.data, function (code, body) {
//                         indexVue.ui.verificationCode = body;
//                     });
//                 })
//                 .catch(function (error) {
//                     _TcC.defaultAxiosExHandler(error);
//                 });
//         },
//
//         signup: function () {
//             // TODO wait vue validation released.
//             indexVue._layerLoading = layer.load(1, {shade: [0.7, '#fff']});
//             var password = indexVue.ui.form.password;
//             indexVue.ui.form.password = (new Hashes.SHA1).hex(password);
//             indexVue.doSignup();
//         },
//
//         doSignup: function () {
//             _TcAxios
//                 .post('/auths/signup.json', $.param(indexVue.ui.form))
//                 .then(function (response) {
//                     layer.close(indexVue._layerLoading);
//                     _TcC.doWithTcR(response.data, function (code, body) {
//                         if (code === 0) {
//                             // signup succ, reload page, will auto redirect to home.
//                             location.reload();
//                             return;
//                         } else if (code === 1) {
//                             // verification code not match
//                             layer.msg('验证码输入错误，请重新登陆！');
//                             indexVue.reset();
//                             return;
//                         } else if (code === 2) {
//                             // account not exists
//                             layer.msg('账号不存在，请重新登陆！');
//                             indexVue.reset();
//                             return;
//                         } else if (code === 3) {
//                             // account be locked
//                             layer.msg('账号被锁定，请稍后再试！');
//                             indexVue.reset();
//                             return;
//                         } else if (code === 4) {
//                             // account not match password
//                             layer.msg('账号密码错误，请重新登陆！');
//                             indexVue.reset();
//                             return;
//                         }
//                         layer.alert('抱歉，系统繁忙，请稍后再试！');
//                     });
//                 })
//                 .catch(function (error) {
//                     layer.close(indexVue._layerLoading);
//                     _TcC.defaultAxiosExHandler(error);
//                 });
//         },
//
//         reset: function () {
//             indexVue.ui.form.username = '';
//             indexVue.ui.form.password = '';
//             indexVue.ui.form.verificationCode = '';
//             indexVue.ui.form.rememberMe = false;
//             indexVue.reloadVerificationCode();
//         }
//     },
//
//     computed: {
//         // -- styles validation
//         validationStylesUsername: function () {
//             return {
//                 'am-input-group-primary': this.state.usernameValid,
//                 'am-input-group-danger': !this.state.usernameValid
//             };
//         },
//
//         validationStylesPassword: function () {
//             return {
//                 'am-input-group-primary': this.state.passwordValid,
//                 'am-input-group-danger': !this.state.passwordValid
//             };
//         },
//
//         validationStylesVerificationCode: function () {
//             return {
//                 'am-input-group-primary': this.state.verificationCodeValid,
//                 'am-input-group-danger': !this.state.verificationCodeValid
//             };
//         },
//
//         validationStylesSubmit: function () {
//             return {
//                 'am-disabled': !(this.state.usernameValid &&
//                 this.state.passwordValid &&
//                 this.state.verificationCodeValid)
//             };
//         }
//
//     }
// });
