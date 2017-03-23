layui.use(['element'], function () {

});

function menu_logout() {
    _menu_logout()
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

function _menu_logout() {
    return new Promise(function (resolve, reject) {
        _TcAxios
            .post('/auths/logout.json')
            .then(function (response) {
                _TcC.doWithTcR(response.data, function (code, body) {
                    resolve({
                        code: code,
                        body: body
                    });
                });
            })
            .catch(function (error) {
                console.error('axios [/auths/logout.json] => ' + error);
                reject();
            });
    });
}