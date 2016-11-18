<div id="panel"></div>
<script src="${.vars.staticBase}/libs/jquery-3.1.0.min.js" type="application/javascript"></script>
<script src="${.vars.staticBase}/libs/websocket/sockjs.min.js" type="application/javascript"></script>
<script src="${.vars.staticBase}/libs/websocket/stomp.min.js" type="application/javascript"></script>

<script type="text/javascript">

    // 不需要认证的 ／ 需要认证的
    // 单点 ／ 群发
    // 消息留存
    var _TcWs = {
        wsUrl: 'http://websocket.tc.com/ep',

        username: '15862c20cd700ea',

        passcode: '15862c20cd700e9',

        client: undefined,

        subscriptions: [],

        // [{topic: '', handler: function() {}}]
        init: function (handlers) {
            _TcWs.client = Stomp.over(new SockJS(_TcWs.wsUrl));
            _TcWs.client.connect(_TcWs.username, _TcWs.passcode, function (frame) {
                _TcWs.subscribe(handlers);
            });
        },

        subscribe: function (handlers) {
            for (var i = 0; i < handlers.length; i++) {
                var handler = handlers[i];
                var subscription = _TcWs.client.subscribe(handler.topic, handler.handler);
                _TcWs.subscriptions.push({
                    name: handler.name,
                    subscription: subscription
                });
            }
        },

        unsubscribe: function (name) {
            $(_TcWs.subscriptions).filter(function (element) {
                return element.name == name;
            }).each(function (index, element) {
                element.unsubcribe();
            });
        },

        send: function (destnation, payload) {
            _TcWs.client.send(destnation, {}, JSON.stringify(payload));
        },

        disconnected: function () {
            _TcWs.client.disconnect();
        }
    };

    $(document).ready(function () {
        _TcWs.init([
            {
                name: 'chatroom',
                topic: '/dtp/chartroom',
                handler: function (payload) {
                    console.log(payload);
                }
            },
            {
                name: 'chart',
                topic: '/dup/123123/chart',
                handler: function (payload) {
                    console.log(payload);
                }
            }
        ]);
    });

</script>