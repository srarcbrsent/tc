<div id="panel"></div>
<script src="${.vars.staticBase}/libs/jquery-3.1.0.min.js" type="application/javascript"></script>
<script src="${.vars.staticBase}/libs/stomp.min.js" type="application/javascript"></script>

<script type="text/javascript">

    $(document).ajaxError(function () {
        console.error('ajax error');
    });

    // token
    $.ajax({
        url: '/auth/token.json',
        type: 'post'
    }).done(function(data, textStatus, jqXHR) {
        // login
        $.ajax({
            url: '/auth/signup.json',
            type: 'post',
            data: {
                'username': 'yaowu.zhang',
                'password': '123123123',
                'rememberMe': false,
                'verificationCode': '123'
            }
        }).done(function(data, textStatus, jqXHR) {
            console.log('success');

            // websocket
            var client = Stomp.over(new WebSocket('ws://websocket.tc.com/tcendpoint'));
            client.connect({}, function(frame) {

            });

            socket.onopen = function (event) {
                // 发送一个初始化消息
                socket.send('I am the client and I\'m listening!');

                // 监听消息
                socket.onmessage = function (event) {
                    console.log('Client received a message', event);
                    var panel = document.getElementById('panel');
                    panel.innerHTML = '<p>' + event.data + '</p>' + panel.innerHTML;
                };

                // 监听Socket的关闭
                socket.onclose = function (event) {
                    console.log('Client notified socket has closed', event);
                };

                // 关闭Socket....
                //socket.close()

            }
        });
    });
</script>