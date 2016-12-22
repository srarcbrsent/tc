// 登陆相关
var _signupVue = new Vue({
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
            axios
                .get(__openApiBase + '/auths/get_verification_code.json', __axiosConfig)
                .then(function (response) {
                    __doWithTcR(response.data, function (body) {
                        _signupVue.hiddenElement.verificationCode = body;
                    });
                })
                .catch(function () {
                    layer.alert('抱歉，系统异常，请稍后再试！');
                });
        },

        signup: function () {
            // TODO wait vue validation released.
            _signupVue.layerLoading = layer.load(1, {shade: [0.7, '#fff']});
            axios
                .get(__openApiBase + '/auths/get_token.json', __axiosConfig)
                .then(function (tokenR) {
                    __doWithTcR(tokenR.data, function (token) {
                        var password = _signupVue.uiElement.formElement.password;
                        _signupVue.uiElement.formElement.password = $.md5($.md5(password) + token);
                        _signupVue.doSignup();
                    });
                })
                .catch(function (error) {
                    layer.close(_signupVue.layerLoading);
                    layer.alert('抱歉，系统异常，请稍后再试！');
                });
        },

        doSignup: function () {
            axios
                .post(__openApiBase + '/auth/signup.json', $.param(_signupVue.uiElement.formElement), __axiosConfig)
                .then(function (response) {
                    layer.close(_signupVue.layerLoading);
                    __doWithTcR(response.data, function (body) {
                        if (body === 0) {
                            // signup succ, reload page, will auto redirect to home.
                            window.location.reload();
                            return;
                        } else if (body == 1) {
                            // verification code not match
                            _signupVue.uiElement.signupErrorMsg = '验证码输入错误，请重新登陆！';
                            _signupVue.reset();
                            return;
                        } else if (body == 2) {
                            // account not exists
                            _signupVue.uiElement.signupErrorMsg = '账号不存在，请重新登陆！';
                            _signupVue.reset();
                            return;
                        } else if (body == 3) {
                            // account be locked
                            _signupVue.uiElement.signupErrorMsg = '账号被锁定，请稍后再试！';
                            _signupVue.reset();
                            return;
                        } else if (body == 4) {
                            // account not match password
                            _signupVue.uiElement.signupErrorMsg = '账号密码错误，请重新登陆！';
                            _signupVue.reset();
                            return;
                        }
                        layer.alert('抱歉，系统繁忙，请稍后再试！');
                    });
                })
                .catch(function (error) {
                    layer.close(_signupVue.layerLoading);
                    layer.alert('抱歉，系统异常，请稍后再试！');
                });
        },

        reset: function () {
            _signupVue.uiElement.formElement.username = '';
            _signupVue.uiElement.formElement.password = '';
            _signupVue.uiElement.formElement.verificationCode = '';
            _signupVue.uiElement.formElement.rememberMe = false;
            _signupVue.reloadVerificationCode();
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
