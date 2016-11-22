layui.use(['form', 'upload'], function () {
    var form = layui.form();
    var upload = layui.upload;

    upload({
        url: '上传接口url'
        , success: function (res) {
            console.log(res); //上传成功返回值，必须为json格式
        }
    });
});