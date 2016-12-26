var indexVue = new Vue({

    el: '#doc-signup-div',

    data: {
        uiElement: {
            formElement: {
                username: '',
                password: '',
                verificationCode: '',
                rememberMe: false
            },
            signupErrorMsg: ''
        },
        hiddenElement: {
            verificationCode: ''
        },
        stateElement: {
            usernameValid: true,
            passwordValid: true,
            verificationCodeValid: true,
            rememberMeValid: true
        }
    },

    mounted: function () {
        this.reloadVerificationCode();
    },

    methods: {

        layerLoading: undefined,

        reloadVerificationCode: function () {
            _TcAxios
                .get('/auths/get_verification_code.json')
                .then(function (response) {
                    _TcC.doWithTcR(response.data, function (body) {
                        indexVue.hiddenElement.verificationCode = body;
                    });
                    throw new Error();
                })
                .catch(function (error) {
                    _TcC.defaultAxiosExHandler(error);
                });
        },

        signup: function () {
            // TODO wait vue validation released.
            indexVue.layerLoading = layer.load(1, {shade: [0.7, '#fff']});
            _TcAxios
                .get('/auths/get_token.json')
                .then(function (tokenR) {
                    _TcC.doWithTcR(tokenR.data, function (token) {
                        var password = indexVue.uiElement.formElement.password;
                        indexVue.uiElement.formElement.password = $.md5($.md5(password) + token);
                        indexVue.doSignup();
                    });
                })
                .catch(function (error) {
                    layer.close(indexVue.layerLoading);
                    _TcC.defaultAxiosExHandler(error);
                });
        },

        doSignup: function () {
            _TcAxios
                .post('/auth/signup.json', $.param(indexVue.uiElement.formElement))
                .then(function (response) {
                    layer.close(indexVue.layerLoading);
                    _TcC.doWithTcR(response.data, function (body) {
                        if (body === 0) {
                            // signup succ, reload page, will auto redirect to home.
                            location.reload();
                            return;
                        } else if (body === 1) {
                            // verification code not match
                            indexVue.uiElement.signupErrorMsg = '验证码输入错误，请重新登陆！';
                            indexVue.reset();
                            return;
                        } else if (body === 2) {
                            // account not exists
                            indexVue.uiElement.signupErrorMsg = '账号不存在，请重新登陆！';
                            indexVue.reset();
                            return;
                        } else if (body === 3) {
                            // account be locked
                            indexVue.uiElement.signupErrorMsg = '账号被锁定，请稍后再试！';
                            indexVue.reset();
                            return;
                        } else if (body === 4) {
                            // account not match password
                            indexVue.uiElement.signupErrorMsg = '账号密码错误，请重新登陆！';
                            indexVue.reset();
                            return;
                        }
                        layer.alert('抱歉，系统繁忙，请稍后再试！');
                    });
                })
                .catch(function (error) {
                    layer.close(indexVue.layerLoading);
                    _TcC.defaultAxiosExHandler(error);
                });
        },

        reset: function () {
            indexVue.uiElement.formElement.username = '';
            indexVue.uiElement.formElement.password = '';
            indexVue.uiElement.formElement.verificationCode = '';
            indexVue.uiElement.formElement.rememberMe = false;
            indexVue.reloadVerificationCode();
        }
    },

    computed: {
        // -- styles
        usernameClass: function () {
            return {
                'am-input-group-primary': this.stateElement.usernameValid,
                'am-input-group-danger': !this.stateElement.usernameValid
            };
        },

        passwordClass: function () {
            return {
                'am-input-group-primary': this.stateElement.passwordValid,
                'am-input-group-danger': !this.stateElement.passwordValid
            };
        },

        verificationCodeClass: function () {
            return {
                'am-input-group-primary': this.stateElement.verificationCodeValid,
                'am-input-group-danger': !this.stateElement.verificationCodeValid
            };
        },

        formClass: function () {
            return {
                'am-disabled': !(this.stateElement.usernameValid &&
                this.stateElement.passwordValid &&
                this.stateElement.verificationCodeValid)
            };
        },

        showSignupErrorMsg: function () {
            return this.uiElement.signupErrorMsg.length > 0;
        }
    }
});
