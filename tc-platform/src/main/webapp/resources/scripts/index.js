// 登陆相关
var _signupVue = new Vue({
    el: '#doc-signup-form',
    data: {
        model: {
            username: '',
            password: '',
            verificationCode: '',
            rememberMe: false
        },
        hiddenModel: {
            verificationCode: '123456',
            signupErrorMsg: ''
        },
        verifyRules: {},
        stateControl: {
            usernameValid: true,
            passwordValid: true,
            verificationCodeValid: true,
            rememberMeValid: true
        }
    },
    methods: {
        reloadVerificationCode: function () {
            var a = axios.get(__base + '/auth/get_verification_code.json')
                .then(function (response) {
                    __doWithTcR(response.data, function (body) {
                        _signupVue.hiddenModel.verificationCode = body;
                    });
                }).catch(function () {
                    layer.alert('抱歉，系统异常，请稍后再试！');
                });
        },

        signup: function () {

            window.location.reload();
        }
    },
    computed: {
        // -- styles
        usernameValidClass: function () {
            return {
                'am-input-group-primary': this.stateControl.usernameValid,
                'am-input-group-danger': !this.stateControl.usernameValid
            }
        },

        passwordValidClass: function () {
            return {
                'am-input-group-primary': this.stateControl.passwordValid,
                'am-input-group-danger': !this.stateControl.passwordValid
            }
        },

        verificationCodeValidClass: function () {
            return {
                'am-input-group-primary': this.stateControl.verificationCodeValid,
                'am-input-group-danger': !this.stateControl.verificationCodeValid
            }
        },

        formInValidClass: function () {
            return {
                'am-disabled': !(this.stateControl.usernameValid &&
                this.stateControl.passwordValid &&
                this.stateControl.verificationCodeValid)
            }
        },

        showSignupErrorMsg: function () {
            return this.hiddenModel.signupErrorMsg.length > 0;
        }
    }
});

// layui.use(['form'], function () {
//     var _form = layui.form();
//
//     // form verify
//     _form.verify({
//         sp_username: function (value) {
//             if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5]{6,16}$").test(value)) {
//                 return '用户名由6-16位数字，字符，汉字，下划线组成';
//             }
//         },
//
//         sp_password: function (value) {
//             if (!new RegExp("^[a-zA-Z0-9]{6,16}$").test(value)) {
//                 return '密码由6-16位数字字符组成';
//             }
//         },
//
//         sp_verificationCode: function (value) {
//             if (!new RegExp("^[a-zA-Z0-9]{6}$").test(value)) {
//                 return '验证码由6位数字字符组成';
//             }
//         }
//     });
//
//     // form submit
//     _form.on('submit(signup)', function (data) {
//         var password = data.field.password;
//         // find token
//         var token = fetchToken();
//         // encode and set
//         var cltPassword = $.md5($.md5(password) + token);
//         $('#signup-form').find('input[type = "password"][name = "password"]').val(cltPassword);
//         return true;
//     });
//
//     var fetchToken = function () {
//         var token = null;
//         $.ajax({
//             url: __base + '/auth/token.json',
//             type: 'post',
//             async: false,
//             success: function (data, textStatus, jqXHR) {
//                 __doWithTcR(data, function (body) {
//                     token = body;
//                 });
//             }
//         });
//         return token;
//     }
// });