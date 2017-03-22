layui.use(['element'], function () {

});

function menu_signout() {
    _menu_signout()
        .then(function (data) {
            var code = data.code;
            if (code === 0) {
                window.location.reload();
            } else {
                _TcC.defaultAxiosExHandler();
            }
        })
        .catch(function () {
            _TcC.defaultAxiosExHandler();
        });
}

function _menu_signout() {
    return new Promise(function (resolve, reject) {
        _TcAxios
            .post('/auths/signout.json')
            .then(function (response) {
                _TcC.doWithTcR(response.data, function (code, body) {
                    resolve({
                        code: code,
                        body: body
                    });
                });
            })
            .catch(function (error) {
                console.error('axios [/auths/signout.json] => ' + error);
                reject();
            });
    });
}